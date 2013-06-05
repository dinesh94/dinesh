/**
 * 
 */
package com.semanticintelligence.app.util;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.semanticintelligence.app.service.EmailSender;

/**
 * @author dinesh.bhavsar
 * 
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	@Resource
	private EmailSender emailSender;

	private static Logger logger = Logger.getLogger(ExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		HttpSession session = request.getSession();
		if (session != null) {

			String message = SIUtil.getExceptionMessage(ex);
			logger.error("ExceptionMessage = " + message);

			SIUtil.sendErrorMessage(request, emailSender, ex);

			request.setAttribute("message", message);

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			return super.doResolveException(request, response, handler, ex);
		} else {
			return null;
		}
	}

}