/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.dao.Si2DocNetworkMapDAO;
import com.semanticintelligence.app.dao.Si2DocStoreDAO;
import com.semanticintelligence.app.domain.Si2DocStore;

/**
 * @author dinesh.bhavsar
 *
 */
@Service
public class Si2DocNetworkMapServiceImpl implements Si2DocNetworkMapService{
	
	@Resource
	private Si2DocNetworkMapDAO si2DocNetworkMapDAO;

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public Long getGraphIdByDocumentAndType(Command command) {		
		return si2DocNetworkMapDAO.getGraphIdByDocumentAndType(command);
	}

	
}