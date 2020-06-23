package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	SQLiteJDBCDatabase database = SQLiteJDBCDatabase.getInstance();
	
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		database.createLoginTable();
		database.createProductsTable();

		
		loginWindow();
	}
	
	public void loginWindow() {
		try {
			
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginWindow.fxml"));
			AnchorPane pane = loader.load();
			primaryStage.setMinHeight(400.00);
			primaryStage.setMinWidth(300.00);
			
			LoginWindowController loginWindowController = new LoginWindowController();
			loader.setController(loginWindowController);
			loginWindowController.setMain(this);
			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public static void main(String[] args) {
		launch(args);
	}

}
