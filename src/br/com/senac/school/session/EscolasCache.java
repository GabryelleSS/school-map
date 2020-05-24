package br.com.senac.school.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.senac.school.model.Escola;

public class EscolasCache {

	public static Map<Integer, Escola> escolas;

	public static Map<Integer, Escola> favoritos;

	static {
		escolas = new LinkedHashMap<Integer, Escola>();
		favoritos = new LinkedHashMap<Integer, Escola>();
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

	public static void favorite(Integer id, Escola escola) {
		favoritos.put(id, escola);
	}

	public static void favorite(List<Escola> escolas) {
		escolas.forEach(x -> favoritos.put(x.getId(), x));
	}

	public static boolean isFavorited(Integer id) {
		return favoritos.containsKey(id);
	}

	public static void deslike(Integer id) {
		favoritos.remove(id);
	}
	
	public static List<Escola> favorites() {
		return new ArrayList<Escola>(favoritos.values());
	}

}
