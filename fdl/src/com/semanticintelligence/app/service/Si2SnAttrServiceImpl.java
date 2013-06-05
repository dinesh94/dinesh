/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.dao.Si2SnAttrDAO;
import com.semanticintelligence.app.dao.Si2SnNodeDAO;
import com.semanticintelligence.app.domain.Si2SnAttr;
import com.semanticintelligence.app.domain.Si2SnNode;
import com.semanticintelligence.app.model.NewsDetails;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2SnAttrServiceImpl implements Si2SnAttrService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private Si2SnAttrDAO objDao;

	@Resource
	private Si2SnNodeDAO si2SnNodeDAO;

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnAttr save(Si2SnAttr objPersist) {
		return objDao.save(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	public void delete(Si2SnAttr objRemove) {
		objDao.delete(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnAttr merge(Si2SnAttr toMerge) {
		return objDao.merge(toMerge);
	}

	@Override
	public Map<String, Object> getByNodeId(List<Long> nodeId) {

		Set<Long> documentIds = new HashSet<Long>();

		Si2SnNode si2SnNode = null;
		List<Object[]> result = null;
		List<String> titles = new ArrayList<String>();
		String title;
		List<Si2SnNode> si2SnNodeList = new ArrayList<Si2SnNode>();

		Map<String, Object> map = objDao.getByNodeId(nodeId);

		List<Object[]> list = (List<Object[]>) map.get("node_sentence");

		if (list != null && list.isEmpty() == false) {

			for (Object[] obj : list) {

				try {

					si2SnNode = new Si2SnNode();
					si2SnNode.setNodeId(Long.parseLong(obj[0].toString()));

					si2SnNode.setNewsId(Long.parseLong(obj[1].toString()));
					documentIds.add(si2SnNode.getNewsId());

					si2SnNode.setConceptId(obj[5] == null ? null : Long.parseLong(obj[5].toString()));
					si2SnNode.setRawConcept(obj[6] == null ? null : obj[6].toString());
					si2SnNode.setSentenceId(obj[7] == null ? null : Long.parseLong(obj[7].toString()));
					si2SnNode.setEntityType(obj[8] == null ? null : obj[8].toString());
					si2SnNode.setConceptTokens(obj[9] == null ? null : obj[9].toString());

					//si2SnNode.setSentenceId(sentenceId) [10]
					si2SnNode.setSentence(obj[11] == null ? null : obj[11].toString());

					si2SnNodeList.add(si2SnNode);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			result = si2SnNodeDAO.getNewDetails(documentIds);
			for (Object[] objects : result) {
				title = objects[1] != null ? objects[1].toString() : "";
				if (!title.isEmpty())
					titles.add(objects[1] != null ? objects[1].toString() : "");
			}
		}

		map.remove("node_sentence");

		map.put("node_sentence", si2SnNodeList);
		map.put("documentIds", documentIds);
		map.put("documents", titles);

		return map;
	}

	@Override
	public Map<String, Object> getDataForEdge(List<Long> referringId) {

		Set<Long> documentIds = new HashSet<Long>();

		Si2SnNode si2SnNode = null;
		List<Object[]> result = null;
		List<String> titles = new ArrayList<String>();
		String title;
		List<Si2SnNode> si2SnNodeList = new ArrayList<Si2SnNode>();

		Map<String, Object> map = objDao.getDataForEdge(referringId);

		List<Object[]> list = (List<Object[]>) map.get("node_sentence");

		if (list != null && list.isEmpty() == false) {

			for (Object[] obj : list) {

				try {

					si2SnNode = new Si2SnNode();
					si2SnNode.setNodeId(Long.parseLong(obj[0].toString()));

					si2SnNode.setNewsId(Long.parseLong(obj[1].toString()));
					documentIds.add(si2SnNode.getNewsId());
					
					si2SnNode.setConceptId(obj[2] == null ? null : Long.parseLong(obj[2].toString()));
					si2SnNode.setRawConcept(obj[3] == null ? null : obj[3].toString());
					si2SnNode.setSentenceId(obj[4] == null ? null : Long.parseLong(obj[4].toString()));
					si2SnNode.setEntityType(obj[5] == null ? null : obj[5].toString());
					si2SnNode.setConceptTokens(obj[6] == null ? null : obj[6].toString());

					//si2SnNode.setSentenceId(sentenceId) [10]
					si2SnNode.setSentence(obj[8] == null ? null : obj[8].toString());

					si2SnNodeList.add(si2SnNode);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			result = si2SnNodeDAO.getNewDetails(documentIds);
			for (Object[] objects : result) {
				title = objects[1] != null ? objects[1].toString() : "";
				if (!title.isEmpty())
					titles.add(objects[1] != null ? objects[1].toString() : "");
			}
		}

		map.remove("node_sentence");

		map.put("node_sentence", si2SnNodeList);
		map.put("documentIds", documentIds);
		map.put("documents", titles);

		return map;
	}

	@Override
	public List<Si2SnAttr> getBySourceTargetId(Command command) {
		return objDao.getBySourceTargetId(command);
	}

	@Override
	public List<Long> getNewReferingIds(List<Long> referringId) {
		return objDao.getNewReferingIds(referringId);
	}

}