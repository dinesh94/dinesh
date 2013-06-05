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

import com.semanticintelligence.app.domain.Si2SnConceptAttr;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2SnConceptAttrDAOImpl extends AbstractJPADAO<Si2SnConceptAttr> implements Si2SnConceptAttrDAO {

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
	public Si2SnConceptAttr save(Si2SnConceptAttr objPersist) {
		return super.persist(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(Si2SnConceptAttr objRemove) {
		super.remove(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2SnConceptAttr merge(Si2SnConceptAttr toMerge) {
		return super.merge(toMerge);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Si2SnConceptAttr> getSi2SnConceptAttr() {

		String hql = "from Si2SnConceptAttr";

		Query query = entityManager.createQuery(hql);

		List<Si2SnConceptAttr> list = query.getResultList();

		return list;
	}
}