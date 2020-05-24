package br.com.senac.school.dao;

import java.util.List;

import br.com.senac.school.model.Usuario;

public interface UsuarioDao {

	boolean verify(String email);
	
	List<Usuario> findByEmail(String email);
	
	void resetPassword(String email, String password);

	int save(Usuario usuario);

	void update(Usuario usuario);

	void openConnection();
	
	void closeConnection();
	
}