package com.caner.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDatabase {
	Connection getConnect = null;

	public Connection getConnection(String dbName, String userName, String password) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String url = "jdbc:MySQL://localhost/" + dbName
					+ "?serverTimezone=GMT&useSSL=false&characterEncoding=UTF-8";
			getConnect = DriverManager.getConnection(url, userName, password);
			System.out.println("\nbaglandi");
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
			e.printStackTrace();
		}
		return getConnect;
	}
}