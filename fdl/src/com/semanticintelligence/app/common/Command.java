package com.semanticintelligence.app.common;

import java.util.ArrayList;
import java.util.List;

public class Command {

	private List<Long> companyIds = new ArrayList<Long>();

	private List<Long> geoIds = new ArrayList<Long>();

	private List<Long> regulatoryBodiesIds = new ArrayList<Long>();

	private List<Long> topicIds = new ArrayList<Long>();

	private Integer timePeriod;

	private String searchText;

	private List<Long> conceptIdList = new ArrayList<Long>();

	private List<Long> defaultGraphIdList = new ArrayList<Long>();

	private Long sourceNodeId;

	private Long targetNodeId;

	private String param;

	private String graphType;

	private Long document;

	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
	}

	public Long getSourceNodeId() {
		return sourceNodeId;
	}

	public void setSourceNodeId(Long sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}

	public Long getTargetNodeId() {
		return targetNodeId;
	}

	public void setTargetNodeId(Long targetNodeId) {
		this.targetNodeId = targetNodeId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public List<Long> getDefaultGraphIdList() {
		return defaultGraphIdList;
	}

	public void setDefaultGraphIdList(List<Long> defaultGraphIdList) {
		this.defaultGraphIdList = defaultGraphIdList;
	}

	public List<Long> getRegulatoryBodiesIds() {
		return regulatoryBodiesIds;
	}

	public void setRegulatoryBodiesIds(List<Long> regulatoryBodiesIds) {
		this.regulatoryBodiesIds = regulatoryBodiesIds;
	}

	public List<Long> getTopicIds() {
		return topicIds;
	}

	public void setTopicIds(List<Long> topicIds) {
		this.topicIds = topicIds;
	}

	public List<Long> getConceptIdList() {
		return conceptIdList;
	}

	public void setConceptIdList(List<Long> conceptIdList) {
		this.conceptIdList = conceptIdList;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List<Long> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Long> companyIds) {
		this.companyIds = companyIds;
	}

	public List<Long> getGeoIds() {
		return geoIds;
	}

	public void setGeoIds(List<Long> geoIds) {
		this.geoIds = geoIds;
	}

	public Integer getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(Integer timePeriod) {
		this.timePeriod = timePeriod;
	}

}
