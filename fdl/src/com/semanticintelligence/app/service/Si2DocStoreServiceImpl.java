/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.semanticintelligence.app.dao.Si2DocStoreDAO;
import com.semanticintelligence.app.domain.Si2DocStore;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2DocStoreServiceImpl implements Si2DocStoreService{
	
	@Resource
	private Si2DocStoreDAO si2DocStoreDAO;

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<Si2DocStore> getMasterDocumentList() {		
		return si2DocStoreDAO.getMasterDocumentList();
	}	
}