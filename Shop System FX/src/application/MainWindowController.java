package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainWindowController {
	
	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();

	//Views
	@FXML private TextField username;
	@FXML private PasswordField password;
	private String user;
	private String passw;
	public Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void registerButtonClick(ActionEvent  event) {
		setLoginData();
		sqlDatabase.setLoginData(user, passw);
	}
	
	@FXML
	public void loginButtonClick(ActionEvent  event) {
		setLoginData();
		String loginData = sqlDatabase.getPasswordFromUser(user);
		System.out.println(loginData);
	}
	
	private void setLoginData() {
		user = username.getText();
		passw = password.getText();
	}

}
