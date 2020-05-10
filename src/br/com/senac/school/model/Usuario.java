package br.com.senac.school.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String cpf;

	@Enumerated(EnumType.STRING)
	private Genero sexo;

	private LocalDate dataNascimento;

	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Usuario_Telefone", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
			@JoinColumn(name = "telefone_id") })
	private List<TelefoneUsuario> telefones;

	@OneToOne(cascade = CascadeType.ALL)
	private EnderecoUsuario endereco;

	private String email;
	private String senha;

	private boolean ativo;

	public Usuario() {
	}

	public Usuario(String nome, String cpf, Genero sexo, LocalDate dataNascimento, EstadoCivil estadoCivil,
			List<TelefoneUsuario> telefones, EnderecoUsuario endereco, String email, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.estadoCivil = estadoCivil;
		this.telefones = telefones;
		this.endereco = endereco;
		this.email = email;
		this.senha = senha;
		this.ativo = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Genero getSexo() {
		return sexo;
	}

	public void setSexo(Genero sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public List<TelefoneUsuario> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneUsuario> telefones) {
		this.telefones = telefones;
	}

	public EnderecoUsuario getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoUsuario endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
