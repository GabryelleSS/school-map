package br.com.senac.school.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Escola {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	private String nome;
	private Boolean situacao;

	@ManyToOne
	private Distrito distrito;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Escola_Telefone", joinColumns = { @JoinColumn(name = "escola_id") }, inverseJoinColumns = {
			@JoinColumn(name = "telefone_id") })
	private List<TelefoneEscola> telefone = new ArrayList<>();

	@OneToOne
	private EnderecoEscola endereco;

	public Escola() {
	}

	public Escola(String tipo, String nome, boolean situacao, List<TelefoneEscola> telefones, EnderecoEscola endereco,
			Distrito distrito) {
		this.tipo = tipo;
		this.nome = nome;
		this.situacao = situacao;
		this.telefone = telefones;
		this.endereco = endereco;
		this.distrito = distrito;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

	public List<TelefoneEscola> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<TelefoneEscola> telefone) {
		this.telefone = telefone;
	}

	public EnderecoEscola getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoEscola endereco) {
		this.endereco = endereco;
	}

}
