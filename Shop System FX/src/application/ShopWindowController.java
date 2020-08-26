package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML
	private TableView<Produkt> produkte;
	@FXML
	private TableColumn<Produkt, String> colProduktname;
	@FXML
	private TableColumn<Produkt, Integer> colId;
	@FXML
	private TableColumn<Produkt, Integer> colPreis;
	
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
		ObservableList<Produkt> productList = FXCollections.observableArrayList();
		productList = sqlDatabase.getProductIDNameandPrice(category);
		colId.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("id"));
		colProduktname.setCellValueFactory(new PropertyValueFactory<Produkt, String>("productName"));
		colPreis.setCellValueFactory(new PropertyValueFactory<Produkt, Integer>("price"));
		produkte.setItems(productList);
	}
}
