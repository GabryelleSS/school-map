package br.com.senac.school.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Encrypt {

	private Encrypt() {

	}

	public static String encode(String password) {

		return BCrypt.withDefaults().hashToString(12, password.toCharArray());
	}

	public static boolean verify(String password, String HashPassword) {

		return BCrypt.verifyer().verify(password.getBytes(), HashPassword.getBytes()).verified;

	}
}
