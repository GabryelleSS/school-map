package br.com.senac.school.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.EstadoCivil;
import br.com.senac.school.model.Genero;
import br.com.senac.school.model.TelefoneUsuario;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.util.Encrypt;

public class UsuarioBuilder {

	private String nome;
	private String cpf;
	private Genero sexo;
	private LocalDate dataNascimento;
	private EstadoCivil estadoCivil;
	private List<TelefoneUsuario> telefones;
	private EnderecoUsuario endereco;
	private String email;
	private String senha;

	public UsuarioBuilder comNome(String nome) {
		this.nome = nome;
		return this;
	}

	public UsuarioBuilder comCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}

	public UsuarioBuilder comSexo(String sexo) {
		this.sexo = Genero.valueOf(sexo);
		return this;
	}

	public UsuarioBuilder comDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
		return this;
	}

	public UsuarioBuilder comEstadoCivil(String estadoCivil) {
		this.estadoCivil = EstadoCivil.valueOf(estadoCivil);
		return this;
	}

	public UsuarioBuilder comTelefones(List<String> telefones) {
		this.telefones = telefones.stream().map(x -> new TelefoneUsuario(x)).collect(Collectors.toList());
		return this;
	}

	public UsuarioBuilder comEndereco(EnderecoUsuario endereco) {
		this.endereco = endereco;
		return this;
	}

	public UsuarioBuilder comEmail(String email) {
		this.email = email;
		return this;
	}

	public UsuarioBuilder comSenha(String senha) {
		this.senha = Encrypt.encode(senha);
		return this;

	}

	public Usuario gera() {
		return new Usuario(nome, cpf, sexo, dataNascimento, estadoCivil, telefones, endereco, email, senha);
	}
}
