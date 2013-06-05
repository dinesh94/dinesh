package com.rage.siapp.nlp.tools.network.graph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import com.rage.siapp.db.DatabaseConnection;
import com.rage.siapp.nlp.tools.network.AttributeType;
import com.rage.siapp.nlp.tools.network.EdgeName;
import com.rage.siapp.nlp.tools.network.EdgeType;
import com.rage.siapp.nlp.tools.network.graph.Pair;
import com.rage.siapp.nlp.tools.network.graph.normalize.DBAttribute;
import com.rage.siapp.nlp.tools.network.graph.normalize.DBEdge;
import com.rage.siapp.nlp.tools.network.graph.normalize.DBNode;
import com.rage.siapp.nlp.tools.network.graph.normalize.EntityType;
import com.rage.siapp.nlp.tools.network.graph.ui.JsonExpressionCreater;
import com.semanticintelligence.app.model.Node;

public class DBGraphCreater
{
	private Connection conn;
	private Long graphID;

	private Vector<DBNode> mainNodes;

	private HashMap<Long, DBNode> idNodesMap;
	private HashSet<Long> mainNodeIDs;
	private HashMap<Long, Vector<Pair<Long, DBEdge>>> edgesMap;

	public DBGraphCreater(Connection conn, Long graphID)
	{
		setConn(conn);
		setGraphID(graphID);

		setMainNodes(new Vector<DBNode>());
	}

	public void run()
	{
		deSerializeNodes();
		//System.out.println("ID NODES MAP : " + getIdNodesMap());
		//System.out.println("MAIN NODE IDs: " + getMainNodeIDs());
		deSerializeEdges();
		//System.out.println("EDGES MAP : " + getEdgesMap());

		createHierarchy();
	}

	private void createHierarchy()
	{
		for (Long mainNodeID : getMainNodeIDs())
		{
			DBNode mainNode = getIdNodesMap().containsKey(mainNodeID) ? getIdNodesMap().get(mainNodeID) : null;

			if (mainNode == null)
				continue;

			LinkedList<DBNode> queue = new LinkedList<DBNode>();
			queue.add(mainNode);

			HashSet<DBNode> doneNodes = new HashSet<DBNode>();

			while (!queue.isEmpty())
			{
				DBNode node = queue.removeFirst();
				//System.out.println("\n\nPROCESSING NODE : " + node);
				if (node == null)
					continue;

				if (doneNodes.contains(node))
					continue;

				/*if (doneNodes.size() > 2)
					break;*/

				doneNodes.add(node);

				Long nodeID = node.getNodeID();

				Vector<Pair<Long, DBEdge>> children = getEdgesMap().containsKey(nodeID) ? getEdgesMap().get(nodeID) : new Vector<Pair<Long, DBEdge>>();
				//System.out.println("CHILDREN SIZE : " + children.size());

				for (int i = 0; i < children.size(); i++)
				{
					Pair<Long, DBEdge> child = children.elementAt(i);
						DBEdge edge = child.getBValue();

						Long childNodeID = child.getAValue();
						DBNode childNode = getIdNodesMap().containsKey(childNodeID) ? getIdNodesMap().get(childNodeID) : null;

						if (childNode.getNodeID().equals(node.getNodeID()))
							childNode = null;

						node.addEdge(childNode, edge);

						if (!doneNodes.contains(childNode))
					{
							queue.add(childNode);
						//System.out.println("ADDING NODE : " + childNode + " : TO : " + node) ;
					}
					else
					{
						//System.out.println("MISSING NODE : " + childNode + " : TO : " + node) ;
					}
				}

			}

			getMainNodes().addElement(mainNode);

			//System.out.println("DBGraphCreater.createHierarchy() mainNode = " + mainNode.toString());
		}
	}

