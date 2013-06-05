/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;
import java.util.Set;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnNode;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnNodeDAO {

	public Si2SnNode save(Si2SnNode obj);

	public Si2SnNode merge(Si2SnNode obj);

	public void delete(Si2SnNode obj);

	public List<Long> getAllNodeIdByConeptId(Long rootNodeId);

	public List<Si2SnNode> getSi2SnNodeByConceptId(Command command);

	public List<Object[]> getGraphDataMap(Long nodeId, Long graphId);

	public List<Long> getConceptsIds(Command command);

	public List<Si2SnNode> getSi2SnNodeByNodeId(List<Long> rootNodeIdList);

	public List<Object[]> getNewDetails(Set<Long> newsIds);

}