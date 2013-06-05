/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnEdge;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnEdgeService {

	public Si2SnEdge save(Si2SnEdge obj);

	public Si2SnEdge merge(Si2SnEdge obj);

	public void delete(Si2SnEdge obj);

	public List<Si2SnEdge> getBySourceTargetId(Command command);
	
}