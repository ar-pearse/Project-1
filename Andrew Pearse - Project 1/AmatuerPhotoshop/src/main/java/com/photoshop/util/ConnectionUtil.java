package com.photoshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static ConnectionUtil instance;
	private String url = System.getenv("url");
	private String username = System.getenv("username");
	private String password = System.getenv("password");
	
	private ConnectionUtil() {}
	
	public static ConnectionUtil getInstance() {
		if (instance == null)
			instance = new ConnectionUtil();
		
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, username, password);
	}
}
