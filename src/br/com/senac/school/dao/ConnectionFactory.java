package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static Connection conn;

	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school-map?serverTimezone=UTC", "developer",
					"104580");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

}
