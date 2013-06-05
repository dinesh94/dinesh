package com.semanticintelligence.app.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semanticintelligence.app.domain.User;
import com.semanticintelligence.app.exception.UserNotLoginException;
import com.semanticintelligence.app.util.SIUtil;

/**
 * @author dinesh.bhavsar
 */

@Controller
public class LoginController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Resource
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {

		//Clean the User session WorkQueue..
		HttpSession theSession = request.getSession();

		theSession.invalidate();

		return "redirect:/";
	}

	@RequestMapping({ "/", "/login.htm" })
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		ArrayList<String> errors = new ArrayList<String>();

		try {
			User userObject = SIUtil.getUser();

			if (userObject != null) {
				request.getSession().setAttribute("user", userObject);
				request.getSession().setAttribute("userID", userObject.getUserId());

				return "redirect:/home/";

			}
		} catch (Exception e) {
			logger.info("" + e.getMessage());
		}

		if (request.getParameter("error") != null && request.getParameter("error").equals("1")) {

			if (request.getParameter("sessiontimeout") != null) {
				errors.add("* Session timeout. Please re-login.");
			} else {
				errors.add("* Please enter valid Username");
				errors.add("* Please enter valid Password");
			}
			model.addAttribute("errors", errors);
		}

		return "/login";

	}

	@RequestMapping(value = "/welcome.htm")
	public String welcome(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, UserNotLoginException {

		logger.info("START");

		User userObject = SIUtil.getUser();

		request.getSession().setAttribute("userDetails", userObject);

		logger.info("result received " + userObject);

		request.getSession().setAttribute("record_date", SIUtil.getCurrentDate());

		if (userObject != null) {
			request.getSession().setAttribute("user", userObject);
			request.getSession().setAttribute("userID", userObject.getUserId());

			return "redirect:/home/";

		} else {

			return "redirect:/login?error=1";
		}

	}

	@RequestMapping(value = "/change-password.htm")
	public String changePassword(@RequestParam(value = "newPassword", required = true) String newPassword) throws IOException, UserNotLoginException {

		logger.info("START");

		logger.info("LoginController.changePassword() New Password Text = " + passwordEncoder.encodePassword(newPassword, null));

		logger.info("END");

		return "change-password";

	}

}
