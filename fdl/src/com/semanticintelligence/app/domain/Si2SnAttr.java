package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SI_2_SN_ATTR database table.
 * 
 */
@Entity
@Table(name = "SI_2_SN_ATTR")
public class Si2SnAttr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SI_2_SN_ATTR_ATTRIBUTEID_GENERATOR", sequenceName = "SEQ_SI_2_SN_ATTR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SI_2_SN_ATTR_ATTRIBUTEID_GENERATOR")
	@Column(name = "ATTRIBUTE_ID")
	private long attributeId;

	@Column(name = "IS_NODE_ATTR")
	private String isNodeAttr;

	@Column(name = "KEY")
	private String key;

	@Column(name = "REFERRING_ID")
	private Long referringId;

	@Column(name = "VALUE")
	private String value;

	public Si2SnAttr() {
	}

	public long getAttributeId() {
		return this.attributeId;
	}

	public void setAttributeId(long attributeId) {
		this.attributeId = attributeId;
	}

	public String getIsNodeAttr() {
		return this.isNodeAttr;
	}

	public void setIsNodeAttr(String isNodeAttr) {
		this.isNodeAttr = isNodeAttr;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getReferringId() {
		return this.referringId;
	}

	public void setReferringId(Long referringId) {
		this.referringId = referringId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Si2SnAttr [attributeId=" + attributeId + ", isNodeAttr=" + isNodeAttr + ", key=" + key + ", referringId=" + referringId + ", value=" + value + "]";
	}

}