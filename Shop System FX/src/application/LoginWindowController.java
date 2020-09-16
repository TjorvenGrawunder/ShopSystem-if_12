package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class LoginWindowController implements Initializable {

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	//Instanz
	private static LoginWindowController instance;
	// Views
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private AnchorPane loginPane;
	private String user;
	private String passw;
	public Main main;

	public void setMain(Main main) {
		this.main = main;
	}
	//Ausführung des Register-Buttons
	@FXML
	public void registerButtonClick(ActionEvent event) {
		setLoginData();
		sqlDatabase.setLoginData(user, passw.hashCode());
	}
	//Ausführung des Login-Buttons
	@FXML
	public void loginButtonClick(ActionEvent event) throws IOException {
		setLoginData();
		boolean nextWindow = checkLoginData(user, passw.hashCode());
		String admin = sqlDatabase.isAdmin(user);
		if (nextWindow == true) {
			if(admin.equals("TRUE")) {
			AnchorPane shopPane = FXMLLoader.load(getClass().getResource("ShopWindow2TestNeu.fxml"));
			loginPane.getChildren().setAll(shopPane);
			}
			else{
				AnchorPane shopPane = FXMLLoader.load(getClass().getResource("ShopWindowTestNeu.fxml"));
				loginPane.getChildren().setAll(shopPane);
			}
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
	//Prüfen ob Passwort und Username übereinstimmen
	private boolean checkLoginData(String user, int password) {
		boolean correctPassword = false;
		int loginData = sqlDatabase.getPasswordFromUser(user);
		if (loginData == password) {
			correctPassword = true;
		}
		return correctPassword;

	}
	public static LoginWindowController getInstance() {
		return instance; 
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public String getUser() {
		return user;
	}
	
}
