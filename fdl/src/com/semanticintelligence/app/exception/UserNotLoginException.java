/**
 * 
 */
package com.semanticintelligence.app.exception;

/**
 * @author dinesh.bhavsar
 * 
 */
@SuppressWarnings("serial")
public class UserNotLoginException extends Exception {

	private String exceptionMessage = "";

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public UserNotLoginException() {
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public UserNotLoginException(String useString)
	{
		if (useString.trim().length() > 0)
			exceptionMessage = exceptionMessage + "\n" + useString + "\n";
	}

	@Override
	public String getMessage()
	{
		return "Exception: \n Exception Message = " + exceptionMessage + super.getMessage();
	}

}
