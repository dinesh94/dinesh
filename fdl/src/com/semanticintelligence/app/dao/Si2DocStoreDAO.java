/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;
import java.util.Map;

import com.semanticintelligence.app.domain.Si2DocStore;
import com.semanticintelligence.app.domain.Si2SnAttr;
import com.semanticintelligence.app.domain.Si2SnNode;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2DocStoreDAO {

	public Si2DocStore save(Si2DocStore obj);

	public Si2DocStore merge(Si2DocStore obj);

	public void delete(Si2DocStore obj);

	public List<Si2DocStore> getMasterDocumentList();
}