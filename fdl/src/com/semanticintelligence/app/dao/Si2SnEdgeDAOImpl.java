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

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Array;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnEdge;

/**
 * @author dinesh.bhavsar
 *
 */
@Repository
public class Si2SnEdgeDAOImpl extends AbstractJPADAO<Si2SnEdge> implements Si2SnEdgeDAO {

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
	public Si2SnEdge save(Si2SnEdge objPersist) {
		return super.persist(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public void delete(Si2SnEdge objRemove) {
		super.remove(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	@Override
	public Si2SnEdge merge(Si2SnEdge toMerge) {
		return super.merge(toMerge);
	}

	@Override
	public List<Long> getRootNodeIdList() {
		String sql = "";

		/*sql += " select min(source_node_id) from si_2_sn_edge ";
		sql += " group by graph_id ";*/

		sql += " select distinct node_id from SI_2_SN_NODE where SENTENCE_ID=-1";

		Query query = entityManager.createNativeQuery(sql);

		List<BigDecimal> list = query.getResultList();
		List<Long> rootNodeIdLIst = null;
		if (list != null) {
			rootNodeIdLIst = new ArrayList<Long>();

			for (BigDecimal bigDecimalNumber : list) {
				rootNodeIdLIst.add(bigDecimalNumber.longValue());
			}
		}

		return rootNodeIdLIst;
	}

	@Override
	public Map<Long, Long> getRootNodeIdGraphIdMap(List<Long> defaultGraphIdList) {

		System.out.println("Si2SnEdgeDAOImpl.getRootNodeIdGraphIdMap() defaultGraphIdList = " + defaultGraphIdList);

		String sql = "";
		Map<Long, Long> nodeIdGraphId = null;
		Long nodeId = null;
		Long graphId = null;

		sql += " select distinct n.node_id, e.graph_id from si_2_sn_node n, si_2_sn_edge e ";
		sql += " where n.node_id = e.source_node_id and n.is_main_node = 'Y' and e.graph_id in (:defaultGraphIdList) ";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("defaultGraphIdList", defaultGraphIdList);

		List<Object[]> list = query.getResultList();

		if (list != null) {
			nodeIdGraphId = new HashMap<Long, Long>();

			for (Object[] obj : list) {
				nodeId = obj[0] == null ? -1 : Long.parseLong(obj[0].toString());
				graphId = obj[1] == null ? -1 : Long.parseLong(obj[1].toString());
				nodeIdGraphId.put(nodeId, graphId);
			}
		}

		return nodeIdGraphId;
	}

	@Override
	public List<Object[]> getGraphNodes(Command command) {
		String sql = "";

		sql += " select distinct source_node_id,graph_id from si_2_sn_edge where graph_id in ( :defaultGraphIdList ) ";
		sql += " union ";
		sql += " select distinct destination_node_id,graph_id from si_2_sn_edge where graph_id in ( :defaultGraphIdList ) ";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("defaultGraphIdList", command.getDefaultGraphIdList());

		List<Object[]> list = query.getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Si2SnEdge> getSi2SnEdgeForGraphId(List<Long> defaultGraphIdList) {

		String sql = "from Si2SnEdge where graphId in (126)";

		Query query = entityManager.createQuery(sql);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Si2SnEdge> getBySourceTargetId(Command command) {

		String sql = "from Si2SnEdge where sourceNodeId = :sourceNodeId and destinationNodeId = :destinationNodeId";

		Query query = entityManager.createQuery(sql);
		query.setParameter("sourceNodeId", command.getSourceNodeId());
		query.setParameter("destinationNodeId", command.getTargetNodeId());

		return query.getResultList();
	}
}