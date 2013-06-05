/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnAttr;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2SnAttrDAOImpl extends AbstractJPADAO<Si2SnAttr> implements Si2SnAttrDAO {

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
	public Si2SnAttr save(Si2SnAttr objPersist) {
		return super.persist(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(Si2SnAttr objRemove) {
		super.remove(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2SnAttr merge(Si2SnAttr toMerge) {
		return super.merge(toMerge);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getByNodeId(List<Long> referringId) {

		Map<String, Object> map = new HashMap<String, Object>();

		String sql = "SELECT node.NODE_ID, node.NEWS_ID, node.CONCEPT_ID, node.RAW_CONCEPT, node.SENTENCE_ID, node.ENTITY_TYPE, node.CONCEPT_TOKENS,si.sentence_id, si.sentence ";
		sql += " FROM SI_2_SN_NODE node ";
		sql += " LEFT OUTER JOIN Sentence_Identifier si on si.sentence_id = node.sentence_id and si.news_id = node.news_id ";
		sql += " where node.node_id in ( :referringId )";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("referringId", referringId);

		List<Object[]> node_sentence = query.getResultList();

		map.put("node_sentence", node_sentence);

		List<Si2SnAttr> attrList = entityManager.createQuery("from Si2SnAttr where referringId in (:referringId) ").setParameter("referringId", referringId).getResultList();

		map.put("attrList", attrList);

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDataForEdge(List<Long> referringId) {

		Map<String, Object> map = new HashMap<String, Object>();

		String sql = "SELECT node.NODE_ID, node.NEWS_ID, node.CONCEPT_ID, node.RAW_CONCEPT, node.SENTENCE_ID, node.ENTITY_TYPE, node.CONCEPT_TOKENS,si.sentence_id, si.sentence ";
		sql += " FROM SI_2_SN_NODE node  ";
		sql += " LEFT OUTER JOIN Sentence_Identifier si on si.sentence_id = node.sentence_id and si.news_id = node.news_id ";
		sql += " where node.node_id in (  ";
		sql += " select referring_id from si_2_sn_attr where referring_id in ( select value from si_2_sn_attr where is_node_attr='N' and referring_id in ( ";
		sql += " select distinct edge_id from si_2_sn_edge where referring_id in ( :referringId ) ) and key='PATH_EDGE_ID'  ) ";
		sql += " and key not in ('PATH_NODE_ID','PATH_EDGE_ID') ";
		sql += " ) ";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("referringId", referringId);

		List<Object[]> node_sentence = query.getResultList();

		map.put("node_sentence", node_sentence);

		sql = "select * from si_2_sn_attr where referring_id in ( select value from si_2_sn_attr where is_node_attr='N' and referring_id in ( ";
		sql += " select distinct edge_id from si_2_sn_edge where referring_id in ( :referringId ) ) and key='PATH_EDGE_ID'  ) ";
		sql += " and key not in ('PATH_NODE_ID','PATH_EDGE_ID') ";

		List<Si2SnAttr> attrList = entityManager.createNativeQuery(sql, Si2SnAttr.class).setParameter("referringId", referringId).getResultList();

		map.put("attrList", attrList);

		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Si2SnAttr> getBySourceTargetId(Command command) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(command.getSourceNodeId());
		ids.add(command.getTargetNodeId());

		String sql = "from Si2SnAttr where referringId in (:ids)";

		Query query = entityManager.createQuery(sql);
		query.setParameter("ids", ids);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getNewReferingIds(List<Long> referringId) {

		List<Long> actualResult = new ArrayList<Long>();

		String sql = "select REFERRING_ID from si_2_sn_attr where REFERRING_ID in (:referringId) and key in ('PATH_NODE_ID','PATH_EDGE_ID')";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("referringId", referringId);

		List<BigDecimal> result = query.getResultList();

		for (BigDecimal bigDecimal : result) {
			actualResult.add(new Long(bigDecimal.toString()));
		}

		return actualResult;
	}
}