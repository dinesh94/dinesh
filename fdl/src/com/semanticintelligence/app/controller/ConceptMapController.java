package com.semanticintelligence.app.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rage.siapp.db.DatabaseConnection;
import com.rage.siapp.nlp.tools.network.graph.db.DBGraphCreater;
import com.rage.siapp.nlp.tools.network.graph.ui.JsonExpressionCreater;
import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.common.CommonConstants;
import com.semanticintelligence.app.domain.Si2SnAttr;
import com.semanticintelligence.app.domain.Si2SnEdge;
import com.semanticintelligence.app.domain.Si2SnNode;
import com.semanticintelligence.app.model.GraphData;
import com.semanticintelligence.app.model.Node;
import com.semanticintelligence.app.service.Si2DocNetworkMapService;
import com.semanticintelligence.app.service.Si2SnAttrService;
import com.semanticintelligence.app.service.Si2SnEdgeService;
import com.semanticintelligence.app.service.Si2SnNodeService;
import com.semanticintelligence.app.util.SIUtil;

@Controller
public class ConceptMapController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private Si2SnNodeService si2SnNodeService;

	@Resource
	private Si2SnAttrService si2SnAttrService;

	@Resource
	private Si2SnEdgeService si2SnEdgeService;

	@Resource
	private Si2DocNetworkMapService si2DocNetworkMapService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/concept-map")
	public String conceptMap(Command command, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		// TODO 
		/* 
			1. Look for attribute in concept_attr based on key & value
			2. Match concept_id's with concept & get all concepts & it's names 
			3. Get nodes by concept_id's from node
			4. Look senteces based on sentence_id from node table
			5. Look for relations by node_id in node table, based on we will form edges
		*/

		/*List<Long> conceptIdList = si2SnNodeService.getConceptsIds(command);

		command.setConceptIdList(conceptIdList);*/

		Node tmpNode;
		List<Node> children;

		Map<Integer, Node> idNodeWithChildren = new HashMap<Integer, Node>();

		Map<String, Object> graphDataMap = si2SnNodeService.getGraphDataMap(command, new ArrayList<Long>());

		Map<Integer, Node> idNode = (HashMap<Integer, Node>) graphDataMap.get("idNode");
		Map<Integer, List<Node>> parentChild = (HashMap<Integer, List<Node>>) graphDataMap.get("parentChild");

		List<Integer> rootNodes = new ArrayList<Integer>();
		Integer weight = null;
		for (Map.Entry<Integer, Node> map : idNode.entrySet()) {
			//parentChild.get(map.getValue())
			weight = parentChild.get(map.getValue().getNodeId()) == null ? CommonConstants.DEFAULT_WEIGHT : CommonConstants.DEFAULT_WEIGHT + (2 * parentChild.get(map.getValue().getNodeId()).size());
			map.getValue().setWeight(weight, null);
			if (map.getValue().getIsRoot() != null && map.getValue().getIsRoot() == true) {
				rootNodes.add(map.getValue().getNodeId());
			}

			children = parentChild.get(map.getKey());
			tmpNode = map.getValue();
			tmpNode.setChildren(children, null);

			idNodeWithChildren.put(tmpNode.getNodeId(), tmpNode);
		}

		Gson gson = new Gson();

		//Map<Integer, Node> idNode = new HashMap<Integer, Node>();
		//Map<Integer, List<Node>> parentChild = new HashMap<Integer, List<Node>>();

		//fillDummyData(idNode, parentChild);

		model.put("rootNodeList", gson.toJson(rootNodes));

		model.put("idNode", gson.toJson(idNodeWithChildren));
		model.put("parentChild", gson.toJson(parentChild));

		//System.out.println("ConceptMapController.conceptMap() gson.toJson(rootNodes)");
		//System.out.println(gson.toJson(rootNodes));

		//System.out.println("ConceptMapController.conceptMap() gson.toJson(idNode)");
		//System.out.println(gson.toJson(idNode));

		//System.out.println("ConceptMapController.conceptMap()  gson.toJson(parentChild)");
		//System.out.println(gson.toJson(parentChild));

		request.getSession().removeAttribute("rootNodeList");
		request.getSession().setAttribute("rootNodeList", rootNodes);

		request.getSession().removeAttribute("idNode");
		request.getSession().setAttribute("idNode", idNode);

		request.getSession().removeAttribute("parentChild");
		request.getSession().setAttribute("parentChild", parentChild);

		return "/clollapse-works";
		//return "/concept-map";
	}

	@RequestMapping(value = "/landin-graph")
	public String landinGraph(Command command, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("command", command);

		Connection conn = DatabaseConnection.getConnection();

		Long graphId;
		if (command.getDocument() == null) {
			graphId = command.getDefaultGraphIdList().get(0);
		} else {
			graphId = si2DocNetworkMapService.getGraphIdByDocumentAndType(command);
		}

		DBGraphCreater result = new DBGraphCreater(conn, graphId);
		result.run();

		List<Long> filterConceptIds = new ArrayList<Long>();
		filterConceptIds.addAll(command.getCompanyIds());
		filterConceptIds.addAll(command.getGeoIds());
		filterConceptIds.addAll(command.getTopicIds());
		filterConceptIds.addAll(command.getRegulatoryBodiesIds());

		model.put("rootNodes", graphId);

		String json = "{" + JsonExpressionCreater.createJsonExpression(result.getMainNodes().get(0), filterConceptIds) + "}";
		model.put("nodes", json);

		System.out.println(json);

		return "/landin-graph";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/json-concept-map")
	public @ResponseBody
	Map<String, ? extends Object> jsonConceptMapNew(Command command, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = null;
		modelMap = SIUtil.getModelMapSuccess("Success");

		/*Connection conn = DatabaseConnection.getConnection();

		Long graphId = command.getDefaultGraphIdList().get(0);
		DBGraphCreater result = new DBGraphCreater(conn, graphId);
		result.run();

		modelMap.put("rootNodes", 126);
		modelMap.put("nodes", result.getMainNodes().get(0));*/

		return modelMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/json-concept-map-old")
	public @ResponseBody
	Map<String, ? extends Object> jsonConceptMap(Command command, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		List<Long> filterConceptIds = new ArrayList<Long>();
		filterConceptIds.addAll(command.getCompanyIds());
		filterConceptIds.addAll(command.getGeoIds());
		filterConceptIds.addAll(command.getTopicIds());
		filterConceptIds.addAll(command.getRegulatoryBodiesIds());

		System.out.println("ConceptMapController.jsonConceptMap() filterConceptIds = " + filterConceptIds.toString());

		Node tmpNode;
		List<Node> children;

		List<Node> rootNodes = new ArrayList<Node>();

		Map<Integer, Node> idNodeWithChildren = new HashMap<Integer, Node>();

		Map<String, Object> graphDataMap = si2SnNodeService.getGraphDataMap(command, filterConceptIds);

		Map<Integer, Node> idNode = (HashMap<Integer, Node>) graphDataMap.get("idNode");
		//Map<Integer, List<Node>> parentChild = (HashMap<Integer, List<Node>>) graphDataMap.get("parentChild");
		rootNodes.add((Node) graphDataMap.get("rootNodes"));

		List<Integer> rootNodeIds = new ArrayList<Integer>();
		Integer weight = null;

		Connection conn = DatabaseConnection.getConnection();

		DBGraphCreater result = new DBGraphCreater(conn, (long) 126);
		result.run();

		/*for (Map.Entry<Integer, Node> map : idNode.entrySet()) {

			//parentChild.get(map.getValue())

			children = parentChild.get(map.getKey());
			tmpNode = map.getValue();
			tmpNode.setChildren(children, filterConceptIds);

			weight = parentChild.get(map.getValue().getNodeId()) == null ? CommonConstants.DEFAULT_WEIGHT : CommonConstants.DEFAULT_WEIGHT + (2 * parentChild.get(map.getValue().getNodeId()).size());
			tmpNode.setWeight(weight, filterConceptIds);

			idNodeWithChildren.put(tmpNode.getNodeId(), tmpNode);

			if (tmpNode.getIsRoot() != null && tmpNode.getIsRoot() == true) {
				rootNodeIds.add(tmpNode.getNodeId());
				tmpNode.setCuePhrase("");
				rootNodes.add(tmpNode);
			}
		}
		*/
		Node node = new Node();
		node.setNodeId(-1);
		node.setId(-1);

		node.setChildren(rootNodes, filterConceptIds);
		node.setRadius(0);
		node.setWeight(0, null);
		rootNodeIds.add(-1);

		List<Node> rootNode = new ArrayList<Node>();
		rootNode.add(node);

		Map<String, Object> modelMap = null;
		modelMap = SIUtil.getModelMapSuccess("Success");

		modelMap.put("rootNodes", rootNodeIds);
		modelMap.put("nodes", rootNode);

		/*for (Node Node : rootNodes) {
			System.out.println(new Gson().toJson(Node));
			System.out.println("");
		}*/

		//modelMap.put("nodes", idNodeWithChildren);
		//modelMap.put("parentChild", parentChild);

		return modelMap;
	}

	@RequestMapping(value = "/expand-node")
	@SuppressWarnings("unchecked")
	public String expandNode(Integer nodeId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		Gson gson = new Gson();

		Map<Integer, Node> idNode = (Map<Integer, Node>) request.getSession().getAttribute("idNode");
		Map<Integer, List<Node>> parentChild = (Map<Integer, List<Node>>) request.getSession().getAttribute("parentChild");

		Node tmpNode = idNode.get(nodeId);

		if (tmpNode.getExpand() == null || tmpNode.getExpand() == false)
			tmpNode.setExpand(true);
		else
			tmpNode.setExpand(false);

		idNode.put(nodeId, tmpNode);

		//System.out.println("ConceptMapController.expandNode() gson.toJson(idNode) = " + gson.toJson(idNode));

		//System.out.println("ConceptMapController.expandNode() gson.toJson(parentChild) = " + gson.toJson(parentChild));

		model.put("idNode", gson.toJson(idNode));
		model.put("parentChild", gson.toJson(parentChild));

		request.getSession().removeAttribute("idNode");
		request.getSession().setAttribute("idNode", idNode);

		request.getSession().removeAttribute("parentChild");
		request.getSession().setAttribute("parentChild", parentChild);

		List<Integer> rootNodes = (List<Integer>) request.getSession().getAttribute("rootNodeList");
		model.put("rootNodeList", rootNodes);

		return "/concept-map";
	}

	@RequestMapping(value = "/node-information")
	public @ResponseBody
	Map<String, ? extends Object> nodeInformation(Integer nodeId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = null;

		modelMap = SIUtil.getModelMapSuccess("Success");

		List<Long> referringId = new ArrayList<Long>();
		referringId.add(new Long(nodeId));

		Map<String, Object> data = si2SnAttrService.getByNodeId(referringId);

		List<Si2SnNode> nodes = (List<Si2SnNode>) data.get("node_sentence");
		List<Si2SnAttr> attrList = (List<Si2SnAttr>) data.get("attrList");

		String val;
		List<String> attrValues = new ArrayList<String>();
		List<Si2SnAttr> filteredAttrList = new ArrayList<Si2SnAttr>();

		for (Si2SnAttr si2SnAttr : attrList) {
			if (si2SnAttr.getValue() != null) {
				val = si2SnAttr.getValue().toLowerCase().trim();
				if (!attrValues.contains(val)) {
					attrValues.add(val);
					filteredAttrList.add(si2SnAttr);
				}
			}
		}

		modelMap.put("node", nodes);
		modelMap.put("nodeAttr", filteredAttrList);

		return modelMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/edge-information")
	public @ResponseBody
	Map<String, ? extends Object> edgeInformation(Command command, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = null;

		modelMap = SIUtil.getModelMapSuccess("Success");

		List<Long> referringId = new ArrayList<Long>();

		List<Si2SnEdge> edges = si2SnEdgeService.getBySourceTargetId(command);

		for (Si2SnEdge si2SnEdge : edges) {
			referringId.add(si2SnEdge.getEdgeId());
		}

		/*referringId.add(command.getSourceNodeId());
		referringId.add(command.getTargetNodeId());*/
		
		List<Si2SnNode> nodes = new ArrayList<Si2SnNode>();
		List<Si2SnAttr> attrList = new ArrayList<Si2SnAttr>();
		List<Si2SnAttr> filteredAttrList = new ArrayList<Si2SnAttr>();
		Set<Long> documentIds = new HashSet<Long>();
		List<String> titles = new ArrayList<String>();

		if (referringId.size() > 0) {

			Map<String, Object> data = si2SnAttrService.getDataForEdge(referringId);

			titles = (List<String>) data.get("titles");
			documentIds = (Set<Long>) data.get("documentIds");
			nodes = (List<Si2SnNode>) data.get("node_sentence");
			attrList = (List<Si2SnAttr>) data.get("attrList");

			String val;
			List<String> attrValues = new ArrayList<String>();
			filteredAttrList = new ArrayList<Si2SnAttr>();

			for (Si2SnAttr si2SnAttr : attrList) {
				if (si2SnAttr.getValue() != null) {
					val = si2SnAttr.getValue().toLowerCase().trim();
					if (!attrValues.contains(val)) {
						attrValues.add(val);
						filteredAttrList.add(si2SnAttr);
					}
				}
			}
		}

		modelMap.put("totalEdges", edges.size());
		modelMap.put("node", nodes);
		modelMap.put("nodeAttr", filteredAttrList);
		modelMap.put("documentIds", documentIds);
		modelMap.put("titles", titles);

		return modelMap;
	}

	private void fillDummyData(Map<Integer, Node> idNode, Map<Integer, List<Node>> parentChild) {
		List<Node> tmpNodeList = null;

		idNode.put(0, new Node("UBS", 0, 0, CommonConstants.Entity_COMPANY, true));
		idNode.put(1, new Node("result 1Q12", 1, 1, CommonConstants.Entity_GEO, false));
		idNode.put(2, new Node("wealth management solid", 2, 1, CommonConstants.Entity_TOPIC, false));
		idNode.put(3, new Node("investment banking", 3, 1, CommonConstants.Entity_REGULATORY_BODY, false));
		idNode.put(4, new Node("retail and corporate banking", 4, 1, CommonConstants.Entity_REGULATORY_BODY, false));
		idNode.put(5, new Node("RWA ( reduction )", 5, 1, CommonConstants.Entity_GEO, false));
		idNode.put(6, new Node("Balance sheet", 6, 1, CommonConstants.Entity_TOPIC, false));

		for (int i = 1; i < 7; i++) {

			if (idNode.get(0).getChildren() == null)
				idNode.get(0).setChildren(new ArrayList<Node>(), null);

			idNode.get(0).getChildren().add(idNode.get(i));
		}

		tmpNodeList = new ArrayList<Node>();
		tmpNodeList.add(new Node("result 1Q12", 1, 1, CommonConstants.Entity_TOPIC, false));
		tmpNodeList.add(new Node("wealth management solid", 2, 1, CommonConstants.Entity_TOPIC, false));
		tmpNodeList.add(new Node("investment banking", 3, 1, CommonConstants.Entity_GEO, false));
		tmpNodeList.add(new Node("retail and corporate banking", 4, 1, CommonConstants.Entity_GEO, false));
		tmpNodeList.add(new Node("RWA ( reduction )", 5, 1, CommonConstants.Entity_REGULATORY_BODY, false));
		tmpNodeList.add(new Node("Balance sheet", 6, 1, CommonConstants.Entity_COMPANY, false));
		parentChild.put(0, tmpNodeList);

		int cnt = 7;
		for (int i = 1; i < 7; i++) {

			tmpNodeList = new ArrayList<Node>();

			idNode.put(cnt, new Node("result 1Q12", cnt, i, CommonConstants.Entity_REGULATORY_BODY, false));
			tmpNodeList.add(idNode.get(cnt));
			cnt++;

			idNode.put(cnt, new Node("wealth management solid", cnt, i, CommonConstants.Entity_REGULATORY_BODY, false));
			tmpNodeList.add(idNode.get(cnt));
			cnt++;

			idNode.put(cnt, new Node("investment banking", cnt, i, CommonConstants.Entity_REGULATORY_BODY, false));
			tmpNodeList.add(idNode.get(cnt));
			cnt++;

			if (i % 2 == 0) {
				idNode.put(cnt, new Node("retail and corporate banking", cnt, i, CommonConstants.Entity_REGULATORY_BODY, false));
				tmpNodeList.add(idNode.get(cnt));
				cnt++;

				idNode.put(cnt, new Node("RWA ( reduction )", cnt, i, CommonConstants.Entity_REGULATORY_BODY, false));
				tmpNodeList.add(idNode.get(cnt));
				cnt++;

				idNode.put(cnt, new Node("Balance sheet", cnt, i, CommonConstants.Entity_REGULATORY_BODY, false));
				tmpNodeList.add(idNode.get(cnt));
				cnt++;
			}

			parentChild.put(i, tmpNodeList);

			idNode.get(i).setChildren(tmpNodeList, null);
		}

	}

	@RequestMapping(value = "/graph.htm")
	public @ResponseBody
	Map<String, ? extends Object> graphData(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> modelMap = null;

		modelMap = SIUtil.getModelMapSuccess("Expression saved successfully.");

		ArrayList<GraphData> children = null;

		GraphData data = new GraphData("UBS", 743);

		children = new ArrayList<GraphData>();
		children.add(new GraphData("RBS", 743));

		data.setChildren(children);

		modelMap.put("json", data);

		return modelMap;
	}
}
