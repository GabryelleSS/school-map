package br.com.senac.school.dao;

import java.util.List;
import java.util.Optional;

import br.com.senac.school.model.Usuario;

public interface UsuarioDao {

	Optional<Usuario> findByCpf(String cpf);
	
	List<String> passwordLogin(String email);
	
	List<Usuario> findByEmail(String email);
	
	List<Usuario> findAll();

	void save(Usuario usuario);

	void update(Usuario usuario);

	void delete(String cpf);
	
	void openConnection();
	
	void closeConnection();
	
	void resetPassword(String email, String password);
}