package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SI_2_SN_CONCEPT_ATTR database table.
 * 
 */
@Entity
@Table(name = "SI_2_SN_CONCEPT_ATTR")
public class Si2SnConceptAttr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SI_2_SN_CONCEPT_ATTR_NETWORKCONCEPTATTRID_GENERATOR", sequenceName = "SEQ_SI_2_SN_CONCEPTS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SI_2_SN_CONCEPT_ATTR_NETWORKCONCEPTATTRID_GENERATOR")
	@Column(name = "NETWORK_CONCEPT_ATTR_ID")
	private long networkConceptAttrId;

	@Column(name = "KEY")
	private String key;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "CONCEPT_ID")
	private Long conceptId;

	public Si2SnConceptAttr() {
	}

	public long getNetworkConceptAttrId() {
		return this.networkConceptAttrId;
	}

	public void setNetworkConceptAttrId(long networkConceptAttrId) {
		this.networkConceptAttrId = networkConceptAttrId;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
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
}