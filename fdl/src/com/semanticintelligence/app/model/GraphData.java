package com.semanticintelligence.app.model;

import java.util.ArrayList;

public class GraphData {

	private String name;

	private int size;

	private ArrayList<GraphData> children;

	/**
	 * @param name
	 * @param size
	 */
	public GraphData(String name, int size) {
		super();
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<GraphData> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<GraphData> children) {
		this.children = children;
	}

}
