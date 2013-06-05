package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SI_2_SN_ATTR database table.
 * 
 */
@Entity
@Table(name = "SI_2_DOC_STORE")
public class Si2DocStore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SI_2_DOC_STORE_NEWSID_GENERATOR", sequenceName = "SEQ_SI_2_DOC_STORE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SI_2_DOC_STORE_NEWSID_GENERATOR")
	@Column(name = "NEWS_ID")
	private long newsId;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "COMPANY_ID")
	private Long companyId;

	@Column(name = "ANALYST_ID")
	private Long analystId;

	public Si2DocStore() {
	}

	/**
	 * @return the newsId
	 */
	public long getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId the newsId to set
	 */
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the analystId
	 */
	public Long getAnalystId() {
		return analystId;
	}

	/**
	 * @param analystId the analystId to set
	 */
	public void setAnalystId(Long analystId) {
		this.analystId = analystId;
	}
}