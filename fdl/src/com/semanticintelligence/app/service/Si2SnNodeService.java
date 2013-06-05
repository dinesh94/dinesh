/**
 * 
 */
package com.semanticintelligence.app.service;

import java.util.List;
import java.util.Map;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnNode;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnNodeService {

	public Si2SnNode save(Si2SnNode obj);

	public Si2SnNode merge(Si2SnNode obj);

	public void delete(Si2SnNode obj);

	public List<Long> getAllNodeIdByConeptId(Long rootNodeId);

	public Map<String, Object> getGraphDataMap(Command command, List<Long> filterConceptIds);

	public List<Long> getConceptsIds(Command command);

	List<Si2SnNode> getSi2SnNodeByConceptId(Command command);

}