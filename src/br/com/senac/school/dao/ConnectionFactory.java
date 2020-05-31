package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.com.senac.school.util.LoadProperties;

public class ConnectionFactory {

	public static Connection getConnection() {
		try {
			Properties properties = new LoadProperties().load("/br/com/senac/properties/db.properties");
			String url = properties.getProperty("dburl");
			return DriverManager.getConnection(url, properties);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
