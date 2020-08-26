package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ShopWindowController implements Initializable {

	@FXML
	private AnchorPane shopPane;
	@FXML
	private TextField productIdShoppingCart;
	@FXML
	private ComboBox<String> sizeCombobox;
	@FXML
	private ComboBox<String> categoryCombobox;
	
	public ArrayList<WarenkorbElement> shoppingCartList = new ArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void logoutButtonClick(ActionEvent event) throws IOException {
		AnchorPane loginPane = FXMLLoader.load(getClass().getResource("LoginWindowTest.fxml"));
		shopPane.getChildren().setAll(loginPane);
	}
	
	public void addToShoppingCartButtonClick(ActionEvent event) throws IOException {
		WarenkorbElement element = new WarenkorbElement(Integer.parseInt(productIdShoppingCart.getText()), sizeCombobox.getValue());
		shoppingCartList.add(element);
	}
	
	public void searchButtonClick(ActionEvent event) throws IOException {
		String category;
		SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
		category = categoryCombobox.getValue();
		ArrayList<Produkt> productList = new ArrayList();
		productList = sqlDatabase.getProductIDNameandPrice(category);
		for(int i = 0; i<productList.size(); i++) {
			Produkt product;
			product = productList.get(i);
			int id = product.getId();
			String productName = product.getProductName();
			int price = product.getPrice();
		}
	}
}
