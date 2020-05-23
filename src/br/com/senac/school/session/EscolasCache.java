package br.com.senac.school.session;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.senac.school.model.Escola;

public class EscolasCache {

	public static Map<Integer, Escola> escolas;

	static {
		escolas = new LinkedHashMap<Integer, Escola>();
	}

	public static void add(List<Escola> valores) {
		for (Escola escola : valores) {
			escolas.put(escola.getId(), escola);
		}
	}

	public static List<Escola> escolas() {
		return new ArrayList<Escola>(escolas.values());
	}

	public static Escola getEscola(int id) {
		return escolas.get(id);
	}

	public static void clean() {
		escolas.clear();
	}

}
