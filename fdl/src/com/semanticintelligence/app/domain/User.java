/**
 * 
 */
package com.semanticintelligence.app.domain;

import java.io.Serializable;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * @author dinesh.bhavsar
 */

@Entity
@Table(name = "USERS")
@NamedQueries({
		@NamedQuery(name = "loadUserByUsername", query = "FROM User U where U.login = ?1") })
public class User implements Serializable,
		org.springframework.security.core.userdetails.UserDetails, Principal {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "USERS_USERID_GENERATOR", sequenceName = "SEQ_USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_USERID_GENERATOR")
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 38)
	private long userId;

	@Column(length = 100)
	private String city;

	@Column(name = "COMPANY_NAME", length = 100)
	private String companyName;

	@Column(length = 20)
	private String country;

	@Column(name = "CREATED_BY", length = 20)
	private String createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Timestamp createdDate;

	@Column(length = 100)
	private String email;

	@Column(name = "FIRST_NAME", length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", length = 100)
	private String lastName;

	@Column(nullable = false, length = 50)
	private String login;

	@Column(name = "MODIFIED_BY", length = 20)
	private String modifiedBy;

	@Column(name = "MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(nullable = false, length = 50)
	private String password;

	@Column(length = 100)
	private String telephone;

	@Column(name = "ZIP_CODE", length = 20)
	private String zipCode;

	//bi-directional many-to-one association to UsersGroup
	@OneToMany(mappedBy = "user")
	private List<UsersGroup> usersGroups;

	public User() {
	}

	public User(Long userId) {
		this.userId = userId;
	}

	/**
	 * @param email2
	 * @param object
	 * @param object2
	 */
	public User(Long userId, String firstName, String email) {
		this.userId = userId;
		this.firstName = firstName;
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<UsersGroup> getUsersGroups() {
		return usersGroups;
	}

	public void setUsersGroups(List<UsersGroup> usersGroups) {
		this.usersGroups = usersGroups;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		// ADD USER ROLES
		authList.add(new GrantedAuthorityImpl("ROLE_USER"));

		return authList;
	}

	/*
	 * public static List<GrantedAuthority>
	 * getGrantedAuthorities(ErmUserFeatureMapping ermUserFeatureMapping) {
	 * List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	 * authorities.add(new
	 * GrantedAuthorityImpl(ermUserFeatureMapping.getErmApplicationFeature
	 * ().getFeatureName())); return authorities; }
	 * 
	 * public static List<GrantedAuthority> getGrantedAuthorities(ErmUserRole
	 * roles) { List<GrantedAuthority> authorities = new
	 * ArrayList<GrantedAuthority>(); authorities.add(new
	 * GrantedAuthorityImpl(roles.getRole())); return authorities; }
	 */

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static boolean isRolePresent(
			Collection<GrantedAuthority> authorities, String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;

	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public String getName() {
		return firstName;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
