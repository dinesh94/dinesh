package com.rage.siapp.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.rage.siapp.utils.Configuration;

public class DatabaseConnection 
{
	private static String CONNECTION_DRIVER_NAME = "db.connection.driver" ;
	private static String CONNECTION_STRING_PROPERTY = "db.connection.string" ;
	private static String CONNECTION_USERNAME = "db.connection.username" ;
	private static String CONNECTION_PASSWORD = "db.connection.password" ;
	
	private static String LSCONNECTION_DRIVER_NAME = "ls.db.connection.driver" ;
	private static String LSCONNECTION_STRING_PROPERTY = "ls.db.connection.string" ;
	private static String LSCONNECTION_USERNAME = "ls.db.connection.username" ;
	private static String LSCONNECTION_PASSWORD = "ls.db.connection.password" ;
	
	private static String GOLDCONNECTION_DRIVER_NAME = "gold.db.connection.driver" ;
	private static String GOLDCONNECTION_STRING_PROPERTY = "gold.db.connection.string" ;
	private static String GOLDCONNECTION_USERNAME = "gold.db.connection.username" ;
	private static String GOLDCONNECTION_PASSWORD = "gold.db.connection.password" ;
	
	
	public static Connection getLSConnection()
	{
		Connection conn = null ;
		
		String driver = Configuration.getProperty(LSCONNECTION_DRIVER_NAME) ;
		String connectionString = Configuration.getProperty(LSCONNECTION_STRING_PROPERTY) ;
		String username = Configuration.getProperty(LSCONNECTION_USERNAME) ;
		String password = Configuration.getProperty(LSCONNECTION_PASSWORD) ;
		
		try
		{			
			Class.forName(driver) ;
			conn = DriverManager.getConnection(connectionString, username, password) ;
		}
		catch (Exception e) 
		{
			System.err.println("ERROR IN GETTING THE LS CONNECTION : " + e.getMessage()) ;
			System.err.println("\tDRIVER = " + driver) ;
			System.err.println("\tCONNECTION STRING = " + connectionString) ;
			System.err.println("\tUSERNAME = " + username) ;
			System.err.println("\tPASSWORD = " + password) ;
			e.printStackTrace() ;
		}
		
		return conn ;
	}
	
	public static Connection getRageDevConnection()
	{
		Connection conn = null ;
		
		String driver = Configuration.getProperty(CONNECTION_DRIVER_NAME) ;
		String connectionString = Configuration.getProperty(CONNECTION_STRING_PROPERTY) ;
		String username = "ragedev" ;
		String password = "ragedev" ;
		
		try
		{			
			Class.forName(driver) ;
			conn = DriverManager.getConnection(connectionString, username, password) ;
		}
		catch (Exception e) 
		{
			System.err.println("ERROR IN GETTING THE CONNECTION : " + e.getMessage()) ;
			System.err.println("\tDRIVER = " + driver) ;
			System.err.println("\tCONNECTION STRING = " + connectionString) ;
			System.err.println("\tUSERNAME = " + username) ;
			System.err.println("\tPASSWORD = " + password) ;
			e.printStackTrace() ;
		}
		
		return conn ;
	}
	
	public static Connection getConnection()
	{
		Connection conn = null ;
		
		String driver = Configuration.getProperty(CONNECTION_DRIVER_NAME) ;
		String connectionString = Configuration.getProperty(CONNECTION_STRING_PROPERTY) ;
		String username = Configuration.getProperty(CONNECTION_USERNAME) ;
		String password = Configuration.getProperty(CONNECTION_PASSWORD) ;
		
		System.out.println(driver + "\t" + connectionString + "\t" + username + "\t" + password) ;
		
		try
		{
			Class.forName(driver) ;
			conn = DriverManager.getConnection(connectionString, username, password) ;
		}
		catch (Exception e) 
		{
			System.err.println("ERROR IN GETTING THE CONNECTION : " + e.getMessage()) ;
			System.err.println("\tDRIVER = " + driver) ;
			System.err.println("\tCONNECTION STRING = " + connectionString) ;
			System.err.println("\tUSERNAME = " + username) ;
			System.err.println("\tPASSWORD = " + password) ;
			e.printStackTrace() ;
		}
		
		return conn ;
	}

	public static Connection getGOLDConnection() 
	{
Connection conn = null ;
		
		String driver = Configuration.getProperty(GOLDCONNECTION_DRIVER_NAME) ;
		String connectionString = Configuration.getProperty(GOLDCONNECTION_STRING_PROPERTY) ;
		String username = Configuration.getProperty(GOLDCONNECTION_USERNAME) ;
		String password = Configuration.getProperty(GOLDCONNECTION_PASSWORD) ;
		
		System.out.println(driver + "\t" + connectionString + "\t" + username + "\t" + password) ;
		
		try
		{
			Class.forName(driver) ;
			conn = DriverManager.getConnection(connectionString, username, password) ;
		}
		catch (Exception e) 
		{
			System.err.println("ERROR IN GETTING THE GOLD CONNECTION : " + e.getMessage()) ;
			System.err.println("\tDRIVER = " + driver) ;
			System.err.println("\tCONNECTION STRING = " + connectionString) ;
			System.err.println("\tUSERNAME = " + username) ;
			System.err.println("\tPASSWORD = " + password) ;
			e.printStackTrace() ;
		}
		
		return conn ;
	}
}
