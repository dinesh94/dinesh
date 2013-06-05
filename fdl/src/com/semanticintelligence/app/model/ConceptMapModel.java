package com.semanticintelligence.app.model;

import java.util.List;
import java.util.Map;

public class ConceptMapModel {

	private Map<Integer, List<Node>> levelNodes;

	private Map<Integer, List<Edge>> levelEdges;

	public Map<Integer, List<Node>> getLevelNodes() {
		return levelNodes;
	}

	public void setLevelNodes(Map<Integer, List<Node>> levelNodes) {
		this.levelNodes = levelNodes;
	}

	public Map<Integer, List<Edge>> getLevelEdges() {
		return levelEdges;
	}

	public void setLevelEdges(Map<Integer, List<Edge>> levelEdges) {
		this.levelEdges = levelEdges;
	}

}
