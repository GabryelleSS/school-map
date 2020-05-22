package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.senac.school.model.Log;
import br.com.senac.school.session.Session;

public class LogDAO {
	private static Logger logger = LogManager.getLogger(LogDAO.class);

	private Connection connection = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	public List<Log> findAll() {

		List<Log> logs = new ArrayList<Log>();

		String sql = "SELECT * FROM app_logs WHERE ENTRY_DATE > ?";

		try {
			connection = ConnectionFactory.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, Session.data);
			ps.executeQuery();
			rs = ps.getResultSet();

			while (rs.next()) {

				String id = rs.getString("LOG_ID");
				String data = rs.getString("ENTRY_DATE");
				String logger = rs.getString("LOGGER");
				String level = rs.getString("LOG_LEVEL");
				String message = rs.getString("MESSAGE");
				logs.add(new Log(id, data, logger, level, message));
			}

			return logs;
		} catch (Exception e) {
			logger.error("Erro consultando logs do sistema", e);
		} finally {
			try {
				connection.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				logger.error("Erro ao fechar os recursos", e);
			}
		}
		return logs;
	}

	public void remove(Log log) {
		String sql = " DELETE FROM app_logs where LOG_ID = ?";

		try {
			connection = ConnectionFactory.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, log.getId().getValue());
			ps.execute();
		} catch (SQLException e) {
			logger.error("Erro ao tentar deletar log", e);
		} finally {
			try {
				connection.close();
				ps.close();
			} catch (Exception e) {
				logger.error("Erro ao fechar os recursos", e);
			}
		}

	}
}
