package br.com.senac.school.factory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import br.com.senac.school.builder.EnderecoBuilder;
import br.com.senac.school.builder.UsuarioBuilder;
import br.com.senac.school.model.EnderecoUsuario;
import br.com.senac.school.model.Usuario;

public class UsuarioFactory {

	public static Usuario generate(String nome, String cpf, LocalDate dataNascimento, String telefone, String email,
			String senha, String celular, String estadoCivil, String genero, String rua, String cep, String numero,
			String bairro, String complemento, String cidade, String estado, String uf) {

		List<String> telefones = Arrays.asList(celular, telefone);

		EnderecoBuilder enderecoBuilder = new EnderecoBuilder();

		EnderecoUsuario endereco =
				enderecoBuilder
				.comBairro(bairro)
				.comCep(cep)
				.comComplemento(complemento)
				.comEndereco(rua)
				.comEstado(estado)
				.comNumero(numero)
				.comUf(uf)
				.comCidade(cidade)
				.gera();

		UsuarioBuilder usuarioBuilder = new UsuarioBuilder();

		Usuario usuario = 
				usuarioBuilder
				.comNome(nome)
				.comCpf(cpf)
				.comDataNascimento(dataNascimento)
				.comEmail(email)
				.comEstadoCivil(estadoCivil.toUpperCase())
				.comSenha(senha).comSexo(genero)
				.comTelefones(telefones)
				.comEndereco(endereco)
				.gera();

		return usuario;
	}

}
