package com.semanticintelligence.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.semanticintelligence.app.dao.UsersDAO;
import com.semanticintelligence.app.domain.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements SIUserDetailsService {

	@Resource
	private UsersDAO usersDao;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException, DataAccessException {
		return usersDao.loadUserByUsername(loginId);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public List<User> getUsersById(Set<Long> userIds) {
		return usersDao.getUsersById(userIds);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Map<Long, String> getUsedIdNameMapper(Set<Long> userIds) {

		Map<Long, String> usedIdNameMapper = new HashMap<Long, String>();

		if (userIds != null && !userIds.isEmpty()) {
			List<User> users = getUsersById(userIds);

			for (User user : users) {
				usedIdNameMapper.put(user.getUserId(), user.getName());
			}
		}
		return usedIdNameMapper;

	}

}
