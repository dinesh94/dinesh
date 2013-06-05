/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;

import com.semanticintelligence.app.domain.Si2SnConcept;


/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnConceptDAO {

	public Si2SnConcept save(Si2SnConcept obj);

	public Si2SnConcept merge(Si2SnConcept obj);

	public void delete(Si2SnConcept obj);

	public List<Si2SnConcept> getMasterConceptList(String type);
	
}