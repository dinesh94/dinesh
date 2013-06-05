/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import com.semanticintelligence.app.domain.Si2DocStore;
import com.semanticintelligence.app.domain.Si2SnConcept;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2DocStoreService {

	List<Si2DocStore> getMasterDocumentList();
		
}