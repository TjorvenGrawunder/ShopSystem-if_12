package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainWindowController {
	
	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();

	//Views
	@FXML private TextField username;
	@FXML private TextField password;
	private String user;
	public Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void registerButtonClick(ActionEvent  event) {
		user = username.getText();
		String passw = password.getText();
		sqlDatabase.setLoginData(user, passw);
	}
	
	@FXML
	public void loginButtonClick(ActionEvent  event) {
		String loginData = sqlDatabase.getPasswordFromUser(user);
		System.out.println(loginData);
	}
	

}
