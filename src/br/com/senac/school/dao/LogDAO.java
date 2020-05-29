package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.senac.school.model.Log;

public class LogDAO {

	private static Connection connection = null;
	private static PreparedStatement ps = null;

	public static int insert(Log log) {
		String sql = " INSERT INTO app_logs (DATA, EVENTO,LEVEL,IP) VALUES (?,?,?,?)";

		try {
			connection = ConnectionFactory.getConnection();
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, log.getData().getValue());
			ps.setString(2, log.getEvento().getValue());
			ps.setString(3, log.getLevel().getValue());
			ps.setString(4, log.getIp().getValue());
			ps.execute();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				while (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (SQLException e) {
		} finally {
			try {
				connection.close();
				ps.close();
			} catch (Exception e) {
			}
		}
		return 0;

	}

	public static void remove(int id) {
		String sql = " DELETE FROM app_logs where LOG_ID = ?";

		try {
			connection = ConnectionFactory.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
		} finally {
			try {
				connection.close();
				ps.close();
			} catch (Exception e) {
			}
		}

	}
}
