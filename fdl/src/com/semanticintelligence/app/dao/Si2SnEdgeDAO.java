/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;
import java.util.Map;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnEdge;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnEdgeDAO {

	public Si2SnEdge save(Si2SnEdge obj);

	public Si2SnEdge merge(Si2SnEdge obj);

	public void delete(Si2SnEdge obj);

	public List<Long> getRootNodeIdList();

	public Map<Long, Long> getRootNodeIdGraphIdMap(List<Long> defaultGraphIdList);

	public List<Object[]> getGraphNodes(Command command);

	public List<Si2SnEdge> getSi2SnEdgeForGraphId(List<Long> defaultGraphIdList);

	public List<Si2SnEdge> getBySourceTargetId(Command command);

}