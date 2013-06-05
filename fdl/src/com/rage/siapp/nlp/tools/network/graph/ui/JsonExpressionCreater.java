package com.rage.siapp.nlp.tools.network.graph.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import com.rage.siapp.nlp.tools.network.graph.normalize.DBNode;
import com.rage.siapp.nlp.tools.network.graph.normalize.EntityType;
import com.semanticintelligence.app.common.CommonConstants;

public class JsonExpressionCreater
{
	public static String createJsonExpression(DBNode node, List<Long> filterConceptIds)
	{
		Vector<DBNode> childNodes = new Vector<DBNode>();
		return createJsonExpression(node, new HashSet<DBNode>(), filterConceptIds, childNodes);
	}

	public static String createJsonExpression(DBNode node, HashSet<DBNode> doneNodes, List<Long> filterConceptIds, Vector<DBNode> spotChildNodes)
	{
		EntityType entityType = EntityType.NONE;
		String ret = "";
		Integer radius = 2;
		String cuePhrase = "";
		String nodeName = "";

		if (node == null)
			return ret;

		if (doneNodes.contains(node))
			return ret;

		doneNodes.add(node);

		if (!node.getEntityTypes().isEmpty())
			entityType = node.getEntityTypes().get(0);

		Vector<DBNode> childNodes = node.getChildNodes();

		radius = getRadius(node, filterConceptIds, spotChildNodes);
		cuePhrase = getCuePhrase(node);

		if (node.getConceptID().equals(new Long(-1)))
			nodeName = node.getConcept();
		else
			nodeName = node.getNormalizedConcept();

		ret = "\"nodeName\":\"" + nodeName + "\", \"name\":\"" + nodeName + "\", \"nodeId\":\"" +
				node.getNodeID() + "\", \"id\":\"" + node.getNodeID() + "\", \"radius\":" + radius + ", \"entityType\":\"" + entityType.toString()
				+ "\"" + ", \"cuePhrase\":\"" + cuePhrase + "\", \"newsID\":" + node.getNewsID();

		if (childNodes.size() == 0)
			return ret;

		String childrenString = ", \"children\":[";

		boolean first = true;
		for (int i = 0; i < childNodes.size(); i++)
		{
			DBNode childNode = childNodes.elementAt(i);
			String thisChildStr = createJsonExpression(childNode, doneNodes, filterConceptIds, spotChildNodes);
			if (thisChildStr.trim().equalsIgnoreCase(""))
				continue;

			childrenString = childrenString + (first ? "" : ", ") + "{" + thisChildStr + "}";
			if (first == true)
				first = false;
		}

		childrenString = childrenString + "]";

		if (first)
			return ret;

		ret = ret + childrenString;

		return ret;
	}

	private static String getCuePhrase(DBNode node) {
		if (node.getInEdge() != null && node.getInEdge().getCuePhrase() != null)
			return node.getInEdge().getCuePhrase();
		else
			return "";
	}

	private static Integer getRadius(DBNode node, List<Long> filterConceptIds, Vector<DBNode> spotChildNodes) {

		if (spotChildNodes.contains(node)) {
			return CommonConstants.DEFAULT_WEIGHT_SPOT_NODE;
		}
		else if (filterConceptIds != null && filterConceptIds.size() > 0) {

			if (filterConceptIds.contains(node.getConceptID())) {

				Vector<DBNode> childNodes = node.getChildNodes();

				spotChildNodes.addAll(childNodes);

				for (DBNode dbNode : childNodes) {
					if (dbNode.getConceptID() != null && dbNode.getConceptID().equals(new Long(-1)) == false)
						filterConceptIds.add(dbNode.getConceptID());
				}

				return CommonConstants.DEFAULT_WEIGHT_SPOT_NODE;

			} else {
				return CommonConstants.DEFAULT_WEIGHT_DOT;
			}

		} else {
			return highBasedOnChilds(node.getChildNodes().size());
		}
	}

	private static Integer highBasedOnChilds(int size) {

		if (size == 0)
			return CommonConstants.DEFAULT_WEIGHT;
		else if (size > 60)
			return 17;
		else if (size > 50)
			return 15;
		else if (size > 40)
			return 13;
		else if (size > 30)
			return 11;
		else if (size > 20)
			return 9;
		else if (size > 10)
			return 7;
		else
			return CommonConstants.DEFAULT_WEIGHT;
	}
}
