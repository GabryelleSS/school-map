package br.com.senac.school.util;

public enum VIEWS_NAMES {

	LOGIN("/br/com/senac/school/view/Login.fxml"),
	FORGOT_PASSWORD_RESET_TOKEN("/br/com/senac/school/view/ForgotPasswordResetToken.fxml"),
	FORGOT_PASSWORD_TOKEN("/br/com/senac/school/view/ForgotPasswordToken.fxml"),
	FORGOT_PASSWORD("/br/com/senac/school/view/ForgotPassword.fxml"),
	USER_REGISTER("/br/com/senac/school/view/UserRegister.fxml"),
	SEND_EMAIL("/br/com/senac/school/view/SendEmail.fxml"),
	HOME("/br/com/senac/school/view/Home.fxml"),
	
	EDIT_PROFILE("/br/com/senac/school/view/EditProfile.fxml"),
	EDIT_PROFILE_ENDERECO("/br/com/senac/school/view/EditProfileEndereco.fxml"),
	DASHBOARD("/br/com/senac/school/view/Dashboard.fxml");
	private String name;

	VIEWS_NAMES(String string) {
		this.name = string;

	}

	public String getName() {
		return name;
	}

}
