package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SI_2_SN_EDGE database table.
 * 
 */
@Entity
@Table(name = "SI_2_SN_EDGE")
public class Si2SnEdge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SI_2_SN_EDGE_EDGEID_GENERATOR", sequenceName = "SEQ_SI_2_SN_EDGE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SI_2_SN_EDGE_EDGEID_GENERATOR")
	@Column(name = "EDGE_ID")
	private long edgeId;

	@Column(name = "CUE_PHRASE")
	private String cuePhrase;

	@Column(name = "EDGE_NAME")
	private String edgeName;

	@Column(name = "EDGE_TYPE")
	private String edgeType;

	@Column(name = "DESTINATION_NODE_ID")
	private Long destinationNodeId;

	@Column(name = "SOURCE_NODE_ID")
	private Long sourceNodeId;

	@Column(name = "GRAPH_ID")
	private Long graphId;

	public Si2SnEdge() {
	}

	public Long getGraphId() {
		return graphId;
	}

	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}

	public long getEdgeId() {
		return this.edgeId;
	}

	public void setEdgeId(long edgeId) {
		this.edgeId = edgeId;
	}

	public String getCuePhrase() {
		return this.cuePhrase;
	}

	public void setCuePhrase(String cuePhrase) {
		this.cuePhrase = cuePhrase;
	}

	public String getEdgeName() {
		return this.edgeName;
	}

	public void setEdgeName(String edgeName) {
		this.edgeName = edgeName;
	}

	public String getEdgeType() {
		return this.edgeType;
	}

	public void setEdgeType(String edgeType) {
		this.edgeType = edgeType;
	}

	/**
	 * @return the destinationNodeId
	 */
	public Long getDestinationNodeId() {
		return destinationNodeId;
	}

	/**
	 * @param destinationNodeId the destinationNodeId to set
	 */
	public void setDestinationNodeId(Long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	/**
	 * @return the sourceNodeId
	 */
	public Long getSourceNodeId() {
		return sourceNodeId;
	}

	/**
	 * @param sourceNodeId the sourceNodeId to set
	 */
	public void setSourceNodeId(Long sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}

}