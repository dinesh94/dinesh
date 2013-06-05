package com.rage.siapp.nlp.tools.network.graph.normalize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.semanticintelligence.app.model.Node;

public class DBNode
{
	@JsonIgnore
	private Long nodeID;
	@JsonIgnore
	private String concept;
	@JsonIgnore
	private Long conceptID;
	@JsonIgnore
	private String normalizedConcept;
	@JsonIgnore
	private Vector<EntityType> entityTypes;
	@JsonIgnore
	private Vector<DBAttribute> attributes;
	@JsonIgnore
	private DBNode parent;
	@JsonIgnore
	private DBEdge inEdge;
	@JsonIgnore
	private Vector<DBEdge> childEdges;
	@JsonIgnore
	private Vector<DBNode> childNodes;
	@JsonIgnore
	private Boolean isPDF;
	@JsonIgnore
	private Boolean isWebsearch;
	@JsonIgnore
	private Boolean isTwitter;
	@JsonIgnore
	private Integer sentenceID;
	@JsonIgnore
	private TreeSet<Integer> indexes;

	private Long newsID;
	private String nodeName;
	private String name;
	private Integer nodeId;
	private Integer id;
	private Integer radius;
	private List<DBNode> children = new ArrayList<DBNode>();

	public DBNode(DBNode otherNode)
	{
		setNodeID(otherNode.getNodeID());
		setConceptID(otherNode.getConceptID());
		setConcept(otherNode.getConcept());
		setNormalizedConcept(otherNode.getNormalizedConcept());
		setEntityTypes(otherNode.getEntityTypes());
		setAttributes(otherNode.getAttributes());
		setNewsID(otherNode.getNewsID());
		setIsPDF(otherNode.getIsPDF());
		setIsWebsearch(otherNode.getIsWebsearch());
		setIsTwitter(otherNode.getIsTwitter());
		setSentenceID(otherNode.getSentenceID());
		setIndexes(otherNode.getIndexes());

		setParent(null);
		setInEdge(null);

		setChildEdges(new Vector<DBEdge>());
		setChildNodes(new Vector<DBNode>());

		this.nodeId = otherNode.getNodeID().intValue();
		this.id = nodeId;
		this.name = otherNode.getNormalizedConcept();
		this.nodeName = name;
		this.radius = 4;
		setChildren(new ArrayList<DBNode>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DBNode> getChildren() {
		return children;
	}

	public void setChildren(List<DBNode> children) {
		this.children = children;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public DBNode(Long nodeID, Long conceptID, String concept, String normalizedConcept, Vector<EntityType> entityTypes, Vector<DBAttribute> attributes,
			Long newsID, Boolean isPDF, Boolean isWebsearch, Boolean isTwitter, Integer sentenceID, TreeSet<Integer> indexes)
	{
		setNodeID(nodeID);
		setConceptID(conceptID);
		setConcept(concept);
		setNormalizedConcept(normalizedConcept);
		setEntityTypes(entityTypes);
		setAttributes(attributes);
		setNewsID(newsID);
		setIsPDF(isPDF);
		setIsWebsearch(isWebsearch);
		setIsTwitter(isTwitter);
		setSentenceID(sentenceID);
		setIndexes(indexes);

		setParent(null);
		setInEdge(null);

		setChildEdges(new Vector<DBEdge>());
		setChildNodes(new Vector<DBNode>());

		this.nodeId = nodeID.intValue();
		this.id = nodeId;
		this.name = normalizedConcept;
		this.nodeName = name;
		this.radius = 4;
		setChildren(new ArrayList<DBNode>());
	}

	public void addEdge(DBNode childNode, DBEdge childEdge)
	{
		/*if ( edgeAlreadyPresent(childNode, childEdge) )
		{
			System.out.println("ALREADY PRESENT : " + childNode) ;
			return ;
		}*/

		if (childNode != null)
		{
			childNode.setParent(this);
			childNode.setInEdge(childEdge);

			getChildNodes().addElement(childNode);
			getChildEdges().addElement(childEdge);

			getChildren().add(childNode);
		}

	}

	public boolean edgeAlreadyPresent(DBNode childNode, DBEdge childEdge)
	{
		for (int i = 0; i < getChildNodes().size(); i++)
		{
			DBNode node = getChildNodes().elementAt(i);
			DBEdge edge = getChildEdges().elementAt(i);

			if (!node.equals(childNode))
			{
				// System.out.println("NODES ARE EQUAL : " + node + " : " + childNode) ;
				return true;
			}

			if (!edge.equals(childEdge))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString()
	{
		return getNormalizedConcept() + (getNormalizedConcept().equalsIgnoreCase("") ? "" : " ") + "[" + getConceptID() + ", " +
				getConcept() + ", " + getNodeID() + ", " + getNewsID() + ", " + getSentenceID() + ", " +
				getIndexes().toString() + ", " + getAttributes() + "]";
	}

	public String toDeepString()
	{
		return toDeepString(this, null, "", new HashSet<DBNode>(), new HashSet<DBNode>());
	}

	private String toDeepString(DBNode node, DBEdge inEdge, String indent, HashSet<DBNode> doneNodes, HashSet<DBNode> printedNodes)
	{
		String ret = "";

		// System.out.println("PRINTING NODE : " + node) ;

		if (node == null)
		{
			ret = ret + indent + (inEdge == null ? "" : inEdge.toString()) + "   " + "SELF";
			return ret + "\n";
		}

		if (!printedNodes.contains(node))
			ret = ret + indent + (node.getInEdge() == null ? "[ ]" : node.getInEdge().toString()) + "    " + node.toString() + "\n";

		printedNodes.add(node);

		indent = indent + "    ";

		// System.out.println(indent + "THIS NODE's CHILDREN : " + node.getChildNodes().size()) ;

		if (doneNodes.contains(node))
		{
			/*if (node.getChildNodes().size() != 0)
				ret = ret + indent + "..............\n";*/
			return ret;
		}

		doneNodes.add(node);

		for (int i = 0; i < node.getChildNodes().size(); i++)
		{
			DBNode childNode = node.getChildNodes().elementAt(i);
			DBEdge childEdge = node.getChildEdges().elementAt(i);
			// System.out.println(indent + "ADDING CHILD : " + childNode) ;
			ret = ret + toDeepString(childNode, childEdge, indent, doneNodes, printedNodes);
		}

		return ret;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;

		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result + ((conceptID == null) ? 0 : conceptID.hashCode());
		result = prime * result + ((indexes == null) ? 0 : indexes.hashCode());
		result = prime * result + ((isPDF == null) ? 0 : isPDF.hashCode());
		result = prime * result + ((isTwitter == null) ? 0 : isTwitter.hashCode());
		result = prime * result + ((isWebsearch == null) ? 0 : isWebsearch.hashCode());
		result = prime * result + ((newsID == null) ? 0 : newsID.hashCode());
		result = prime * result + ((nodeID == null) ? 0 : nodeID.hashCode());
		result = prime * result + ((normalizedConcept == null) ? 0 : normalizedConcept.hashCode());
		result = prime * result + ((sentenceID == null) ? 0 : sentenceID.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			// System.out.println("SAME OBJECT .... ") ;
			return true;
		}

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		DBNode other = (DBNode) obj;

		if (nodeID == null)
		{
			if (other.nodeID != null)
				return false;
		}
		else if (!nodeID.equals(other.nodeID))
			return false;

		if (conceptID == null)
		{
			if (other.conceptID != null)
				return false;
		}
		else if (!conceptID.equals(other.conceptID))
			return false;

		if (concept == null)
		{
			if (other.concept != null)
				return false;
		}
		else if (!concept.equals(other.concept))
			return false;

		if (attributes == null)
		{
			if (other.attributes != null)
				return false;
		}
		else if (!attributes.equals(other.attributes))
			return false;

		if (normalizedConcept == null)
		{
			if (other.normalizedConcept != null)
				return false;
		}
		else if (!normalizedConcept.equals(other.normalizedConcept))
			return false;

		if (newsID == null)
		{
			if (other.newsID != null)
				return false;
		}
		else if (!newsID.equals(other.newsID))
			return false;

		if (sentenceID == null)
		{
			if (other.sentenceID != null)
				return false;
		}
		else if (!sentenceID.equals(other.sentenceID))
			return false;

		if (indexes == null)
		{
			if (other.indexes != null)
				return false;
		}
		else if (!indexes.equals(other.indexes))
			return false;

		if (isPDF == null)
		{
			if (other.isPDF != null)
				return false;
		}
		else if (!isPDF.equals(other.isPDF))
			return false;

		if (isTwitter == null)
		{
			if (other.isTwitter != null)
				return false;
		}
		else if (!isTwitter.equals(other.isTwitter))
			return false;

		if (isWebsearch == null)
		{
			if (other.isWebsearch != null)
				return false;
		}
		else if (!isWebsearch.equals(other.isWebsearch))
			return false;

		return true;
	}

	public Long getNodeID()
	{
		return nodeID;
	}

	public void setNodeID(Long nodeID)
	{
		this.nodeID = nodeID;
	}

	public String getConcept()
	{
		return concept;
	}

	public void setConcept(String concept)
	{
		this.concept = concept;
	}

	public Long getConceptID()
	{
		return conceptID;
	}

	public void setConceptID(Long conceptID)
	{
		this.conceptID = conceptID;
	}

	public String getNormalizedConcept()
	{
		return normalizedConcept.replaceAll("'", "");
	}

	public void setNormalizedConcept(String normalizedConcept)
	{
		this.normalizedConcept = normalizedConcept;
	}

	public Vector<DBAttribute> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Vector<DBAttribute> attributes)
	{
		this.attributes = attributes;
	}

	public DBNode getParent()
	{
		return parent;
	}

	public void setParent(DBNode parent)
	{
		this.parent = parent;
	}

	public DBEdge getInEdge()
	{
		return inEdge;
	}

	public void setInEdge(DBEdge inEdge)
	{
		this.inEdge = inEdge;
	}

	public Vector<DBEdge> getChildEdges()
	{
		return childEdges;
	}

	public void setChildEdges(Vector<DBEdge> childEdges)
	{
		this.childEdges = childEdges;
	}

	public Vector<DBNode> getChildNodes()
	{
		return childNodes;
	}

	public void setChildNodes(Vector<DBNode> childNodes)
	{
		this.childNodes = childNodes;
	}

	public Long getNewsID()
	{
		return newsID;
	}

	public void setNewsID(Long newsID)
	{
		this.newsID = newsID;
	}

	public Boolean getIsPDF()
	{
		return isPDF;
	}

	public void setIsPDF(Boolean isPDF)
	{
		this.isPDF = isPDF;
	}

	public Boolean getIsWebsearch() {
		return isWebsearch;
	}

	public void setIsWebsearch(Boolean isWebsearch) {
		this.isWebsearch = isWebsearch;
	}

	public Boolean getIsTwitter() {
		return isTwitter;
	}

	public void setIsTwitter(Boolean isTwitter) {
		this.isTwitter = isTwitter;
	}

	public Integer getSentenceID() {
		return sentenceID;
	}

	public void setSentenceID(Integer sentenceID) {
		this.sentenceID = sentenceID;
	}

	public TreeSet<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(TreeSet<Integer> indexes) {
		this.indexes = indexes;
	}

	public Vector<EntityType> getEntityTypes() {
		return entityTypes;
	}

	public void setEntityTypes(Vector<EntityType> entityTypes) {
		this.entityTypes = entityTypes;
	}
}
