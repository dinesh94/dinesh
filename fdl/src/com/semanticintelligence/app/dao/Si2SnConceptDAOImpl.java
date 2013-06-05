/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.domain.Si2SnConcept;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2SnConceptDAOImpl extends AbstractJPADAO<Si2SnConcept> implements Si2SnConceptDAO {

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
	public Si2SnConcept save(Si2SnConcept objPersist) {
		return super.persist(objPersist);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(Si2SnConcept objRemove) {
		super.remove(objRemove);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2SnConcept merge(Si2SnConcept toMerge) {
		return super.merge(toMerge);
	}

	@Override
	public List<Si2SnConcept> getMasterConceptList(String type) {
		String hql = " from Si2SnConcept where conceptId in ( select distinct(conceptId) from Si2SnConceptAttr where value = :type ) order by normalizedConcept asc";
		Query query = entityManager.createQuery(hql);
		query.setParameter("type", type);
		
		return query.getResultList();
	}
}