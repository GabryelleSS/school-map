package br.com.senac.school.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

	private Connection conn;

	@Override
	public int save(Usuario usuario) {
		int id = 0;
		int endereco_id = saveEndereco(usuario);

		String sql = "INSERT INTO usuario (ativo,celular,cpf,dataNascimento,email,estadoCivil,nome,senha,sexo,telefone,endereco_id,preferencia_contato)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			openConnection();

			try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setBoolean(1, usuario.isAtivo());
				ps.setString(2, usuario.getCelular());
				ps.setString(3, usuario.getCpf());
				ps.setDate(4, Date.valueOf(usuario.getDataNascimento()));
				ps.setString(5, usuario.getEmail());
				ps.setString(6, usuario.getEstadoCivil());
				ps.setString(7, usuario.getNome());
				ps.setString(8, usuario.getSenha());
				ps.setString(9, usuario.getSexo());
				ps.setString(10, usuario.getTelefone());
				ps.setInt(11, endereco_id);
				ps.setString(12, usuario.getPreferenciaContato());

				ps.executeUpdate();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					while (rs.next()) {
						id = rs.getInt(1);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return id;
	}

	private int saveEndereco(Usuario usuario) {

		EnderecoUsuario endereco = usuario.getEndereco();

		String sql = "INSERT INTO endereco_usu (bairro,cep,cidade,complemento,endereco,estado,numero,uf,latitude,longitude)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

		try {
			openConnection();

			try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, endereco.getBairro());
				ps.setString(2, endereco.getCep());
				ps.setString(3, endereco.getCidade());
				ps.setString(4, endereco.getComplemento());
				ps.setString(5, endereco.getEndereco());
				ps.setString(6, endereco.getEstado());
				ps.setString(7, endereco.getNumero().toString());
				ps.setString(8, endereco.getUf());
				ps.setDouble(9, endereco.getLatitude());
				ps.setDouble(10, endereco.getLongitude());

				ps.executeUpdate();

				try (ResultSet keys = ps.getGeneratedKeys()) {

					while (keys.next()) {
						return keys.getInt(1);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return 0;
	}

	@Override
	public void update(Usuario usuario) {
		String sql = "UPDATE usuario SET celular = ?,cpf = ?,dataNascimento = ?,email = ?,estadoCivil = ?,nome = ?,senha = ?,sexo = ?,telefone = ?, preferencia_contato =? WHERE id = ?";

		try {
			openConnection();

			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, usuario.getCelular());
				ps.setString(2, usuario.getCpf());
				ps.setDate(3, Date.valueOf(usuario.getDataNascimento()));
				ps.setString(4, usuario.getEmail());
				ps.setString(5, usuario.getEstadoCivil());
				ps.setString(6, usuario.getNome());
				ps.setString(7, usuario.getSenha());
				ps.setString(8, usuario.getSexo());
				ps.setString(9, usuario.getTelefone());
				ps.setString(10, usuario.getPreferenciaContato());
				ps.setInt(11, usuario.getId());

				updateEndereco(usuario);

				ps.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	private void updateEndereco(Usuario usuario) {

		EnderecoUsuario endereco = usuario.getEndereco();

		String sql = "UPDATE endereco_usu SET bairro = ?,cep = ?,cidade = ?,complemento = ?,endereco = ?,estado = ?,numero = ?,uf = ?,latitude = ?,longitude = ? where id_endereco = ?";

		try {
			openConnection();

			try (PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setString(1, endereco.getBairro());
				ps.setString(2, endereco.getCep());
				ps.setString(3, endereco.getCidade());
				ps.setString(4, endereco.getComplemento());
				ps.setString(5, endereco.getEndereco());
				ps.setString(6, endereco.getEstado());
				ps.setString(7, endereco.getNumero().toString());
				ps.setString(8, endereco.getUf());
				ps.setDouble(9, endereco.getLatitude());
				ps.setDouble(10, endereco.getLongitude());

				ps.setInt(11, endereco.getId());

				ps.execute();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

	}

	@Override
	public boolean verify(String email) {
		List<String> list = new ArrayList<String>();

		String sql = "SELECT id from usuario where email = ?";

		Connection conn = ConnectionFactory.getConnection();

		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ps.execute();

			try (ResultSet rs = ps.getResultSet()) {

				while (rs.next()) {
					list.add(rs.getString("id"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

		return !list.isEmpty();
	}

	@Override
	public void resetPassword(String email, String password) {
		Runnable resetTask = () -> {
			String sql = "UPDATE usuario u " + "SET u.senha = ? " + "WHERE u.email = ?";
			try {
				openConnection();

				try (PreparedStatement ps = conn.prepareStatement(sql)) {
					ps.setString(1, password);
					ps.setString(2, email);
					ps.execute();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		};
		Thread thread = new Thread(resetTask);
		thread.start();
	}

	@Override
	public List<Usuario> findByEmail(String email) {

		List<Usuario> list = new ArrayList<Usuario>();

		String sql = "SELECT * FROM usuario u INNER JOIN endereco_usu e ON u.endereco_id = e.id_endereco WHERE u.email = ?";

		try {
			openConnection();
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, email);
				ps.execute();

				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						String nome = rs.getString("nome");
						String sexo = rs.getString("sexo");
						String senha = rs.getString("senha");
						String cpf = rs.getString("cpf");
						String email_usu = rs.getString("email");
						String estadoCivil = rs.getString("estadoCivil");
						String telefone = rs.getString("telefone");
						String celular = rs.getString("celular");
						String preferenciaContato = rs.getString("preferencia_contato");
						Date date = rs.getDate("dataNascimento");
						int id = rs.getInt("id");
						LocalDate dataNascimento = date.toLocalDate();

						String bairro = rs.getString("bairro");
						String cep = rs.getString("cep");
						String cidade = rs.getString("cidade");
						String complemento = rs.getString("complemento");
						String endereco_usu = rs.getString("endereco");
						String estado = rs.getString("estado");
						String uf = rs.getString("uf");
						String numero = rs.getString("numero");
						int id_endereco = rs.getInt("id_endereco");
						double latitude = rs.getDouble("latitude");
						double longitude = rs.getDouble("longitude");

						EnderecoUsuario endereco = new EnderecoUsuario(endereco_usu, bairro, complemento,
								Integer.valueOf(numero), cep, cidade, estado, uf, latitude, longitude);

						endereco.setId(id_endereco);

						Usuario usuario = new Usuario(nome, cpf, sexo, dataNascimento, estadoCivil, telefone, celular,
								endereco, email_usu, senha, preferenciaContato);

						usuario.setId(id);

						list.add(usuario);
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
