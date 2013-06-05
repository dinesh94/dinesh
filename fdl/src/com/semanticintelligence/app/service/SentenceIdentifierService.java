/**
 * 
 */
package com.semanticintelligence.app.service;

import com.semanticintelligence.app.domain.SentenceIdentifier;

/**
 * @author dinesh.bhavsar
 *
 */
public interface SentenceIdentifierService {

	public SentenceIdentifier save(SentenceIdentifier obj);

	public SentenceIdentifier merge(SentenceIdentifier obj);

	public void delete(SentenceIdentifier obj);
	
}