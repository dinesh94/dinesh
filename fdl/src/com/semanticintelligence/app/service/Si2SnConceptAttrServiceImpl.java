/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.dao.Si2SnConceptAttrDAO;
import com.semanticintelligence.app.domain.Si2SnConceptAttr;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2SnConceptAttrServiceImpl implements Si2SnConceptAttrService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private Si2SnConceptAttrDAO objDao;

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnConceptAttr save(Si2SnConceptAttr objPersist) {
		return objDao.save(objPersist);
	}

	/**
	 * dinesh.bhavsar
	 */
	public void delete(Si2SnConceptAttr objRemove) {
		objDao.delete(objRemove);
	}

	/**
	 * dinesh.bhavsar
	 */
	public Si2SnConceptAttr merge(Si2SnConceptAttr toMerge) {
		return objDao.merge(toMerge);
	}

	@Override
	public List<Si2SnConceptAttr> getSi2SnConceptAttr() {
		return objDao.getSi2SnConceptAttr();
	}
}