	private void deSerializeEdges()
	{
		setEdgesMap(new HashMap<Long, Vector<Pair<Long, DBEdge>>>());

		/*String sql = "" +
				"select distinct a.edge_id, a.source_node_id, a.destination_node_id, a.edge_name, a.edge_type, a.cue_phrase, b.key as attribute_id, b.value as attribute_value " +
				"	from si_2_sn_edge a, si_2_sn_attr b " +
				"	where a.edge_id=b.referring_id " +
				"		and b.is_node_attr='N' " +
				"		and a.graph_id=? " ;*/

		String sql = "" +
				"select distinct a.edge_id, a.source_node_id, a.destination_node_id, a.edge_name, a.edge_type, a.cue_phrase, " +
				" b.key as attribute_type, b.value as attribute_value " +
				"	from si_2_sn_edge a LEFT OUTER JOIN si_2_sn_attr b on a.edge_id=b.referring_id and b.is_node_attr='N' " +
				"	where a.graph_id=?" +
				"" ;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		HashMap<DBEdge, Vector<DBAttribute>> edgeAttributesMap = new HashMap<DBEdge, Vector<DBAttribute>>();
		HashMap<DBEdge, Pair<Long, Long>> edgeNodesMap = new HashMap<DBEdge, Pair<Long, Long>>();
		HashSet<DBEdge> allEdges = new HashSet<DBEdge>();

		try
		{
			pstmt = getConn().prepareStatement(sql);
			pstmt.setLong(1, getGraphID());

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				Long edgeID = rs.getLong("edge_id");
				String strEdgeName = rs.getString("edge_name");
				String strEdgeType = rs.getString("edge_type");
				String cuePhrase = rs.getString("cue_phrase") == null ? "" : rs.getString("cue_phrase");

				EdgeName edgeName = EdgeName.valueOf(strEdgeName);
				EdgeType edgeType = EdgeType.valueOf(strEdgeType);

				Vector<DBAttribute> attributes = new Vector<DBAttribute>();
				DBEdge edge = new DBEdge(edgeID, edgeName, edgeType, cuePhrase, attributes);
				allEdges.add(edge);

				String strAttributeType = rs.getObject("attribute_type") == null ? "NONE" : rs.getString("attribute_type");
				AttributeType attributeType = AttributeType.valueOf(strAttributeType);
				String attributeValue = rs.getString("attribute_value");

				if (attributeType.equals(AttributeType.NONE))
				{
					Vector<DBAttribute> thisEdgeAttributes = edgeAttributesMap.containsKey(edge) ? edgeAttributesMap.get(edge) : new Vector<DBAttribute>();
					edgeAttributesMap.put(edge, thisEdgeAttributes);

					Long start = rs.getLong("source_node_id");
					Long end = rs.getObject("destination_node_id") == null ? start : rs.getLong("destination_node_id");
					Pair<Long, Long> pair = new Pair<Long, Long>(start, end);

					edgeNodesMap.put(edge, pair);

					continue;
				}

				DBAttribute attribute = new DBAttribute(attributeType, attributeValue);
				Vector<DBAttribute> thisEdgeAttributes = edgeAttributesMap.containsKey(edge) ? edgeAttributesMap.get(edge) : new Vector<DBAttribute>();
				thisEdgeAttributes.addElement(attribute);
				edgeAttributesMap.put(edge, thisEdgeAttributes);

				Long start = rs.getLong("source_node_id");
				Long end = rs.getLong("destination_node_id");
				Pair<Long, Long> pair = new Pair<Long, Long>(start, end);

				edgeNodesMap.put(edge, pair);
			}
		} catch (Exception e)
		{
			System.err.println("ERROR IN GETTING ALL NODES : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();

				if (rs != null)
					rs.close();
			} catch (Exception e)
			{
				System.err.println("ERROR IN CLOSING THE STATEMENT AND RESULTSET : " + e.getMessage());
				e.printStackTrace();
			}
		}

