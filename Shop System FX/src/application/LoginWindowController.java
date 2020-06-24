package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
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
	//Ausf�hrung des Register-Buttons
	@FXML
	public void registerButtonClick(ActionEvent event) {
		setLoginData();
		sqlDatabase.setLoginData(user, passw.hashCode());
	}
	//Ausf�hrung des Login-Buttons
	@FXML
	public void loginButtonClick(ActionEvent event) throws IOException {
		setLoginData();
		boolean nextWindow = checkLoginData(user, passw.hashCode());
		if (nextWindow == true) {
			AnchorPane shopPane = FXMLLoader.load(getClass().getResource("ShopWindow.fxml"));
			loginPane.getChildren().setAll(shopPane);
		} else {
			Alert wrongLoginData = new Alert(AlertType.WARNING);
			wrongLoginData.setTitle("Loginfehler");
			wrongLoginData.setHeaderText(null);
			String error = "Benutzername oder Passwort ist falsch!";
			wrongLoginData.setContentText(error);
			wrongLoginData.showAndWait();
		}
	}
	//Speichern der Login-Daten
	private void setLoginData() {
		user = username.getText();
		passw = password.getText() + "7453";
	}
	//Pr�fen ob Passwort und Username �bereinstimmen
	private boolean checkLoginData(String user, int password) {
		boolean correctPassword = false;
		int loginData = sqlDatabase.getPasswordFromUser(user);
		if (loginData == password) {
			correctPassword = true;
		}
		return correctPassword;

	}

}
