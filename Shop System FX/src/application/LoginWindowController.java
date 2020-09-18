package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginWindowController implements Initializable {

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();

	// Views
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private AnchorPane loginPane;
	public Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	// Ausführung des Register-Buttons
	@FXML
	public void registerButtonClick(ActionEvent event) {
		sqlDatabase.setLoginData(getUsername(), getPassword());
	}

	// Ausführung des Login-Buttons
	@FXML
	public void loginButtonClick(ActionEvent event) throws IOException {
		boolean nextWindow = checkLoginData(getUsername(), getPassword());
		String admin = sqlDatabase.isAdmin(getUsername());
		if (nextWindow == true) {
			State.getInstance().setUser(getUsername());//Username wird in aktuellem Status gespeichert
			if (admin.equals("TRUE")) {//Es wird geprüft, ob der User Adminrechte besitzt und je nach Rang ein anderes Fenster geöffnet
				AnchorPane shopPane = FXMLLoader.load(getClass().getResource("ShopWindow2TestNeu.fxml"));
				loginPane.getChildren().setAll(shopPane);
			} else {
				AnchorPane shopPane = FXMLLoader.load(getClass().getResource("ShopWindowTestNeu.fxml"));
				loginPane.getChildren().setAll(shopPane);
			}
		} else {
			//Bei falschen Login Daten wird eine Meldung ausgegeben
			Alert wrongLoginData = new Alert(AlertType.WARNING);
			wrongLoginData.setTitle("Loginfehler");
			wrongLoginData.setHeaderText(null);
			String error = "Benutzername oder Passwort ist falsch!";
			wrongLoginData.setContentText(error);
			wrongLoginData.showAndWait();
		}
	}

	private String getUsername() {
		return username.getText();
	}

	private int getPassword() {
		return (password.getText() + "7453").hashCode();
	}

	// Prüfen ob Passwort und Username übereinstimmen
	private boolean checkLoginData(String user, int password) {
		boolean correctPassword = false;
		int loginData = sqlDatabase.getPasswordFromUser(user);
		if (loginData == password) {
			correctPassword = true;
		}
		return correctPassword;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
