/**
 * 
 */
package com.semanticintelligence.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.common.CommonConstants;
import com.semanticintelligence.app.dao.Si2SnEdgeDAO;
import com.semanticintelligence.app.dao.Si2SnNodeDAO;
import com.semanticintelligence.app.domain.SentenceIdentifier;
import com.semanticintelligence.app.domain.Si2SnEdge;
import com.semanticintelligence.app.domain.Si2SnNode;
import com.semanticintelligence.app.model.NewsDetails;
import com.semanticintelligence.app.model.Node;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2SnNodeServiceImpl implements Si2SnNodeService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private Si2SnNodeDAO si2SnNodeDAO;

	@Resource
	private Si2SnEdgeDAO si2SnEdgeDAO;

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnNode save(Si2SnNode objPersist) {
		return si2SnNodeDAO.save(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	public void delete(Si2SnNode objRemove) {
		si2SnNodeDAO.delete(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnNode merge(Si2SnNode toMerge) {
		return si2SnNodeDAO.merge(toMerge);
	}

	@Override
	public List<Long> getAllNodeIdByConeptId(Long rootNodeId) {
		return si2SnNodeDAO.getAllNodeIdByConeptId(rootNodeId);
	}

	@Override
	public List<Si2SnNode> getSi2SnNodeByConceptId(Command command) {
		return si2SnNodeDAO.getSi2SnNodeByConceptId(command);
	}

	@Override
	public Map<String, Object> getGraphDataMap(Command command, List<Long> filterConceptIds) {

		Map<String, Object> map = new HashMap<String, Object>();
		Map<Integer, Node> idNode = new HashMap<Integer, Node>();
		Set<Long> newsIds = new HashSet<Long>();
		List<Node> nodeList = new ArrayList<Node>();

		Map<Long, Long> rootNodeIdGraphIdMap = si2SnEdgeDAO.getRootNodeIdGraphIdMap(command.getDefaultGraphIdList());

		List<Long> rootNodeIdList = new ArrayList<Long>();
		rootNodeIdList.addAll(rootNodeIdGraphIdMap.keySet());

		List<Si2SnNode> parentSi2SnNodeList = si2SnNodeDAO.getSi2SnNodeByNodeId(rootNodeIdList);

		List<Long> rootNodes = new ArrayList<Long>();

		Node rootNode = null;
		Map<Integer, Node> nodeIdNodeMapping = new HashMap<Integer, Node>();
		Set<Integer> doneNodes = new HashSet<Integer>();

		Set<Integer> allNodesIds = new HashSet<Integer>();
		List<Object[]> result = si2SnEdgeDAO.getGraphNodes(command);

		for (Object[] objects : result) {
			allNodesIds.add(objects[0] != null ? Integer.parseInt(objects[0].toString()) : -1);
		}

		List<Node> allNodes = getNodesByIdallNodes(allNodesIds);
		nodeIdNodeMapping = getNodeIdNodeMapping(allNodes, nodeIdNodeMapping);

		Map<Integer, List<Node>> graphIdNodeMap = getGraphIdNodeMap(command, result);

		List<Si2SnEdge> si2SnEdgeList = si2SnEdgeDAO.getSi2SnEdgeForGraphId(command.getDefaultGraphIdList());

		for (Si2SnNode si2SnNode : parentSi2SnNodeList) {

			rootNodes.add(si2SnNode.getNodeId());

			rootNode = new Node(si2SnNode);
			rootNode.setIsRoot(true);
			nodeList.add(rootNode);

			if (filterConceptIds.contains(rootNode.getNode().getConceptId())) {
				rootNode.setRadius(CommonConstants.DEFAULT_WEIGHT_DOT);
			}

			newsIds.add(si2SnNode.getNewsId());

			//getChilde(rootNode, doneNodes, nodeIdNodeMapping, si2SnEdgeList);
			getChilde(rootNode, doneNodes, nodeIdNodeMapping, si2SnEdgeList);

			System.out.println("Si2SnNodeServiceImpl.getGraphDataMap() rootNode = " + rootNode.toString());
		}

		map.put("idNode", nodeIdNodeMapping);
		map.put("rootNodes", rootNode);

		return map;

	}

	/*@Override
	public Map<String, Object> getGraphDataMap(Command command, List<Long> filterConceptIds) {

		Set<Long> newsIds = new HashSet<Long>();

		Map<Integer, Node> idNode = new HashMap<Integer, Node>();
		Map<Integer, List<Node>> parentChild = new HashMap<Integer, List<Node>>();
		List<Si2SnNode> si2SnNodeList = new ArrayList<Si2SnNode>();
		List<Si2SnEdge> si2SnEdgeList = new ArrayList<Si2SnEdge>();
		Map<Long, SentenceIdentifier> sentenceIdentifierMap = new HashMap<Long, SentenceIdentifier>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Long> rootNodes = new ArrayList<Long>();

		//List<Long> rootNodeIdList = si2SnEdgeDAO.getRootNodeIdList();
		Map<Long, Long> rootNodeIdGraphIdMap = si2SnEdgeDAO.getRootNodeIdGraphIdMap(command.getDefaultGraphIdList());

		if (rootNodeIdGraphIdMap == null || rootNodeIdGraphIdMap.isEmpty() == true) {
			return null;
		}

		//Map<Integer, List<Node>> graphIdNodeMap = getGraphIdNodeMap(command);

		List<Long> rootNodeIdList = new ArrayList<Long>();
		rootNodeIdList.addAll(rootNodeIdGraphIdMap.keySet());

		List<Si2SnNode> parentSi2SnNodeList = si2SnNodeDAO.getSi2SnNodeByNodeId(rootNodeIdList);

		//List<Si2SnNode> parentSi2SnNodeList = si2SnNodeDAO.getSi2SnNodeByConceptId(command);

		List<Node> nodeList = new ArrayList<Node>();
		Node rootNode = null;

		for (Si2SnNode sSi2SnNode : parentSi2SnNodeList) {

			rootNodes.add(sSi2SnNode.getNodeId());

			//si2SnNodeList.add(sSi2SnNode);

			rootNode = new Node(sSi2SnNode);
			rootNode.setIsRoot(true);
			//rootNode.setHlink("http://bl.ocks.org/Guerino1/2879486");
			//rootNode.setTitle("Dinesh");
			nodeList.add(rootNode);

			if (filterConceptIds.contains(rootNode.getNode().getConceptId())) {
				rootNode.setRadius(CommonConstants.DEFAULT_WEIGHT_DOT);
			}

			idNode.put(sSi2SnNode.getNodeId().intValue(), rootNode);

			newsIds.add(sSi2SnNode.getNewsId());

			List<Object[]> list = si2SnNodeDAO.getGraphDataMap(sSi2SnNode.getNodeId(), rootNodeIdGraphIdMap.get(sSi2SnNode.getNodeId()));
			fillListValue(list, si2SnNodeList, si2SnEdgeList, sentenceIdentifierMap, newsIds);
		}

		List<Object[]> result = si2SnNodeDAO.getNewDetails(newsIds);

		Map<Long, NewsDetails> newsIdDetails = mapNewsDetails(result);

		List<Node> tempNodeList = createNodeList(si2SnNodeList, si2SnEdgeList, sentenceIdentifierMap, newsIdDetails, rootNodes, filterConceptIds);

		if (tempNodeList != null && tempNodeList.isEmpty() == false) {
			nodeList.addAll(tempNodeList);
		}

		for (Node node : nodeList) {
			idNode.put(node.getNodeId(), node);
		}

		map.put("idNode", idNode);

		List<Node> childNodeList = null;
		List<Long> childNodeIdList = null;

		List<Integer> allNodes = new ArrayList<Integer>();

		//si2SnEdgeList = si2SnEdgeDAO.getSi2SnEdgeForGraphId(command.getDefaultGraphIdList());

		for (Node node : nodeList) {
			if (allNodes.contains(node.getNodeId()) == false) {
				allNodes.add(node.getNodeId());
				childNodeIdList = getChildNodeIdList(node.getNodeId(), si2SnEdgeList);
				childNodeList = new ArrayList<Node>();
				for (Long childNodeId : childNodeIdList) {
					Node tempNode = idNode.get(childNodeId.intValue());
					if (tempNode != null) {
						tempNode.setCuePhrase(getCuePhrase(tempNode.getNodeId(), si2SnEdgeList));
						childNodeList.add(tempNode);
					}
				}
				parentChild.put(node.getNodeId(), childNodeList);
			}
		}
		map.put("parentChild", parentChild);

		return map;
	}*/

	/*private void getChilde(Node node, Set<Integer> doneNodes, Map<Integer, Node> nodeIdNodeMapping, List<Si2SnEdge> si2SnEdgeList) {

		if (!doneNodes.contains(node.getNodeId())) {
			doneNodes.add(node.getNodeId());

			System.out.println(node.getNodeId());

			List<Node> children = getChildNodes(node.getNodeId(), nodeIdNodeMapping, si2SnEdgeList);
			node.setChildren(children, null);

			nodeIdNodeMapping.put(node.getNodeId(), node);

			if (children != null && children.isEmpty() == false) {
				for (Node tmpNode : children) {
					if (!doneNodes.contains(tmpNode.getNodeId())) {
						//doneNodes.add(tmpNode.getNodeId());
						getChilde(tmpNode, doneNodes, nodeIdNodeMapping, si2SnEdgeList);
					}
				}
			}
		} else {
			return;
		}

	}*/

	private void getChilde(Node node, Set<Integer> doneNode, Map<Integer, Node> nodeIdNodeMapping, List<Si2SnEdge> si2SnEdgeList) {

		if (doneNode.contains(node.getNodeId())) {
			return;
		} else {

			doneNode.add(node.getNodeId());

			List<Node> children = getChildNodes(node.getNodeId(), nodeIdNodeMapping, si2SnEdgeList);

			if (children != null && children.isEmpty() == false) {
				node.setChildren(children, null);
				nodeIdNodeMapping.put(node.getNodeId(), node);
				for (Node tmpNode : children) {
					if (doneNode.contains(tmpNode.getNodeId())) {
						return;
					} else {
						getChilde(tmpNode, doneNode, nodeIdNodeMapping, si2SnEdgeList);
					}
				}

			} else {
				return;
			}
		}
	}

	/*private void getChilde(Node node, Set<Integer> doneNode, Map<Integer, Node> nodeIdNodeMapping, List<Si2SnEdge> si2SnEdgeList) {

		if(doneNode.contains(node.getNodeId())) {
			return;
		}
		
		doneNode.add(node.getNodeId());
		
		List<Node> children = getChildNodes(node.getNodeId(), nodeIdNodeMapping, si2SnEdgeList);

		if (children != null && children.isEmpty() == false) {
			node.setChildren(children, null);
			nodeIdNodeMapping.put(node.getNodeId(), node);
			for (Node tmpNode : children) {
				getChilde(tmpNode, doneNode, nodeIdNodeMapping, si2SnEdgeList);
			}

		}

	}*/

	private List<Node> getChildNodes(Integer nodeId, Map<Integer, Node> nodeIdNodeMapping, List<Si2SnEdge> si2SnEdgeList) {

		List<Node> childNodeIdList = new ArrayList<Node>();

		for (Si2SnEdge si2SnEdge : si2SnEdgeList) {
			if (si2SnEdge.getSourceNodeId().intValue() == nodeId && nodeIdNodeMapping.containsKey(si2SnEdge.getDestinationNodeId().intValue())) {
				childNodeIdList.add(nodeIdNodeMapping.get(si2SnEdge.getDestinationNodeId().intValue()));
			}
		}

		return childNodeIdList;
	}

	private Map<Integer, Node> getNodeIdNodeMapping(List<Node> allNodes, Map<Integer, Node> nodeIdNodeMapping) {
		nodeIdNodeMapping.clear();

		for (Node node : allNodes) {
			nodeIdNodeMapping.put(node.getNodeId(), node);
		}

		return nodeIdNodeMapping;
	}

	private List<Node> getNodesByIdallNodes(Set<Integer> allNodesIds) {

		List<Node> node = new ArrayList<Node>();
		List<Long> nodeIds = new ArrayList<Long>();

		for (Integer integer : allNodesIds) {
			nodeIds.add(new Long(integer));
		}

		List<Si2SnNode> Si2Snnodes = si2SnNodeDAO.getSi2SnNodeByNodeId(nodeIds);
		for (Si2SnNode si2SnNode : Si2Snnodes) {
			node.add(new Node(si2SnNode));
		}

		return node;
	}

	private Map<Integer, List<Node>> getGraphIdNodeMap(Command command, List<Object[]> result) {

		Node node;
		Integer graphId;
		Integer nodeId;

		Map<Integer, List<Node>> graphIdNodeMap = new HashMap<Integer, List<Node>>();

		for (Object[] objects : result) {
			graphId = objects[1] != null ? Integer.parseInt(objects[1].toString()) : -1;

			if (!graphIdNodeMap.containsKey(graphId)) {
				graphIdNodeMap.put(graphId, new ArrayList<Node>());
			}

			nodeId = objects[0] != null ? Integer.parseInt(objects[0].toString()) : -1;
			node = new Node();
			node.setNodeId(nodeId);

			graphIdNodeMap.get(graphId).add(node);

		}

		return graphIdNodeMap;
	}

	private String getCuePhrase(Integer nodeId, List<Si2SnEdge> si2SnEdgeList) {
		String cuePhrase = "";
		for (Si2SnEdge si2SnEdge : si2SnEdgeList) {
			if (si2SnEdge.getDestinationNodeId().intValue() == nodeId) {
				cuePhrase = si2SnEdge.getCuePhrase();
			}
		}
		return cuePhrase;
	}

	private List<Long> getChildNodeIdList(int nodeId, List<Si2SnEdge> si2SnEdgeList) {
		List<Long> childNodeIdList = new ArrayList<Long>();
		for (Si2SnEdge si2SnEdge : si2SnEdgeList) {
			if (si2SnEdge.getSourceNodeId().intValue() == nodeId) {
				if (childNodeIdList.contains(si2SnEdge.getDestinationNodeId()) == false) {
					childNodeIdList.add(si2SnEdge.getDestinationNodeId());
				}
			}
		}
		return childNodeIdList;
	}

	private List<Node> createNodeList(List<Si2SnNode> si2SnNodeList, List<Si2SnEdge> si2SnEdgeList, Map<Long, SentenceIdentifier> sentenceIdentifierMap, Map<Long, NewsDetails> newsIdDetails, List<Long> rootNodes, List<Long> filterConceptIds) {
		List<Node> nodeList = new ArrayList<Node>();
		Node node = null;
		String sentence = null;

		Long newsId;
		NewsDetails newsDetail;

		for (Si2SnNode si2SnNode : si2SnNodeList) {

			node = new Node(si2SnNode);

			if (rootNodes.contains(si2SnNode.getNodeId())) {
				node.setIsRoot(true);
			} else {
				node.setIsRoot(false);
			}

			newsId = si2SnNode.getNewsId();
			if (newsId != null && newsIdDetails.containsKey(newsId)) {

				newsDetail = newsIdDetails.get(newsId);
				node.setTitle(newsDetail.getTitle());
				node.setHlink(newsDetail.getUrl());
			}

			node.setNodeName(si2SnNode.getRawConcept());
			node.setNodeId(Long.valueOf(si2SnNode.getNodeId()).intValue());
			//node.setLevel(null);
			//node.setWeight(weight);
			//node.setColor(color);
			//node.setExpand(expand);

			if (sentenceIdentifierMap.containsKey(si2SnNode.getSentenceId()))
				sentence = sentenceIdentifierMap.get(si2SnNode.getSentenceId()).getSentence();

			node.setSentence(sentence == null ? "" : sentence);
			nodeList.add(node);
		}

		return nodeList;
	}

	private void fillListValue(List<Object[]> list, List<Si2SnNode> si2SnNodeList, List<Si2SnEdge> si2SnEdgeList, Map<Long, SentenceIdentifier> sentenceIdentifierMap, Set<Long> newsIds) {
		Si2SnNode si2SnNode = null;
		Si2SnEdge si2SnEdge = null;
		SentenceIdentifier sentenceIdentifier = null;

		for (Object[] obj : list) {

			try {

				si2SnNode = new Si2SnNode();
				si2SnNode.setNodeId(Long.parseLong(obj[0].toString()));

				si2SnNode.setNewsId(Long.parseLong(obj[1].toString()));
				newsIds.add(si2SnNode.getNewsId());

				si2SnNode.setIsPdf(obj[2] == null ? null : obj[2].toString());
				si2SnNode.setIsWebsearch(obj[3] == null ? null : obj[3].toString());
				si2SnNode.setIsTwitter(obj[4] == null ? null : obj[4].toString());

				si2SnNode.setConceptId(obj[5] == null ? null : Long.parseLong(obj[5].toString()));

				si2SnNode.setRawConcept(obj[6] == null ? null : obj[6].toString());
				si2SnNode.setSentenceId(obj[7] == null ? null : Long.parseLong(obj[7].toString()));
				si2SnNode.setEntityType(obj[8] == null ? null : obj[8].toString());
				si2SnNode.setConceptTokens(obj[9] == null ? null : obj[9].toString());
				si2SnNodeList.add(si2SnNode);

				si2SnEdge = new Si2SnEdge();
				si2SnEdge.setEdgeId(Long.parseLong(obj[10].toString()));
				si2SnEdge.setSourceNodeId(obj[11] == null ? null : Long.parseLong(obj[11].toString()));
				si2SnEdge.setDestinationNodeId(obj[12] == null ? null : Long.parseLong(obj[12].toString()));
				si2SnEdge.setEdgeName(obj[13] == null ? null : obj[13].toString());
				si2SnEdge.setEdgeType(obj[14] == null ? null : obj[14].toString());
				si2SnEdge.setCuePhrase(obj[15] == null ? null : obj[15].toString());
				si2SnEdgeList.add(si2SnEdge);

				sentenceIdentifier = new SentenceIdentifier();
				sentenceIdentifier.setSentenceId(Long.parseLong(obj[0].toString()));
				sentenceIdentifier.setSentence(obj[6] == null ? null : obj[6].toString());
				/*sentenceIdentifier.setSentenceId(obj[16] != null ? Long.parseLong(obj[16].toString()) : -1l);
				sentenceIdentifier.setSentence(obj[17] == null ? null : obj[17].toString());*/
				sentenceIdentifierMap.put(sentenceIdentifier.getSentenceId(), sentenceIdentifier);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Long> getConceptsIds(Command command) {
		return si2SnNodeDAO.getConceptsIds(command);
	}
}