package br.com.senac.school.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {

	public Properties load(String file) {
		try (InputStream inputStream = getClass().getResourceAsStream(file)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
