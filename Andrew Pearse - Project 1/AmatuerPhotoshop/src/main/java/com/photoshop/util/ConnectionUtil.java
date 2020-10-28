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
	private String url = "jdbc:postgresql://cardealershipdb.cebtidajd26p.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=project1";
	private String username = "amateur_photoshop";
	private String password = "bungus";
	
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
