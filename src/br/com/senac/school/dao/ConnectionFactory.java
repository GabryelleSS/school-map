package br.com.senac.school.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	public static Connection getConnection() {
		try {
			Properties properties = loadProperties();
			String url = properties.getProperty("dburl");
			return DriverManager.getConnection(url, properties);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream inputStream = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(inputStream);
			return props;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
