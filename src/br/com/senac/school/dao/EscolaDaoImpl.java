package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.senac.school.model.Escola;

public class EscolaDaoImpl implements EscolaDao {

	private Connection conn;

	@Override
	public List<Escola> findByBairro(String bairro) {
		List<Escola> list = new ArrayList<>();

		String sql = "SELECT * FROM escola WHERE bairro LIKE ?";

		try {
			openConnection();
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, "%".concat(bairro).concat("%"));
				ps.execute();

				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						String tipo = rs.getString("tipo");
						String nome = rs.getString("nome");
						String telefone1 = rs.getString("telefone1");
						String telefone2 = rs.getString("telefone2");
						String cod_distrito = rs.getString("cod_distrito");
						String distrito = rs.getString("distrito");
						String endereco = rs.getString("endereco");
						String bairro_esc = rs.getString("bairro");
						boolean situacao = rs.getBoolean("ativa");
						String cep = rs.getString("cep");
						int numero = rs.getInt("numero");
						int id = rs.getInt("id");

						Escola escola = new Escola(id, tipo, nome, situacao, telefone1, telefone2, cod_distrito,
								distrito, endereco, bairro_esc, cep, numero);

						list.add(escola);
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return list;

	}
	@Override
	public List<Escola> findByNameOrType(String value) {
		List<Escola> list = new ArrayList<>();
		
		String sql = "SELECT * FROM escola WHERE nome LIKE ? OR tipo LIKE ? LIMIT 30";
		
		try {
			openConnection();
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, "%".concat(value).concat("%"));
				ps.setString(2, "%".concat(value).concat("%"));
				ps.execute();
				
				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						String tipo = rs.getString("tipo");
						String nome = rs.getString("nome");
						String telefone1 = rs.getString("telefone1");
						String telefone2 = rs.getString("telefone2");
						String cod_distrito = rs.getString("cod_distrito");
						String distrito = rs.getString("distrito");
						String endereco = rs.getString("endereco");
						String bairro_esc = rs.getString("bairro");
						boolean situacao = rs.getBoolean("ativa");
						String cep = rs.getString("cep");
						int numero = rs.getInt("numero");
						int id = rs.getInt("id");
						
						Escola escola = new Escola(id, tipo, nome, situacao, telefone1, telefone2, cod_distrito,
								distrito, endereco, bairro_esc, cep, numero);
						
						list.add(escola);
					}
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return list;
		
	}

	@Override
	public Escola findById(int id) {

		Escola escola = null;

		String sql = "SELECT * FROM escola WHERE id = ?";

		try {
			openConnection();
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setInt(1, id);
				ps.execute();

				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						String tipo = rs.getString("tipo");
						String nome = rs.getString("nome");
						String telefone1 = rs.getString("telefone1");
						String telefone2 = rs.getString("telefone2");
						String cod_distrito = rs.getString("cod_distrito");
						String distrito = rs.getString("distrito");
						String endereco = rs.getString("endereco");
						String bairro_esc = rs.getString("bairro");
						boolean situacao = rs.getBoolean("ativa");
						String cep = rs.getString("cep");
						int numero = rs.getInt("numero");

						escola = new Escola(id, tipo, nome, situacao, telefone1, telefone2, cod_distrito, distrito,
								endereco, bairro_esc, cep, numero);

					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return escola;

	}

	@Override
	public List<Escola> findByLatElong(double latitude, double longitude) {
		List<Escola> list = new ArrayList<>();
		
		final int LIMITE = 30;

		String sql = "CALL EscolasPorLatELong(?,?,?)";

		try {
			openConnection();
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setDouble(1, latitude);
				ps.setDouble(2, longitude);
				ps.setDouble(3, LIMITE);
				ps.execute();

				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						String tipo = rs.getString("tipo");
						String nome = rs.getString("nome");
						String telefone1 = rs.getString("telefone1");
						String telefone2 = rs.getString("telefone2");
						String cod_distrito = rs.getString("cod_distrito");
						String distrito = rs.getString("distrito");
						String endereco = rs.getString("endereco");
						String bairro_esc = rs.getString("bairro");
						boolean situacao = rs.getBoolean("ativa");
						String cep = rs.getString("cep");
						int numero = rs.getInt("numero");
						int id = rs.getInt("id");

						Escola escola = new Escola(id, tipo, nome, situacao, telefone1, telefone2, cod_distrito,
								distrito, endereco, bairro_esc, cep, numero);

						list.add(escola);
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return list;

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
