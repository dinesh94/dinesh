/**
 * 
 */
package com.semanticintelligence.app.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jtpl.Template;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;

import com.semanticintelligence.app.common.CommonConstants;
import com.semanticintelligence.app.domain.User;
import com.semanticintelligence.app.exception.UserNotLoginException;
import com.semanticintelligence.app.service.EmailSender;

/**
 * @author Girish.Deshpande
 * 
 */
public class SIUtil {

	static Logger logger = Logger.getLogger(SIUtil.class);

	/**
	 * @return Pramod S. Nikam
	 */
	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static void setUserID(HttpServletRequest request, String userId, String user_type) {
		request.getSession().setAttribute("userID", request.getSession().getAttribute("userID"));
	}

	public static Integer getUserID(HttpServletRequest request) {
		if (request.getSession().getAttribute("userID") != null)
			return Integer.parseInt(request.getSession().getAttribute("userID").toString());
		// TODO Need to modify code here
		return 1;
	}

	public static String trimAphostrophesAndUnderscores(String statement) {
		if (statement != null) {
			statement = statement.replaceAll("\'", "");
			statement = statement.replaceAll("\"", "");
			statement = statement.replaceAll("_", " ");
		}

		return statement;
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void sendErrorMessage(HttpServletRequest useHttpServletRequest, final EmailSender emailSender, Exception ex) {

		String reqUrl = useHttpServletRequest.getRequestURL().toString();
		StringBuffer messageStringBuffer = new StringBuffer();
		String from = "";

		if (reqUrl.indexOf("localhost") > 0 || reqUrl.indexOf("127.0.0.1") > 0)
			return;

		/*User userMaster = null;

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {

			userMaster = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			messageStringBuffer.append("Exception Occures For The User : " + userMaster.getUserId());
			messageStringBuffer.append(System.getProperty("line.separator"));
		}*/

		messageStringBuffer.append("Requested URL : " + reqUrl);
		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append("Requested Params : ");

		Map params = useHttpServletRequest.getParameterMap();
		Iterator i = params.keySet().iterator();

		while (i.hasNext()) {
			String key = (String) i.next();
			String value = ((String[]) params.get(key))[0];

			messageStringBuffer.append(System.getProperty("line.separator"));
			messageStringBuffer.append("param = " + key + " values = " + value);
		}

		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append("Session Id : " + useHttpServletRequest.getSession().getId());
		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append("Date : " + new Date().toString());
		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append(System.getProperty("line.separator"));

		String exceptionMessage = getExceptionMessage(ex);
		messageStringBuffer.append(exceptionMessage);

		messageStringBuffer.append(System.getProperty("line.separator"));
		messageStringBuffer.append(System.getProperty("line.separator"));

		String subject = "Exception from semanticintelligence application";
		//from = "@creditpointe.com";

		final SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject(subject);
		msg.setText(messageStringBuffer.toString());
		//msg.setFrom(from);
		msg.setTo(CommonConstants.useToForExceptionMessageSending);
		msg.setBcc(CommonConstants.useBccForExceptionMessageSending);

		new Thread() {
			@Override
			public void run() {
				emailSender.getMailSender().send(msg);
			}
		}.start();

	}

	public static void disableCaching(HttpServletResponse response) {

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		logger.info("Caching disabled");
	}

	public static String getChartColorCode(Double score) {
		if (score == null || score == 0.0) {

			return "#e5cf7c";
		} else if (score > 0.0) {
			return "#76a636"; // green
		} else {
			return "#b24e1c"; // red
		}
	}

	public static Integer getNumberOfDays(HttpServletRequest request, boolean readAttribute) {
		Object numberOfDays = request.getSession().getAttribute("numberOfDays");
		if (numberOfDays != null && readAttribute) {
			return Integer.parseInt(numberOfDays.toString());
		} else {
			return 90;
		}
	}

	public static String getEndDate(HttpServletRequest request) {
		Object startDate = request.getSession().getAttribute("endDate");
		if (startDate != null) {
			return startDate.toString();
		} else {
			return "";
		}
	}

	public static String getSource(String SourceName) {
		String Channel = "";
		if (SourceName != null) {

			SourceName = SourceName.replaceAll("http://", "");

			if (SourceName.indexOf("/") != -1) {
				SourceName = SourceName.substring(0, SourceName.indexOf("/"));
			}

			if (SourceName.indexOf("?") != -1) {
				SourceName = SourceName.substring(0, SourceName.indexOf("?"));
			}

			SourceName = SourceName.replaceAll("http://", "");

			if (SourceName.indexOf(".") != -1) {
				SourceName = SourceName.replaceAll(".com", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll("www.", "");
				SourceName = SourceName.replaceAll("www2.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".au", "");
				SourceName = SourceName.replaceAll("au.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".br", "");
				SourceName = SourceName.replaceAll("br.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".tv", "");
				SourceName = SourceName.replaceAll("tv.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".net", "");
				SourceName = SourceName.replaceAll("net.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".ge", "");
				SourceName = SourceName.replaceAll("ge.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".uk", "");
				SourceName = SourceName.replaceAll("uk.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".co", "");
				SourceName = SourceName.replaceAll("co.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".za", "");
				SourceName = SourceName.replaceAll("za.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".il", "");
				SourceName = SourceName.replaceAll("il.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".il", "");
				SourceName = SourceName.replaceAll("il.", "");
			}

			if (SourceName.indexOf(".") != -1) {
				SourceName = SourceName.replaceAll(".org", "");
				SourceName = SourceName.replaceAll("org.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".cn", "");
				SourceName = SourceName.replaceAll("cn.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".za", "");
				SourceName = SourceName.replaceAll("za.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".fr", "");
				SourceName = SourceName.replaceAll("fr.", "");
			}

			if (SourceName.indexOf(".") != -1) {
				SourceName = SourceName.replaceAll(".ph", "");
				SourceName = SourceName.replaceAll("ph.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".de", "");
				SourceName = SourceName.replaceAll("de.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".nz", "");
				SourceName = SourceName.replaceAll("nz.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".my", "");
				SourceName = SourceName.replaceAll("my.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".pk", "");
				SourceName = SourceName.replaceAll("pk.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".fi", "");
				SourceName = SourceName.replaceAll("fi.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".us", "");
				SourceName = SourceName.replaceAll("us.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".ru", "");
				SourceName = SourceName.replaceAll("ru.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".ae", "");
				SourceName = SourceName.replaceAll("ae.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".lb", "");
				SourceName = SourceName.replaceAll("lb.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".tbo", "");
				SourceName = SourceName.replaceAll("tbo.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replaceAll(".az", "");
				SourceName = SourceName.replaceAll("az.", "");
			}

			if (SourceName.indexOf(".") != -1) {

				SourceName = SourceName.replace('.', '#');
			}

			SourceName = SourceName.replaceAll("#", " ");

		}

		String temp = SourceName;

		if (SourceName != null && !"".equals(SourceName) && !" ".equals(SourceName) && !"null".equals(SourceName)) {
			temp = SourceName.substring(0, 1);
			temp = temp.toUpperCase();
			SourceName = SourceName.substring(1, SourceName.length());
			SourceName = temp + SourceName;
			Channel = SourceName;
		} else {
			Channel = "N0_SOURCE";
		}

		return Channel;
	}

	public static String getPropertyFromBundle(String propertyName) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("config");
			if (bundle.getString(propertyName) != null) {
				return bundle.getString(propertyName);
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String highLightParameters(String temp, String input, boolean isIntensityWord) {
		temp = temp + " ";
		if (input != null && input.length() != 0 && temp != null && temp.length() != 0) {
			input = input.replaceAll(Pattern.quote(","), " ");
			input = input.replaceAll(Pattern.quote("["), "");
			input = input.replaceAll(Pattern.quote("]"), "");
			String[] tokens = input.split(" ", 0);
			HashSet<String> hashSet = new HashSet<String>();
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i] != null && tokens[i].trim().length() != 0) {
					hashSet.add(tokens[i].trim());
				}
			}
			Iterator<String> itr = hashSet.iterator();
			while (itr.hasNext()) {
				String token = itr.next();
				temp = match(temp, token.trim(), isIntensityWord);
			}

		}
		return temp.trim();
	}

	private static String match(String temp, String token, boolean isIntensityWord) {
		try {
			if (token.trim().length() != 0) {
				token = token.replaceAll("class", "");
				token = token.replaceAll("style", "");
				token = token.replaceAll("Class", "");
				token = token.replaceAll("Style", "");
				token = token.replaceAll("AND", "");
				if ((token.equalsIgnoreCase("st")) || (token.equalsIgnoreCase("sty")) || (token.equalsIgnoreCase("styl")) || (token.equalsIgnoreCase("c")) || (token.equalsIgnoreCase("s")) || (token.equalsIgnoreCase("cl")) || (token.equalsIgnoreCase("cla")) || (token.equalsIgnoreCase("clas"))) {
					token = "";
				}
			}
			if (token.trim().length() != 0) {
				temp = " " + temp + " ";
				token = token.trim();
				Pattern pattern = Pattern.compile(token + "[^\\p{Space}]*", Pattern.CASE_INSENSITIVE);
				Matcher m = pattern.matcher(temp);
				while (m.find()) {
					for (int j = 0; j <= m.groupCount(); j++) {
						String groupStr = m.group(j);
						if (groupStr.trim().length() != 0 && groupStr.toLowerCase().startsWith(token.toLowerCase())) {
							if (isIntensityWord) {
								temp = temp.replaceAll(" " + Pattern.quote(groupStr.trim()) + " ", " <U class='italics' style='color:#f300a0;text-decoration:underline'>" + groupStr.trim() + " </U> ");

							} else {
								temp = temp.replaceAll(" " + Pattern.quote(groupStr.trim()) + " ", " <B class='selected'  style='color:#000;background-color:#FFFF00;padding-left:2px;padding-right:2px;text-decoration:none'>" + groupStr.trim() + " </B> ");

							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/** Displaying Current Date in specific semanticintelligence format **/
	public static String getCurrentDate() {
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
		String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		StringBuilder displayDate = new StringBuilder();
		Date date = new Date();
		String dateFormatString = "MM/dd/yyyy";
		SimpleDateFormat s = new SimpleDateFormat(dateFormatString);
		String dateSting = s.format(date);
		Calendar c = new GregorianCalendar(new Integer(dateSting.substring(6, 10)).intValue(), new Integer(dateSting.substring(0, 2)).intValue() - 1, new Integer(dateSting.substring(3, 5)).intValue());
		displayDate.append(days[c.get(Calendar.DAY_OF_WEEK) - 1]);
		displayDate.append(", ");
		displayDate.append(months[c.get(Calendar.MONTH)]);
		displayDate.append(" " + dateSting.substring(3, 5) + ", ");
		displayDate.append(dateSting.substring(6, 10));
		return displayDate.toString();

	}

	public static Integer getNoOfDays(String selectedTimeFrame) {
		Integer numberOfDays = null;

		if (selectedTimeFrame != null && !selectedTimeFrame.equalsIgnoreCase("null") && !selectedTimeFrame.equalsIgnoreCase("")) {
			if (new Integer(selectedTimeFrame).intValue() == 2) {

				numberOfDays = 7;

			} else if (new Integer(selectedTimeFrame).intValue() == 3) {

				numberOfDays = 30;

			} else if (new Integer(selectedTimeFrame).intValue() == 4) {

				numberOfDays = 90;

			} else if (new Integer(selectedTimeFrame).intValue() == 5) {

				numberOfDays = 180;

			} else if (new Integer(selectedTimeFrame).intValue() == 6) {

				numberOfDays = 365;

			}
		}

		return numberOfDays;
	}

	public static String SystemErrorOccured(HttpServletRequest request) {
		// request.getSession().invalidate();
		return "ErrorOccuredView";
	}

	public static String getFormattedDate(String date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yy");
		Date d = SIUtil.getDate(date);

		return d != null ? dateFormatter.format(d) : null;
	}

	public static Date getDate(String date) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date d = df.parse(date);

			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Date getDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date d = df.parse(date);

			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// adding method to roundup : G.C.D.

	public static float Round(float Rval, int Rpl) {
		float p = (float) Math.pow(10, Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return tmp / p;
	}

	/**
	 * @return dinesh.bhavsar
	 * @throws UserNotLoginException
	 */
	public static User getUser() throws UserNotLoginException {

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} else {
			throw new UserNotLoginException("User Not Login");
		}
	}

	public static Map<String, Object> getModelMapSuccess(String msg) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", true);
		modelMap.put("status", true);

		return modelMap;
	}

	public static Map<String, Object> getModelMapError(String msg) {

		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", msg);
		modelMap.put("success", false);
		modelMap.put("status", false);

		return modelMap;
	}

	public static void sendHTMLMessage(Template template, String[] to, String subject, List<MimeBodyPart> imageBodyPartList, MailSender mailSender) {

		logger.info("EmailSender.sendHTMLMessage() 7");

		try {

			final MimeMessage msg = new MimeMessage(getSession(mailSender));
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(msg, true);

			InternetAddress[] address = new InternetAddress[to.length];

			for (int i = 0; i < to.length; i++) {
				address[i] = new InternetAddress(to[i]);
			}

			mimeMessageHelper.setTo(address);

			String message = template.out();

			mimeMessageHelper.setSubject(subject);

			Multipart multipart = new MimeMultipart();
			MimeBodyPart bodyPart = new MimeBodyPart();

			bodyPart.setContent(message, "text/html");
			multipart.addBodyPart(bodyPart);

			if (imageBodyPartList != null && !imageBodyPartList.isEmpty()) {
				for (MimeBodyPart imageBodyPart : imageBodyPartList) {
					multipart.addBodyPart(imageBodyPart);
				}
			}

			msg.setContent(multipart);

			logger.info("EmailSender.sendHTMLMessage() 8");

			Transport.send(msg);

		} catch (Exception useException) {
			useException.printStackTrace();
		}
	}

	public static Session getSession(MailSender mailSender) {

		java.util.Properties props = new java.util.Properties();

		Properties provalues = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();

		props.put("mail.smtp.host", provalues.getProperty("mail.smtp.host"));
		props.put("mail.smtp.port", provalues.getProperty("mail.smtp.port"));
		props.put("mail.smtp.starttls.enable", provalues.getProperty("mail.smtp.starttls.enable"));
		props.put("mail.smtp.auth", provalues.getProperty("mail.smtp.auth"));
		props.put("mail.debug", provalues.getProperty("mail.debug"));
		props.put("mail.transport.protocol", provalues.getProperty("mail.transport.protocol"));
		props.put("mail.smtp.ssl.trust", provalues.getProperty("mail.smtp.ssl.trust"));
		props.put("mail.smtp.from", provalues.getProperty("mail.username"));

		final String userName = provalues.getProperty("mail.username");
		final String password = provalues.getProperty("mail.password");

		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		return mailSession;
	}

	public static boolean isProductionEnvironment(ServletContext useServletContext) {
		String isProductionEnvironment = useServletContext.getInitParameter("isProductionEnvironment");

		if (isProductionEnvironment == null)
			return true;
		else if (isProductionEnvironment.equalsIgnoreCase("true"))
			return true;
		else
			return false;

	}

	/**
	 * @param ex
	 * @return dinesh.bhavsar
	 */
	public static String getExceptionMessage(Exception exception) {
		StringBuilder messageBuffer = new StringBuilder();
		messageBuffer.append(exception.toString() + " at ");
		StackTraceElement elements[] = exception.getStackTrace();
		for (int i = 0, n = elements.length; i < n; i++) {
			messageBuffer.append(elements[i].getFileName());
			messageBuffer.append(" : ");
			messageBuffer.append(elements[i].getMethodName());
			messageBuffer.append(" ----> ");
			messageBuffer.append(elements[i].getLineNumber());
			messageBuffer.append(System.getProperty("line.separator"));
		}
		System.out.println(messageBuffer.toString());
		logger.error(messageBuffer.toString());
		return messageBuffer.toString();
	}

}
