package br.com.senac.school.log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import br.com.senac.school.dao.LogDAO;
import br.com.senac.school.model.Log;

public class Logs {

	private static Map<String, Log> logs;

	static {
		logs = new LinkedHashMap<>();
	}

	public static void info(String evento) {
		Log log = create(evento);
		int id = LogDAO.insert(log);
		log.setId(String.valueOf(id));

		logs.put(String.valueOf(id), log);
	}

	public static Collection<Log> findAll() {
		return logs.values();
	}

	public static void remove(String id) {

		LogDAO.remove(Integer.valueOf(id));
		logs.remove(id);

	}

	public static void clear() {
		logs.clear();
	}

	private static Log create(String evento) {

		String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));

		try {
			return new Log(data, evento, Level.INFO.toString(), InetAddress.getLocalHost().getHostAddress());

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}

	}

}
