/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.semanticintelligence.app.domain.User;

/**
 * @author dinesh.bhavsar
 *
 */
public interface SIUserDetailsService extends UserDetailsService {

	/**
	 * @param userIds
	 * @return dinesh.bhavsar
	 */
	List<User> getUsersById(Set<Long> userIds);

	/**
	 * @param userIds
	 * @return dinesh.bhavsar
	 */
	Map<Long, String> getUsedIdNameMapper(Set<Long> userIds);

}
