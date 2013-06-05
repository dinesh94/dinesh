package com.semanticintelligence.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CompanyInfoController {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "/company-info")
	public String conceptMap(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "/company-info";
	}
}
