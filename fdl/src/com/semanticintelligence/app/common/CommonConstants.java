/**
 * 
 */
package com.semanticintelligence.app.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author dinesh.bhavsar
 * 
 */
public class CommonConstants {

	public static final int DEFAULT_WEIGHT = 7;

	public static final int DEFAULT_WEIGHT_DOT = 4;

	public static final int DEFAULT_WEIGHT_SPOT_NODE = 12;

	public static final String KEY_ENTITY_TYPE = "Entity_Type";

	public static String Entity_COMPANY = "Company";
	public static String Entity_GEO = "Geography";
	public static String Entity_REGULATORY_BODY = "Regulatory Body";
	public static String Entity_TOPIC = "Topic";
	public static String ENTITY_NAME_OF_PERSON = "Name of a person";

	public static final String ENTITY_TO_IGNODE = Entity_TOPIC;

	public static List<String> ENTITY_TYPE_TO_CONSIDER = new ArrayList<String>();
	static {
		ENTITY_TYPE_TO_CONSIDER.add("Company");
		ENTITY_TYPE_TO_CONSIDER.add("Name of a person");
		ENTITY_TYPE_TO_CONSIDER.add("Geography");
	}

	public static SimpleDateFormat formatter_rage_shortyr = new SimpleDateFormat("dd-MMM-yy");
	public static SimpleDateFormat formatter_rage = new SimpleDateFormat("dd-MMM-yyyy");
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
	public static SimpleDateFormat fullYearSDF = new SimpleDateFormat("MM/dd/yyyy");

	public static DecimalFormat decimalFormatMatchedNews = new DecimalFormat("#.##");
	public static DecimalFormat decimalFormatVolume = new DecimalFormat("#");
	public static DecimalFormat decimalFormat = new DecimalFormat("#.##");

	public static String[] useToForExceptionMessageSending = { "dinesh.bhavsar@creditpointe.com", "Pramod.Nikam@creditpointe.com", "Mahantesh.Kavishetti@creditpointe.com" };

	public static String[] useToForProcessMessageSending = { "Kenneth.Mascarenhas@creditpointe.com", "dinesh.bhavsar@creditpointe.com", "Pramod.Nikam@creditpointe.com", "Mahantesh.Kavishetti@creditpointe.com" };

	public static String useBccForExceptionMessageSending = "dinesh.bhavsar@creditpointe.com";

	/**
	 * @param level1
	 * @return
	 */
	public static String removeAllSpecialChars(String string) {
		return string.replaceAll(" ", "").replaceAll("\'", "").replaceAll("&", "").replaceAll("/", "").replaceAll("-", "");
	}

}
