package com.semanticintelligence.app.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.semanticintelligence.app.domain.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(LoginInterceptor.class);

	private static final List<String> unRestrictedURLs = new ArrayList<String>();
	static {
		String url;

		url = "/login.htm";
		unRestrictedURLs.add(url);

		url = "/welcome.htm";
		unRestrictedURLs.add(url);

		url = "/logout.htm";
		unRestrictedURLs.add(url);

		url = "/index.htm";
		unRestrictedURLs.add(url);

		url = "/login";
		unRestrictedURLs.add(url);

		url = "/";
		unRestrictedURLs.add(url);

		url = "/j_spring_security_check";
		unRestrictedURLs.add(url);

		url = "/j_spring_security_logout";
		unRestrictedURLs.add(url);
		
		url = "/concept-map";
		unRestrictedURLs.add(url);
		
		url = "/expand-node";
		unRestrictedURLs.add(url);
		
		url = "/node-information";
		unRestrictedURLs.add(url);
		
		url = "/home";
		unRestrictedURLs.add(url);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		User userObject = null;
		String servletPath;

		servletPath = request.getServletPath();
		logger.info("Request ServletPath : " + servletPath);

		if (servletPath.contains(".htm") && !unRestrictedURLs.contains(servletPath)) {

			userObject = (User) request.getSession().getAttribute("userDetails");

			if (request.getRequestedSessionId() == null || userObject == null) {

				logger.info("USER NOT LOGIN EXCEPTION");

				if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").trim().toString().equalsIgnoreCase("XMLHttpRequest")) {

					logger.info("Inside AuthenticationIntercepter identified ajax request");

					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				} else {
					response.sendRedirect(request.getContextPath() + "/login.htm?error=1&sessiontimeout=true");
				}

				return false;

			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
