/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.dao.Si2SnConceptDAO;
import com.semanticintelligence.app.domain.Si2SnConcept;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2SnConceptServiceImpl implements Si2SnConceptService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private Si2SnConceptDAO objDao;
	
	/**
	 * dinesh.bhavsar
	 */
	public Si2SnConcept save(Si2SnConcept objPersist){
		return objDao.save(objPersist);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	public void delete(Si2SnConcept objRemove){
		objDao.delete(objRemove);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	public Si2SnConcept merge(Si2SnConcept toMerge){
		return objDao.merge(toMerge);
	}

	@Override
	public List<Si2SnConcept> getMasterConceptList(String type) {		
		return objDao.getMasterConceptList(type);
	}

	
}