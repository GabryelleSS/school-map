package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.senac.school.model.ContactUs;

public class ContactUsDaoImpl implements ContactUsDao {

	private Connection conn;

	@Override
	public void save(ContactUs contactUs) {

		String sql = "INSERT INTO contact_message (nome,cpf,email,tipo,mensagem,data)" + "VALUES (?,?,?,?,?,?)";

		try {
			openConnection();

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, contactUs.getNome());
				ps.setString(2, contactUs.getCpf());
				ps.setString(3, contactUs.getEmail());
				ps.setString(4, contactUs.getType());
				ps.setString(5, contactUs.getMessage());
				ps.setString(6, contactUs.getData());
				ps.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void openConnection() {
		conn = ConnectionFactory.getConnection();

	}

	@Override
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
