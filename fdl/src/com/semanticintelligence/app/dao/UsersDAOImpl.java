/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.semanticintelligence.app.domain.User;

/**
 * @author dinesh.bhavsar
 * 
 */

@Repository("usersDao")
public class UsersDAOImpl extends AbstractJPADAO<User> implements UsersDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	private EntityManager entityManager;

	@Required
	@PersistenceContext(unitName = "punit")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public UserDetails loadUserByUsername(String loginId)
			throws UsernameNotFoundException, DataAccessException {
		User useDetails = null;

		List<User> usersList = executeQueryByName("loadUserByUsername", loginId);

		if (usersList != null && usersList.isEmpty() == false)
			useDetails = usersList.get(0);

		else if (usersList.size() > 1) {
			return null;
			// throw new
			// Exception("More than one user exist with same login id.");
		} else if (usersList == null || usersList.isEmpty()) {
			return null;
			// throw new Exception("User not found !");
		}

		/*List result = entityManager.createQuery("from DataIntegration where diAliasName like 'officers-text'").getResultList();

		logger.info("UsersDAOImpl.loadUserByUsername() result = " + result);*/

		return useDetails;
	}

	/**
	 * dinesh.bhavsar
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersById(Set<Long> userIds) {

		String hql = "from User where userId in ( :userIds ) ";

		Query query = entityManager.createQuery(hql).setParameter("userIds", userIds);

		return query.getResultList();
	}

}
