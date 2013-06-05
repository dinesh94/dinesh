package com.semanticintelligence.app.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the "GROUP" database table.
 * 
 */
@Entity
@Table(name = "GROUP")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "GROUP_GROUP_ID_GENERATOR", sequenceName = "SEQ_GROUP_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_GROUP_ID_GENERATOR")
	@Column(name="GROUP_ID", unique=true, nullable=false, precision=38)
	private long groupId;

	@Column(name="CREATED_BY", length=20)
	private String createdBy;

	@Column(name="CREATED_DATE", nullable=false)
	private Timestamp createdDate;

	@Column(name="GROUP_DESC", length=200)
	private String groupDesc;

	@Column(name="GROUP_NAME", length=50)
	private String groupName;

	@Column(name="MODIFIED_BY", length=20)
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	//bi-directional many-to-one association to UsersGroup
	@OneToMany(mappedBy="group")
	private List<UsersGroup> usersGroups;

    public Group() {
    }

	public long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
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

	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public List<UsersGroup> getUsersGroups() {
		return this.usersGroups;
	}

	public void setUsersGroups(List<UsersGroup> usersGroups) {
		this.usersGroups = usersGroups;
	}
	
}