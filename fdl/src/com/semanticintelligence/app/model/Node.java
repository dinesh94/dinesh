package com.semanticintelligence.app.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.rage.siapp.nlp.tools.network.graph.normalize.DBNode;
import com.semanticintelligence.app.common.CommonConstants;
import com.semanticintelligence.app.common.MasterData;
import com.semanticintelligence.app.domain.Si2SnConceptAttr;
import com.semanticintelligence.app.domain.Si2SnNode;

public class Node {

	@JsonIgnore
	private Si2SnNode node;

	private String nodeName;

	private Integer nodeId;

	private Integer level;

	private Integer weight;

	private Boolean expand;

	private String entityType;

	private String sentence;

	private String title;

	private String hlink;

	private String cuePhrase;

	private Boolean isRoot;

	private Integer id;

	private Integer radius;

	private List<Node> children;

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHlink() {
		return hlink;
	}

	public void setHlink(String hlink) {
		this.hlink = hlink;
	}

	public Boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	public Node(String nodeName, int nodeId, int level, String entityType, boolean expand) {
		this.nodeName = nodeName;
		this.nodeId = nodeId;
		this.level = level;
		this.entityType = entityType;
		this.expand = expand;

		this.sentence = " Weight :  " + getWeight();
	}

	public Node() {
		// TODO Auto-generated constructor stub
	}

	public Node(Si2SnNode sSi2SnNode) {
		this.node = sSi2SnNode;
		this.nodeId = sSi2SnNode.getNodeId().intValue();
		this.id = sSi2SnNode.getNodeId().intValue();
		this.nodeName = sSi2SnNode.getRawConcept();

		this.level = 0;

		if (MasterData.conceptIdAttrMap.containsKey(sSi2SnNode.getConceptId())) {
			List<Si2SnConceptAttr> conceptAttrs = MasterData.conceptIdAttrMap.get(sSi2SnNode.getConceptId());
			for (Si2SnConceptAttr si2SnConceptAttr : conceptAttrs) {
				if (si2SnConceptAttr.getKey().equalsIgnoreCase(CommonConstants.KEY_ENTITY_TYPE)) {
					setEntityType(si2SnConceptAttr.getValue());
					break;
				}

			}
		} else {
			setEntityType(sSi2SnNode.getEntityType());
		}

		this.expand = true;
		this.sentence = " Weight :  " + getWeight();
		//this.isRoot = true;
	}

	public Node(DBNode mainNode) {
		this.nodeId = mainNode.getNodeID().intValue();
		this.id = this.nodeId;
		this.nodeName = mainNode.getNormalizedConcept();
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight, List<Long> filterConceptIds) {
		if (this.entityType != null && this.entityType.equalsIgnoreCase(CommonConstants.ENTITY_TO_IGNODE)) {
			this.weight = CommonConstants.DEFAULT_WEIGHT_DOT;
			this.radius = CommonConstants.DEFAULT_WEIGHT_DOT;
		} else {
			this.weight = weight;
			this.radius = weight;
		}

		if (filterConceptIds != null && filterConceptIds.size() > 0 && this.getNode() != null && !filterConceptIds.contains(this.getNode().getConceptId())) {
			this.weight = CommonConstants.DEFAULT_WEIGHT_DOT;
			this.radius = CommonConstants.DEFAULT_WEIGHT_DOT;
		}

		if (this.weight > 10)
			this.weight = 5;
	}

	public String getCuePhrase() {
		return cuePhrase;
	}

	public void setCuePhrase(String cuePhrase) {
		this.cuePhrase = cuePhrase;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the nodeId
	 */
	public Integer getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
		this.id = nodeId;
	}

	public Si2SnNode getNode() {
		return node;
	}

	public void setNode(Si2SnNode node) {
		this.node = node;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children, List<Long> filterConceptIds) {
		this.children = children;
		updateWeight(filterConceptIds);
	}

	/*@Override
	public String toString() {
		return "Node [nodeName=" + nodeName + ",  id=" + id + ", radius=" + radius + " nodeId=" + nodeId + ", level=" + level + ", weight=" + weight + ", expand=" + expand + ", entityType=" + entityType + ", sentence=" + sentence + ", title=" + title + ", hlink=" + hlink + ", cuePhrase=" + cuePhrase + ", isRoot=" + isRoot + ", children=" + children + "]";
	}*/

	public void updateWeight(List<Long> filterConceptIds) {
		int weight = CommonConstants.DEFAULT_WEIGHT;
		if (children != null) {
			weight = CommonConstants.DEFAULT_WEIGHT + (2 * children.size());
		}

		setWeight(weight, filterConceptIds);
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", children=" + children + "]";
	}

	/*public void updateWeight(List<Node> list) {
		int weight = CommonConstants.DEFAULT_WEIGHT;
		if (list != null) {
			weight = CommonConstants.DEFAULT_WEIGHT + (2 * list.size());
		}

		this.weight = weight;
	}*/
}
