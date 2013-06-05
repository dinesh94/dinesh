/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.common.CommonConstants;
import com.semanticintelligence.app.domain.Si2SnNode;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2SnNodeDAOImpl extends AbstractJPADAO<Si2SnNode> implements Si2SnNodeDAO {

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
	public Si2SnNode save(Si2SnNode objPersist) {
		return super.persist(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(Si2SnNode objRemove) {
		super.remove(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2SnNode merge(Si2SnNode toMerge) {
		return super.merge(toMerge);
	}

	@Override
	public List<Long> getAllNodeIdByConeptId(Long rootNodeId) {
		String sql = "";
		sql += " SELECT node.node_id FROM SI_2_SN_EDGE edge, SI_2_SN_NODE node ";
		sql += " where edge.destination_node_id = node.node_id ";
		sql += " START with edge.source_node_id = :rootNodeId ";
		sql += " CONNECT BY NOCYCLE PRIOR edge.destination_node_id = edge.source_node_id ";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("rootNodeId", rootNodeId);

		List<Long> nodeIdList = (ArrayList<Long>) query.getResultList();

		return nodeIdList;
	}

	@Override
	public List<Si2SnNode> getSi2SnNodeByConceptId(Command command) {

		String hql = "from Si2SnNode where conceptId in ( :conceptId ) ";
		Query query = entityManager.createQuery(hql, Si2SnNode.class);
		query.setParameter("conceptId", command.getConceptIdList());

		return query.getResultList();
	}

	@Override
	public List<Object[]> getGraphDataMap(Long nodeId, Long graphId) {
		String sql = "";

		sql += " SELECT node.NODE_ID, node.NEWS_ID, node.IS_PDF, node.IS_WEBSEARCH, node.IS_TWITTER, node.CONCEPT_ID, node.RAW_CONCEPT, node.SENTENCE_ID, node.ENTITY_TYPE, node.CONCEPT_TOKENS, ";
		sql += " edge.EDGE_ID, edge.SOURCE_NODE_ID, edge.DESTINATION_NODE_ID, edge.EDGE_NAME, edge.EDGE_TYPE, edge.CUE_PHRASE, edge.GRAPH_ID, edge.PID, edge.SID, edge.IS_ROOT ";
		sql += " FROM SI_2_SN_EDGE edge, SI_2_SN_NODE node ";
		sql += " /*LEFT OUTER JOIN Sentence_Identifier si on si.sentence_id = node.sentence_id and si.news_id = node.news_id */ ";
		sql += " where edge.destination_node_id = node.node_id ";
		sql += " and edge.graph_id = :graphId ";
		//sql += " and node.ENTITY_TYPE in (:list)";
		//sql += " START with edge.source_node_id = :nodeId ";
		//sql += " CONNECT BY NOCYCLE PRIOR edge.destination_node_id = edge.source_node_id ";
		//sql += " ORDER SIBLINGS BY edge.source_node_id ";

		Query query = entityManager.createNativeQuery(sql);
		//query.setParameter("nodeId", nodeId);
		query.setParameter("graphId", graphId);
		//query.setParameter("list", CommonConstants.ENTITY_TYPE_TO_CONSIDER);

		return query.getResultList();
	}

	@Override
	public List<Long> getConceptsIds(Command command) {

		List<Long> list = new ArrayList<Long>();

		List<String> defaultEntiry = new ArrayList<String>();
		defaultEntiry.add(CommonConstants.Entity_COMPANY);
		defaultEntiry.add(CommonConstants.Entity_GEO);
		defaultEntiry.add(CommonConstants.Entity_REGULATORY_BODY);
		defaultEntiry.add(CommonConstants.Entity_TOPIC);
		defaultEntiry.add(CommonConstants.ENTITY_NAME_OF_PERSON);

		String sql = "";
		sql += " select distinct concept_id from SI_2_SN_NODE where ENTITY_TYPE in ( :list )";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("list", defaultEntiry);

		List<BigDecimal> nodeIdList = query.getResultList();
		for (BigDecimal bigDecimal : nodeIdList) {
			list.add(bigDecimal.longValue());
		}

		return list;
	}

	@Override
	public List<Si2SnNode> getSi2SnNodeByNodeId(List<Long> rootNodeIdList) {
		String hql = "from Si2SnNode where nodeId in ( :rootNodeIdList ) ";

		Query query = entityManager.createQuery(hql, Si2SnNode.class);
		query.setParameter("rootNodeIdList", rootNodeIdList);

		return query.getResultList();
	}

	@Override
	public List<Object[]> getNewDetails(Set<Long> newsIds) {

		if (newsIds != null && newsIds.size() > 0) {
			String sql = "select news_id,url,title from news where news_id in (:newsId)";

			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("newsId", newsIds);

			return query.getResultList();
		} else {
			return new ArrayList<Object[]>();
		}
	}
}