package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the SI_2_SN_CONCEPTS database table.
 * 
 */
@Entity
@Table(name = "SI_2_SN_CONCEPTS")
public class Si2SnConcept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SI_2_SN_CONCEPTS_CONCEPTID_GENERATOR", sequenceName = "SEQ_SI_2_SN_CONCEPT_ATTR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SI_2_SN_CONCEPTS_CONCEPTID_GENERATOR")
	@Column(name = "CONCEPT_ID")
	private long conceptId;

	@Column(name = "ENTITY_TYPE")
	private String entityType;

	@Column(name = "NORMALIZED_CONCEPT")
	private String normalizedConcept;

	/*//bi-directional many-to-one association to Si2SnConceptAttr
	@OneToMany(mappedBy = "si2SnConcept")
	private List<Si2SnConceptAttr> si2SnConceptAttrs;
*/
	/*//bi-directional many-to-one association to Si2SnNode
	@OneToMany(mappedBy="si2SnConcept")
	private List<Si2SnNode> si2SnNodes;*/

	/*@Column(name = "NODE_ID")
	private Long nodeId;
*/
	public Si2SnConcept() {
	}

	public long getConceptId() {
		return this.conceptId;
	}

	public void setConceptId(long conceptId) {
		this.conceptId = conceptId;
	}

	public String getEntityType() {
		return this.entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getNormalizedConcept() {
		return this.normalizedConcept;
	}

	public void setNormalizedConcept(String normalizedConcept) {
		this.normalizedConcept = normalizedConcept;
	}

	/*public List<Si2SnConceptAttr> getSi2SnConceptAttrs() {
		return this.si2SnConceptAttrs;
	}

	public void setSi2SnConceptAttrs(List<Si2SnConceptAttr> si2SnConceptAttrs) {
		this.si2SnConceptAttrs = si2SnConceptAttrs;
	}*/

	/*public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}*/

}