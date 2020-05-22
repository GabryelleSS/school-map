package br.com.senac.school.session;

import br.com.senac.school.model.Usuario;

public class Session {

	private static Usuario usuario;
	
	public static String data;

	public static void setUsuario(Usuario usuario) {
		Session.usuario = usuario;
	}

	public static void removeUsuario() {
		usuario = null;
	}

	public static Usuario getUsuario() {
		return usuario;
	}

}
