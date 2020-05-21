package br.com.senac.school.builder;

import java.time.LocalDate;

import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Usuario;
import br.com.senac.school.util.Encrypt;

public class UsuarioBuilder {

	private String nome;
	private String cpf;
	private String sexo;
	private LocalDate dataNascimento;
	private String estadoCivil;
	private String telefone;
	private String celular;
	private EnderecoUsuario endereco;
	private String email;
	private String senha;
	private String preferenciaContato;

	public UsuarioBuilder comPreferenciaContato(String preferenciaContato) {
		this.preferenciaContato = preferenciaContato;
		return this;
	}

	public UsuarioBuilder comNome(String nome) {
		this.nome = nome;
		return this;
	}

	public UsuarioBuilder comCpf(String cpf) {
		this.cpf = cpf;
		return this;
	}

	public UsuarioBuilder comSexo(String sexo) {
		this.sexo = sexo;
		return this;
	}

	public UsuarioBuilder comDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
		return this;
	}

	public UsuarioBuilder comEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
		return this;
	}

	public UsuarioBuilder comCelular(String celular) {
		this.celular = celular;
		return this;
	}

	public UsuarioBuilder comTelefone(String telefone) {
		this.telefone = telefone;
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
		return new Usuario(nome, cpf, sexo, dataNascimento, estadoCivil, telefone, celular, endereco, email, senha,preferenciaContato);
	}
}
