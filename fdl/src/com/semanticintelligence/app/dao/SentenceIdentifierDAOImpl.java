/**
 * 
 */
package com.semanticintelligence.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.domain.SentenceIdentifier;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class SentenceIdentifierDAOImpl extends AbstractJPADAO<SentenceIdentifier> implements SentenceIdentifierDAO {

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
	public SentenceIdentifier save(SentenceIdentifier objPersist) {
		return super.persist(objPersist);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(SentenceIdentifier objRemove) {
		super.remove(objRemove);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	@Override
	public SentenceIdentifier merge(SentenceIdentifier toMerge) {
		return super.merge(toMerge);
	}
}