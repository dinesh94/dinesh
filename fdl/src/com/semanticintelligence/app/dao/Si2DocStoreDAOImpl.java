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

import com.semanticintelligence.app.domain.Si2DocStore;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2DocStoreDAOImpl extends AbstractJPADAO<Si2DocStore> implements Si2DocStoreDAO {

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

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2DocStore save(Si2DocStore objPersist) {
		return super.persist(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(Si2DocStore objRemove) {
		super.remove(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2DocStore merge(Si2DocStore toMerge) {
		return super.merge(toMerge);
	}

	@Override
	public List<Si2DocStore> getMasterDocumentList() {
		String hql = " from Si2DocStore order by title asc";
		Query query = entityManager.createQuery(hql, Si2DocStore.class);		
		return query.getResultList();
	}

	
}