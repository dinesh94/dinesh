/**
 * 
 */
package com.semanticintelligence.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.semanticintelligence.app.common.CommonConstants;
import com.semanticintelligence.app.common.MasterData;
import com.semanticintelligence.app.domain.Si2DocStore;
import com.semanticintelligence.app.domain.Si2SnConcept;
import com.semanticintelligence.app.domain.Si2SnConceptAttr;
import com.semanticintelligence.app.service.Si2DocStoreService;
import com.semanticintelligence.app.service.Si2SnConceptAttrService;
import com.semanticintelligence.app.service.Si2SnConceptService;

/**
 * @author dinesh.bhavsar
 * 
 */
@Controller
public class HomePageController implements InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());

	String KEY_ENTITY_TYPE = "Entity_Type";
	String KEY_KEYWORDS = "Keywords";

	@Resource
	private Si2SnConceptAttrService conceptAttrService;

	@Resource
	private Si2SnConceptService si2SnConceptService;

	@Resource
	private Si2DocStoreService si2DocStoreService;

	@RequestMapping("/home")
	public String homePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		 * 
		 * String Entity_COMPANY = "Company";
			String Entity_GEO = "Geography";
			String Entity_REGULATORY_BODY = "Regulatory Body";
			String Entity_TOPIC = "Topic";
			String ENTITY_ANALYST_NAME = "Analyst Name";
		 * 
		 * */

		model.put("companyList", MasterData.companyList);
		model.put("geographyList", MasterData.geographyList);
		model.put("regulatoryBodyList", MasterData.regulatoryBodyList);
		model.put("topicList", MasterData.topicList);
		model.put("analystNameList", MasterData.analystNameList);
		model.put("documentList", MasterData.documentList);

		//List<Si2SnConceptAttr> conceptAttrs = conceptAttrService.getSi2SnConceptAttr();

		return "/home";
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		List<Si2SnConcept> companyList;
		List<Si2SnConcept> geographyList;
		List<Si2SnConcept> regulatoryBodyList;
		List<Si2SnConcept> topicList;
		List<Si2SnConcept> analystNameList;
		List<Si2DocStore> documentList;

		companyList = MasterData.companyList;

		if (companyList == null) {
			companyList = si2SnConceptService.getMasterConceptList(CommonConstants.Entity_COMPANY);
			MasterData.companyList = companyList;
		}

		geographyList = MasterData.geographyList;
		if (geographyList == null) {
			geographyList = si2SnConceptService.getMasterConceptList(CommonConstants.Entity_GEO);
			MasterData.geographyList = geographyList;
		}

		regulatoryBodyList = MasterData.regulatoryBodyList;
		if (regulatoryBodyList == null) {
			regulatoryBodyList = si2SnConceptService.getMasterConceptList(CommonConstants.Entity_REGULATORY_BODY);
			MasterData.regulatoryBodyList = regulatoryBodyList;
		}

		topicList = MasterData.topicList;
		if (topicList == null) {
			topicList = si2SnConceptService.getMasterConceptList(CommonConstants.Entity_TOPIC);
			MasterData.topicList = topicList;
		}

		analystNameList = MasterData.analystNameList;
		if (analystNameList == null) {
			analystNameList = si2SnConceptService.getMasterConceptList(CommonConstants.ENTITY_NAME_OF_PERSON);
			MasterData.analystNameList = analystNameList;
		}

		documentList = MasterData.documentList;
		if(documentList == null) {
			documentList = si2DocStoreService.getMasterDocumentList();
			MasterData.documentList = documentList;
		}
		
		MasterData.allConcepts.addAll(companyList);
		MasterData.allConcepts.addAll(geographyList);
		MasterData.allConcepts.addAll(regulatoryBodyList);
		MasterData.allConcepts.addAll(topicList);
		MasterData.allConcepts.addAll(analystNameList);

		for (Si2SnConcept si2SnConcept : MasterData.allConcepts) {
			MasterData.allConceptIds.add(si2SnConcept.getConceptId());
		}

		List<Si2SnConceptAttr> conceptAttrs = conceptAttrService.getSi2SnConceptAttr();

		for (Si2SnConceptAttr si2SnConceptAttr : conceptAttrs) {

			if (!MasterData.conceptIdAttrMap.containsKey(si2SnConceptAttr.getConceptId())) {
				MasterData.conceptIdAttrMap.put(si2SnConceptAttr.getConceptId(), new ArrayList<Si2SnConceptAttr>());
			}

			MasterData.conceptIdAttrMap.get(si2SnConceptAttr.getConceptId()).add(si2SnConceptAttr);
		}

	}
}
