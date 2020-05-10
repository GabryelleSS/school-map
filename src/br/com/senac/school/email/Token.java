package br.com.senac.school.email;

import java.util.Random;

public class Token {

	private static final int MAX = 10000;

	public static int generate() {
		return new Random().nextInt(MAX);
	}
}
