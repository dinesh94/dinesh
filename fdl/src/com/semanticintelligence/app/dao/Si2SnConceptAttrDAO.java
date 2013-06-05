/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;

import com.semanticintelligence.app.domain.Si2SnConceptAttr;


/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnConceptAttrDAO {

	public Si2SnConceptAttr save(Si2SnConceptAttr obj);

	public Si2SnConceptAttr merge(Si2SnConceptAttr obj);

	public void delete(Si2SnConceptAttr obj);

	public List<Si2SnConceptAttr> getSi2SnConceptAttr();
	
}