		for (DBEdge edge : allEdges)
		{
			Vector<DBAttribute> attributes = edgeAttributesMap.get(edge);
			Pair<Long, Long> nodesPair = edgeNodesMap.containsKey(edge) ? edgeNodesMap.get(edge) : new Pair<Long, Long>(new Long(-1), new Long(-1));

			edge.setAttributes(attributes);

			Long start = nodesPair.getAValue();
			Long end = nodesPair.getBValue();

			Pair<Long, DBEdge> pair = new Pair<Long, DBEdge>(end, edge);

			Vector<Pair<Long, DBEdge>> thisNodeChildren = getEdgesMap().containsKey(start) ? getEdgesMap().get(start) : new Vector<Pair<Long, DBEdge>>();
			thisNodeChildren.add(pair);
			getEdgesMap().put(start, thisNodeChildren);
		}
	}

	private void deSerializeNodes()
	{
		setIdNodesMap(new HashMap<Long, DBNode>());
		setMainNodeIDs(new HashSet<Long>());

		/*String sql = "" +
				"select distinct c.node_id, c.news_id, c.is_pdf, c.is_websearch, c.is_twitter, c.sentence_id, c.raw_concept, c.concept_tokens, c.indexes, a.concept_id, a.normalized_concept, " +
				"		b.value as entity_type, d.key as attribute_type, d.value as attribute_value " +
				"	from si_2_sn_concepts a, si_2_sn_concept_attr b, si_2_sn_node c, si_2_sn_attr d, si_2_sn_edge e " +
				"	where c.node_id=d.referring_id " +
				"		and c.concept_id=a.concept_id " +
				"		and a.concept_id=b.concept_id " +
				"		and d.is_node_attr='Y' " +
				"		and b.key='Entity_Type' " +
				"		and c.node_id=e.source_node_id " +
				"		and e.graph_id=? " +
				"union " +
				"select distinct c.node_id, c.news_id, c.is_pdf, c.is_websearch, c.is_twitter, c.sentence_id, c.raw_concept, c.concept_tokens, c.indexes, a.concept_id, a.normalized_concept, " +
				"		b.value as entity_type, d.key as attribute_type, d.value as attribute_value " +
				"	from si_2_sn_concepts a, si_2_sn_concept_attr b, si_2_sn_node c, si_2_sn_attr d, si_2_sn_edge e " +
				"	where c.node_id=d.referring_id " +
				"		and c.concept_id=a.concept_id " +
				"		and a.concept_id=b.concept_id " +
				"		and d.is_node_attr='Y' " +
				"		and d.key='Entity_Type' " +
				"		and c.node_id=e.destination_node_id " +
				"		and e.graph_id=? " +
				"order by node_id" +
				"" ;*/

		String sql = "" +
				"select distinct a.node_id, a.news_id, a.is_pdf, a.is_websearch, a.is_twitter, a.concept_id, a.raw_concept, a.sentence_id, " +
				"		a.indexes, a.is_main_node, c.attribute_id, c.key as attribute_type, c.value as attribute_value, " +
				"		d.concept_id, d.normalized_concept, e.value as entity_type " +
				"	from si_2_sn_node a INNER JOIN si_2_sn_edge b on a.node_id=b.source_node_id " +
				"			LEFT OUTER JOIN si_2_sn_attr c on a.node_id=c.referring_id and c.is_node_attr='Y' " +
				"			LEFT OUTER JOIN si_2_sn_concepts d on a.concept_id=d.concept_id " +
				"			LEFT OUTER JOIN si_2_sn_concept_attr e on a.concept_id=e.concept_id and e.key='Entity_Type' " +
				"	where b.graph_id=? " +
				"union " +
				"select distinct a.node_id, a.news_id, a.is_pdf, a.is_websearch, a.is_twitter, a.concept_id, a.raw_concept, a.sentence_id, " +
				"		a.indexes, a.is_main_node, c.attribute_id, c.key as attribute_type, c.value as attribute_value, " +
				"		d.concept_id, d.normalized_concept, e.value as entity_type " +
				"	from si_2_sn_node a INNER JOIN si_2_sn_edge b on a.node_id=b.destination_node_id " +
				"			LEFT OUTER JOIN si_2_sn_attr c on a.node_id=c.referring_id and c.is_node_attr='Y' " +
				"			LEFT OUTER JOIN si_2_sn_concepts d on a.concept_id=d.concept_id " +
				"			LEFT OUTER JOIN si_2_sn_concept_attr e on a.concept_id=e.concept_id and e.key='Entity_Type' " +
				"	where b.graph_id=? " +
				"";

		HashMap<DBNode, Vector<EntityType>> nodeEntityTypesMap = new HashMap<DBNode, Vector<EntityType>>();
		HashMap<DBNode, Vector<DBAttribute>> nodeAttributesMap = new HashMap<DBNode, Vector<DBAttribute>>();
		HashSet<DBNode> mainNodes = new HashSet<DBNode>();
		HashSet<DBNode> allNodes = new HashSet<DBNode>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			pstmt = getConn().prepareStatement(sql);
			pstmt.setLong(1, getGraphID());
			pstmt.setLong(2, getGraphID());

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				Long nodeID = rs.getLong("node_id");
				Long conceptID = rs.getObject("concept_id") == null ? new Long(-1) : rs.getLong("concept_id");
				String concept = rs.getString("raw_concept");
				String normalizedConcept = conceptID.longValue() == Long.parseLong("-1") ? "" : rs.getString("normalized_concept");
				Long newsID = rs.getLong("news_id");
				Boolean isPDF = rs.getString("is_pdf").equalsIgnoreCase("Y") ? Boolean.TRUE : Boolean.FALSE;
				Boolean isWebsearch = rs.getString("is_websearch").equalsIgnoreCase("Y") ? Boolean.TRUE : Boolean.FALSE;
				Boolean isTwitter = rs.getString("is_twitter").equalsIgnoreCase("Y") ? Boolean.TRUE : Boolean.FALSE;
				Integer sentenceID = rs.getInt("sentence_id");
				String strIndexes = rs.getString("indexes");

				strIndexes = strIndexes == null ? "" : strIndexes.replaceAll("\\[", "").replaceAll("\\]", "").trim() ;
				TreeSet<String> indexes1 = new TreeSet<String>(Arrays.asList(strIndexes.split(", ")));
				TreeSet<Integer> indexes = new TreeSet<Integer>();
				if (!strIndexes.trim().equalsIgnoreCase(""))
				{
					for (String index1 : indexes1)
						indexes.add(new Integer(index1));
				}

				Vector<EntityType> entityTypes = new Vector<EntityType>();
				Vector<DBAttribute> attributes = new Vector<DBAttribute>();

				DBNode node = new DBNode(nodeID, conceptID, concept, normalizedConcept, entityTypes, attributes, newsID, isPDF, isWebsearch, isTwitter, sentenceID, indexes);
				allNodes.add(node);

				String strIsMainNode = rs.getString("is_main_node") ;
				Boolean isMainNode = strIsMainNode == null ? Boolean.FALSE : strIsMainNode.equalsIgnoreCase("N") ? Boolean.FALSE : Boolean.TRUE ;
				
				if (isMainNode.booleanValue())
					mainNodes.add(node);

				EntityType entityType = EntityType.NONE;
				if (conceptID.longValue() != Long.parseLong("-1"))
				{
					normalizedConcept = rs.getString("normalized_concept");
					//System.out.println("NORMALIZED CONCEPT : " + normalizedConcept) ;

					String strEntityType = rs.getString("entity_type");
					entityType = strEntityType == null ? EntityType.NONE :
							(strEntityType.equalsIgnoreCase("Company") ? EntityType.COMPANY :
									(strEntityType.equalsIgnoreCase("Name of Person") ? EntityType.PERSON :
											(strEntityType.equalsIgnoreCase("Topic") ? EntityType.RELATIONSHIP :
													(strEntityType.equalsIgnoreCase("Geography") ? EntityType.GEOGRAPHY :
															(strEntityType.equalsIgnoreCase("Regulatory Boby") ? EntityType.REGULATORY_BODY :
																	(strEntityType.equalsIgnoreCase("Time Period") ? EntityType.TIME_PERIOD : EntityType.NONE))))));
				}

				if (entityType.equals(EntityType.NONE))
				{
					Vector<EntityType> thisNodeEntities = nodeEntityTypesMap.containsKey(node) ? nodeEntityTypesMap.get(node) : new Vector<EntityType>();
					nodeEntityTypesMap.put(node, thisNodeEntities);
				}
				else
				{
					Vector<EntityType> thisNodeEntities = nodeEntityTypesMap.containsKey(node) ? nodeEntityTypesMap.get(node) : new Vector<EntityType>();
					thisNodeEntities.addElement(entityType);
					nodeEntityTypesMap.put(node, thisNodeEntities);
				}

				String strAttributeType = rs.getObject("attribute_type") == null ? "NONE" : rs.getString("attribute_type");

				AttributeType attributeType = AttributeType.valueOf(strAttributeType);
				if ( attributeType.equals(AttributeType.NONE) )
				{
					Vector<DBAttribute> thisNodeAttributes = nodeAttributesMap.containsKey(node) ? nodeAttributesMap.get(node) : new Vector<DBAttribute>();
					nodeAttributesMap.put(node, thisNodeAttributes);
					continue;
				}

				String attributeValue = attributeType.equals(AttributeType.NONE) ? "" : rs.getString("attribute_value");

				DBAttribute attribute = new DBAttribute(attributeType, attributeValue);

				Vector<DBAttribute> thisNodeAttributes = nodeAttributesMap.containsKey(node) ? nodeAttributesMap.get(node) : new Vector<DBAttribute>();
				thisNodeAttributes.addElement(attribute);
				nodeAttributesMap.put(node, thisNodeAttributes);
			}
		} catch (Exception e)
		{
			System.err.println("ERROR IN GETTING ALL NODES : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();

				if (rs != null)
					rs.close();
			} catch (Exception e)
			{
				System.err.println("ERROR IN CLOSING THE STATEMENT AND RESULTSET : " + e.getMessage());
				e.printStackTrace();
			}
		}

		for (DBNode node : allNodes)
		{
			Vector<EntityType> entityTypes = nodeEntityTypesMap.containsKey(node) ? nodeEntityTypesMap.get(node) : new Vector<EntityType>();
			Vector<DBAttribute> attributes = nodeAttributesMap.containsKey(node) ? nodeAttributesMap.get(node) : new Vector<DBAttribute>();
			Boolean isNodeMain = mainNodes.contains(node) ? Boolean.TRUE : Boolean.FALSE;

			node.setAttributes(attributes);
			node.setEntityTypes(entityTypes);

			Long nodeID = node.getNodeID();

			getIdNodesMap().put(nodeID, node);

			if (isNodeMain.booleanValue()) {
				getMainNodeIDs().add(nodeID);
				System.out.println("Main Node Found = " + nodeID);
			}

			//getMainNodeIDs().add(13678l);
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Long getGraphID() {
		return graphID;
	}

	public void setGraphID(Long graphID) {
		this.graphID = graphID;
	}

	public Vector<DBNode> getMainNodes() {
		return mainNodes;
	}

	public void setMainNodes(Vector<DBNode> mainNodes) {
		this.mainNodes = mainNodes;
	}

	public HashMap<Long, DBNode> getIdNodesMap() {
		return idNodesMap;
	}

	public void setIdNodesMap(HashMap<Long, DBNode> idNodesMap) {
		this.idNodesMap = idNodesMap;
	}

	public HashSet<Long> getMainNodeIDs() {
		return mainNodeIDs;
	}

	public void setMainNodeIDs(HashSet<Long> mainNodeIDs) {
		this.mainNodeIDs = mainNodeIDs;
	}

	public HashMap<Long, Vector<Pair<Long, DBEdge>>> getEdgesMap() {
		return edgesMap;
	}

	public void setEdgesMap(HashMap<Long, Vector<Pair<Long, DBEdge>>> edgesMap) {
		this.edgesMap = edgesMap;
	}

	public static void main(String[] args) {

		/*Connection conn = DatabaseConnection.getConnection();

		DBGraphCreater result = new DBGraphCreater(conn, new Long(20));
		result.run();

		for (int i = 0; i < result.getMainNodes().size(); i++)
		{
			DBNode mainNode = result.getMainNodes().elementAt(i);
			System.out.println(mainNode.toDeepString());
		}

		String json = "{" + JsonExpressionCreater.createJsonExpression(result.getMainNodes().get(0), new ArrayList<Long>()) + "}";

		System.out.println(json);*/

	}
}
