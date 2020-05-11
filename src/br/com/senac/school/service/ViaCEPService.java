package br.com.senac.school.service;

import java.util.Optional;

import com.github.gilbertotorrezan.viacep.se.ViaCEPClient;
import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;

public class ViaCEPService {

	public static Optional<ViaCEPEndereco> consulta(String cep) {
		Optional<ViaCEPEndereco> endereco = Optional.ofNullable(null);
		try {
			endereco = Optional.ofNullable(new ViaCEPClient().getEndereco(cep));
		} catch (Exception e) {
		}
		return endereco;
	}
}
