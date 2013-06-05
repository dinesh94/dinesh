package com.semanticintelligence.app.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the SENTENCE_IDENTIFIER database table.
 * 
 */
@Entity
@Table(name="SENTENCE_IDENTIFIER")
public class SentenceIdentifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SENTENCE_IDENTIFIER_SENTENCEIDENTIFIERID_GENERATOR", sequenceName = "SEQ_SENTENCE_IDENTIFIER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SENTENCE_IDENTIFIER_SENTENCEIDENTIFIERID_GENERATOR")
	@Column(name="SENTENCE_IDENTIFIER_ID")
	private long sentenceIdentifierId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="NEWS_ID")
	private Long newsId;

	private String sentence;

	@Column(name="SENTENCE_ID")
	private Long sentenceId;

    public SentenceIdentifier() {
    }

	public long getSentenceIdentifierId() {
		return this.sentenceIdentifierId;
	}

	public void setSentenceIdentifierId(long sentenceIdentifierId) {
		this.sentenceIdentifierId = sentenceIdentifierId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public String getSentence() {
		return this.sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Long getSentenceId() {
		return this.sentenceId;
	}

	public void setSentenceId(Long sentenceId) {
		this.sentenceId = sentenceId;
	}

}