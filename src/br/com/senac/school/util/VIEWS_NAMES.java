package br.com.senac.school.util;

public enum VIEWS_NAMES {

	LOGIN("/br/com/senac/school/view/Login.fxml"),
	FORGOT_PASSWORD_RESET_TOKEN("/br/com/senac/school/view/ForgotPasswordResetToken.fxml"),
	FORGOT_PASSWORD_TOKEN("/br/com/senac/school/view/ForgotPasswordToken.fxml"),
	FORGOT_PASSWORD("/br/com/senac/school/view/ForgotPassword.fxml"),
	USER_REGISTER("/br/com/senac/school/view/UserRegister.fxml"),
	SEND_EMAIL("/br/com/senac/school/view/SendEmail.fxml"),
	HOME("/br/com/senac/school/view/Home.fxml"),
	MENU_HAMBURGUER("/br/com/senac/school/view/Menu.fxml"),
	SPLASH("/br/com/senac/school/view/Splash.fxml"),
	ITEM_SELECTED("/br/com/senac/school/view/itemDashboardSelected.fxml"),
	CONTACT_US("/br/com/senac/school/view/Contact-us.fxml"),
	CONTACT_US_MENU("/br/com/senac/school/view/Contact-us-menu.fxml"),
	
	EDIT_PROFILE("/br/com/senac/school/view/EditProfile.fxml"),
	EDIT_PROFILE_ENDERECO("/br/com/senac/school/view/EditProfileEndereco.fxml"),
	ITEM_DASHBOARD1("/br/com/senac/school/view/ItemDashboard1.fxml"),
	ITEM_DASHBOARD2("/br/com/senac/school/view/ItemDashboard2.fxml"),
	DASHBOARD("/br/com/senac/school/view/Dashboard.fxml"), 
	LOGS("/br/com/senac/school/view/Logs.fxml");
	private String name;

	VIEWS_NAMES(String string) {
		this.name = string;

	}

	public String getName() {
		return name;
	}

}
