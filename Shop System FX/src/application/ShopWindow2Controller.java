package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/*
 * Controller für das ShopWindow der User mit Admin-Rechten.
 * Soll neben den Funktionen für den normalen User auch noch
 * weitere Funktionen aufweisen, die nur User mit Admin-Rechten nutzen können
 */
public class ShopWindow2Controller extends ShopWindowController {
	

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	
	
	@FXML
	private TextField productNameField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField categoryField;
	@FXML
	private TextField productIdShoppingCart2;
	@FXML
	private ComboBox<String> sizeCombobox2;
	public ArrayList<WarenkorbElement> shoppingCartList2 = new ArrayList();
	
	public void addProductButtonClick(ActionEvent event) throws IOException {
		String productname = productNameField.getText();
		int price = Integer.parseInt(priceField.getText());
		String category = categoryField.getText();
		sqlDatabase.addProducts( productname, price, category);
	}
	public void addToShoppingCartButtonClick2(ActionEvent event) throws IOException {
		WarenkorbElement element = new WarenkorbElement(Integer.parseInt(productIdShoppingCart2.getText()), sizeCombobox2.getValue());
		shoppingCartList2.add(element);
	}
}


