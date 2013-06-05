/**
 * 
 */
package com.semanticintelligence.app.dao;

import java.util.List;
import java.util.Map;

import com.semanticintelligence.app.common.Command;
import com.semanticintelligence.app.domain.Si2SnAttr;
import com.semanticintelligence.app.domain.Si2SnNode;

/**
 * @author dinesh.bhavsar
 *
 */
public interface Si2SnAttrDAO {

	public Si2SnAttr save(Si2SnAttr obj);

	public Si2SnAttr merge(Si2SnAttr obj);

	public void delete(Si2SnAttr obj);

	public Map<String, Object> getByNodeId(List<Long> nodeId);

	public List<Si2SnAttr> getBySourceTargetId(Command command);

	public List<Long> getNewReferingIds(List<Long> referringId);

	public Map<String, Object> getDataForEdge(List<Long> referringId);

}