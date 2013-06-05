package com.semanticintelligence.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the USERS_GROUP database table.
 * 
 */
@Entity
@Table(name="USERS_GROUP")
public class UsersGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERS_GROUP_USERSGROUPID_GENERATOR", sequenceName="SEQ_USERS_GROUP_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERS_GROUP_USERSGROUPID_GENERATOR")
	@Column(name="USERS_GROUP_ID", unique=true, nullable=false, precision=38)
	private long usersGroupId;

	@Column(name="CREATED_BY", length=20)
	private String createdBy;

	@Column(name="CREATED_DATE", nullable=false)
	private Timestamp createdDate;

	@Column(name="MODIFIED_BY", length=20)
	private String modifiedBy;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	//bi-directional many-to-one association to Group
    @ManyToOne
	@JoinColumn(name="GROUP_ID", nullable=false)
	private Group group;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;

    public UsersGroup() {
    }

	public long getUsersGroupId() {
		return this.usersGroupId;
	}

	public void setUsersGroupId(long usersGroupId) {
		this.usersGroupId = usersGroupId;
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

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}