package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class LoginWindowController {

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();

	// Views
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private AnchorPane loginPane;
	private String user;
	private String passw;
	public Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	public void registerButtonClick(ActionEvent event) {
		setLoginData();
		sqlDatabase.setLoginData(user, passw.hashCode());
	}

	@FXML
	public void loginButtonClick(ActionEvent event) throws IOException {
		setLoginData();
		boolean nextWindow = checkLoginData(user, passw.hashCode());
		if (nextWindow == true) {
			AnchorPane shopPane = FXMLLoader.load(getClass().getResource("ShopWindow.fxml"));
			loginPane.getChildren().setAll(shopPane);
		}
		else {
			Alert wrongLoginData = new Alert(AlertType.ERROR);
			wrongLoginData.setTitle("Loginfehler");
			wrongLoginData.setHeaderText(null);
			String error = "Benutzername oder Passwort ist falsch";
			wrongLoginData.setContentText(error);
			wrongLoginData.showAndWait();
		}
	}

	private void setLoginData() {
		user = username.getText();
		passw = password.getText();
	}

	private boolean checkLoginData(String user, int password) {
		boolean correctPassword = false;
		int loginData = sqlDatabase.getPasswordFromUser(user);
		if (loginData == password) {
			correctPassword = true;
		}
		return correctPassword;

	}

}
