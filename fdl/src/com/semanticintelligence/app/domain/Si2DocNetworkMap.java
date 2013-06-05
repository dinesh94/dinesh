package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SI_2_SN_ATTR database table.
 * 
 */
@Entity
@Table(name = "SI_2_DOC_NETWORK_MAP")
public class Si2DocNetworkMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MAP_DOC_NTWRK_MAP_ID_GENERATOR", sequenceName = "SEQ_SI_2_DOC_NTWRK_MAP_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAP_DOC_NTWRK_MAP_ID_GENERATOR")
	@Column(name = "DOC_NTWRK_MAP_ID")
	private long docNtwrkMapId;

	@Column(name = "NEWS_ID")
	private Long newsId;

	@Column(name = "GRAPH_ID")
	private Long graphId;

	@Column(name = "GRAPH_TYPE")
	private String graphType;

	@Column(name = "IS_LATEST")
	private String isLatest;
	
	public Si2DocNetworkMap() {
	}

	/**
	 * @return the docNtwrkMapId
	 */
	public long getDocNtwrkMapId() {
		return docNtwrkMapId;
	}

	/**
	 * @param docNtwrkMapId the docNtwrkMapId to set
	 */
	public void setDocNtwrkMapId(long docNtwrkMapId) {
		this.docNtwrkMapId = docNtwrkMapId;
	}

	/**
	 * @return the newsId
	 */
	public Long getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId the newsId to set
	 */
	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	/**
	 * @return the isLatest
	 */
	public String getIsLatest() {
		return isLatest;
	}

	/**
	 * @param isLatest the isLatest to set
	 */
	public void setIsLatest(String isLatest) {
		this.isLatest = isLatest;
	}

	/**
	 * @return the graphId
	 */
	public Long getGraphId() {
		return graphId;
	}

	/**
	 * @param graphId the graphId to set
	 */
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}

	/**
	 * @return the graphType
	 */
	public String getGraphType() {
		return graphType;
	}

	/**
	 * @param graphType the graphType to set
	 */
	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}
}