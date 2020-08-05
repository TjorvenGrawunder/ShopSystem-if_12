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
	public ArrayList<WarenkorbElement> shoppingCartList = new ArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void logoutButtonClick(ActionEvent event) throws IOException {
		AnchorPane loginPane = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
		shopPane.getChildren().setAll(loginPane);
	}
	
	public void addToShoppingCartButtonClick(ActionEvent event) throws IOException {
		WarenkorbElement element = new WarenkorbElement(Integer.parseInt(productIdShoppingCart.getText()), sizeCombobox.getValue());
		shoppingCartList.add(element);
	}
}
