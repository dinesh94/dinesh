/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2DocNetworkMap;
import com.semanticintelligence.app.domain.Si2DocStore;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2DocNetworkMapDAOImpl extends AbstractJPADAO<Si2DocNetworkMap> implements Si2DocNetworkMapDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	private EntityManager entityManager;

	@Required
	@PersistenceContext(unitName = "punit")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Long getGraphIdByDocumentAndType(Command command) {
		String hql = " from Si2DocNetworkMap where newsId = :newsId and graphType = :graphType and isLatest = 'Y' ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("newsId", command.getDocument());
		query.setParameter("graphType", command.getGraphType());
		
		Si2DocNetworkMap si2DocNetworkMap = (Si2DocNetworkMap) query.getSingleResult();
		
		if(si2DocNetworkMap != null) {
			return si2DocNetworkMap.getGraphId();
		} else {
			return null;
		}
	}

	

	
}