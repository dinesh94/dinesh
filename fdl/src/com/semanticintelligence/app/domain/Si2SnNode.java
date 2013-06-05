package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the SI_2_SN_NODE database table.
 * 
 */
@Entity
@Table(name = "SI_2_SN_NODE")
public class Si2SnNode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SI_2_SN_NODE_NODEID_GENERATOR", sequenceName = "SEQ_SI_2_SN_NODE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SI_2_SN_NODE_NODEID_GENERATOR")
	@Column(name = "NODE_ID")
	private Long nodeId;

	@Column(name = "CONCEPT_TOKENS")
	private String conceptTokens;

	@Column(name = "ENTITY_TYPE")
	private String entityType;

	@Column(name = "IS_PDF")
	private String isPdf;

	@Column(name = "IS_TWITTER")
	private String isTwitter;

	@Column(name = "IS_WEBSEARCH")
	private String isWebsearch;

	@Column(name = "NEWS_ID")
	private Long newsId;

	@Column(name = "RAW_CONCEPT")
	private String rawConcept;

	@Column(name = "SENTENCE_ID")
	private Long sentenceId;

	@Transient
	private String sentence;

	@Column(name = "CONCEPT_ID")
	private Long conceptId;

	public Si2SnNode() {
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getConceptTokens() {
		return this.conceptTokens;
	}

	public void setConceptTokens(String conceptTokens) {
		this.conceptTokens = conceptTokens;
	}

	public String getEntityType() {
		return this.entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getIsPdf() {
		return this.isPdf;
	}

	public void setIsPdf(String isPdf) {
		this.isPdf = isPdf;
	}

	public String getIsTwitter() {
		return this.isTwitter;
	}

	public void setIsTwitter(String isTwitter) {
		this.isTwitter = isTwitter;
	}

	public String getIsWebsearch() {
		return this.isWebsearch;
	}

	public void setIsWebsearch(String isWebsearch) {
		this.isWebsearch = isWebsearch;
	}

	public Long getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public String getRawConcept() {
		return this.rawConcept;
	}

	public void setRawConcept(String rawConcept) {
		this.rawConcept = rawConcept;
	}

	public Long getSentenceId() {
		return this.sentenceId;
	}

	public void setSentenceId(Long sentenceId) {
		this.sentenceId = sentenceId;
	}

	/**
	 * @return the conceptId
	 */
	public Long getConceptId() {
		return conceptId;
	}

	/**
	 * @param conceptId the conceptId to set
	 */
	public void setConceptId(Long conceptId) {
		this.conceptId = conceptId;
	}

	@Override
	public String toString() {
		return "Si2SnNode [nodeId=" + nodeId + ", conceptTokens=" + conceptTokens + ", entityType=" + entityType + ", isPdf=" + isPdf + ", isTwitter=" + isTwitter + ", isWebsearch=" + isWebsearch + ", newsId=" + newsId + ", rawConcept=" + rawConcept + ", sentenceId=" + sentenceId + ", sentence=" + sentence + ", conceptId=" + conceptId + "]";
	}

}