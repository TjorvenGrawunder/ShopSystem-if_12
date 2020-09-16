package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ShopWindowController implements Initializable {
	
	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	
	@FXML
	private PasswordField passwordNew,passwordNewSafe;
	@FXML
	private Pane pnl_BE,pnl_WA,pnl_PR,pnl_Ph,pnl_PWaendern;
	@FXML
	private JFXButton btn_BE,btn_WA,btn_PR,btn_Ph,btn_PWaendern, btn_PWgeaendert;
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
	//Durch Ancklicken der Knöpfe der Sidebar schiebt sich das jeweilige Pane nach vorne 
	public void sidebarButtonClick(ActionEvent event) {
		if(event.getSource() == btn_BE) {
			pnl_BE.toFront();
		}else {
			if(event.getSource() == btn_WA) {
				pnl_WA.toFront();
			}else {
				if(event.getSource() == btn_PR) {
					pnl_PR.toFront();
				}else {
					if(event.getSource() == btn_Ph) {
						pnl_Ph.toFront();
					}
				}
			}
		}
	}
	public void passwortAendernMenuButtonClick(ActionEvent event) {
			pnl_PWaendern.toFront();
		
	}
	public void passwortAendernButtonClick(ActionEvent event) {
		LoginWindowController loginWindow = LoginWindowController.getInstance();
		String user = loginWindow.getUser();
		String passwortNeu = passwordNew.getText();
		String passwortbestaetigt = passwordNewSafe.getText();
		if(passwortNeu.equals(passwortbestaetigt)) {
			passwortNeu = passwortNeu + "7453";
			sqlDatabase.changePassword(user, passwortNeu.hashCode());
			Alert passwordchanged = new Alert(AlertType.INFORMATION);
			passwordchanged.setTitle("Info");
			passwordchanged.setHeaderText(null);
			String info = "Passwort wurde erfolgreich geändert!";
			passwordchanged.setContentText(info);
			passwordchanged.showAndWait();
		}else {
			Alert wrongpassword = new Alert(AlertType.WARNING);
			wrongpassword.setTitle("");
			wrongpassword.setHeaderText(null);
			String error = "Passwörter stimmen nicht überein!";
			wrongpassword.setContentText(error);
			wrongpassword.showAndWait();
		}
		
	}
}

