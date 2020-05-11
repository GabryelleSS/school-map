package br.com.senac.school.util;

public enum VIEWS_NAMES {

	LOGIN("/br/com/senac/school/view/Login.fxml"),
	FORGOT_PASSWORD_RESET_TOKEN("/br/com/senac/school/view/ForgotPasswordResetToken.fxml"),
	FORGOT_PASSWORD_TOKEN("/br/com/senac/school/view/ForgotPasswordToken.fxml"),
	FORGOT_PASSWORD("/br/com/senac/school/view/ForgotPassword.fxml");

	private String name;

	VIEWS_NAMES(String string) {
		this.name = string;

	}

	public String getName() {
		return name;
	}

}
