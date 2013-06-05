package com.semanticintelligence.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.semanticintelligence.app.domain.Si2DocStore;
import com.semanticintelligence.app.domain.Si2SnConcept;
import com.semanticintelligence.app.domain.Si2SnConceptAttr;

public class MasterData {

	public static List<Si2SnConcept> companyList;
	public static List<Si2SnConcept> geographyList;
	public static List<Si2SnConcept> regulatoryBodyList;
	public static List<Si2SnConcept> topicList;
	public static List<Si2SnConcept> analystNameList;
	public static List<Si2DocStore> documentList;

	public static List<Si2SnConcept> allConcepts = new ArrayList<Si2SnConcept>();

	public static Set<Long> allConceptIds = new HashSet<Long>();

	public static Map<Long, List<Si2SnConceptAttr>> conceptIdAttrMap = new HashMap<Long, List<Si2SnConceptAttr>>();
}
