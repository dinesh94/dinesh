/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.dao.Si2SnEdgeDAO;
import com.semanticintelligence.app.domain.Si2SnEdge;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2SnEdgeServiceImpl implements Si2SnEdgeService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private Si2SnEdgeDAO objDao;

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnEdge save(Si2SnEdge objPersist) {
		return objDao.save(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	public void delete(Si2SnEdge objRemove) {
		objDao.delete(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnEdge merge(Si2SnEdge toMerge) {
		return objDao.merge(toMerge);
	}

	@Override
	public List<Si2SnEdge> getBySourceTargetId(Command command) {
		return objDao.getBySourceTargetId(command);
	}
}