/**
 * 
 */
package com.semanticintelligence.app.dao;

import com.semanticintelligence.app.domain.SentenceIdentifier;


/**
 * @author dinesh.bhavsar
 *
 */
public interface SentenceIdentifierDAO {

	public SentenceIdentifier save(SentenceIdentifier obj);

	public SentenceIdentifier merge(SentenceIdentifier obj);

	public void delete(SentenceIdentifier obj);
	
}