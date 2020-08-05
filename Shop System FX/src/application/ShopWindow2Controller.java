package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/*
 * Controller f�r das ShopWindow der User mit Admin-Rechten.
 * Soll neben den Funktionen f�r den normalen User auch noch
 * weitere Funktionen aufweisen, die nur User mit Admin-Rechten nutzen k�nnen
 */
public class ShopWindow2Controller extends ShopWindowController {
	
	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	
	@FXML
	private String productname;
	private int price;
	private String category;
	
	public void addProductButtonClick(ActionEvent event) throws IOException {
		sqlDatabase.createProductsTable();
		sqlDatabase.addProducts( productname, price, category);
	}
}
