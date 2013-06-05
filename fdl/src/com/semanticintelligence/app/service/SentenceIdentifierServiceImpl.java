/**
 * 
 */
package com.semanticintelligence.app.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticintelligence.app.dao.SentenceIdentifierDAO;
import com.semanticintelligence.app.domain.SentenceIdentifier;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class SentenceIdentifierServiceImpl implements SentenceIdentifierService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private SentenceIdentifierDAO objDao;
	
	/**
	 * dinesh.bhavsar
	 */
	public SentenceIdentifier save(SentenceIdentifier objPersist){
		return objDao.save(objPersist);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	public void delete(SentenceIdentifier objRemove){
		objDao.delete(objRemove);
	}
	
	/**
	 * dinesh.bhavsar
	 */
	public SentenceIdentifier merge(SentenceIdentifier toMerge){
		return objDao.merge(toMerge);
	}
}