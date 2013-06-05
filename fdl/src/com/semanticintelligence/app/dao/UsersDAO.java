/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.semanticintelligence.app.domain.User;

/**
 * @author dinesh.bhavsar
 * 
 */
public interface UsersDAO extends GenericJPADAO<User> {

	UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException, DataAccessException;

	/**
	 * @param userIds
	 * @return dinesh.bhavsar
	 */
	List<User> getUsersById(Set<Long> userIds);
	
}
