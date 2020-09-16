package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;

import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;;

public class ShopWindowController implements Initializable {
	

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	
	@FXML
	private PasswordField passwordNew,passwordNewSafe;
	@FXML
	private Pane pnl_BE,pnl_WA,pnl_PR,pnl_Ph,pnl_PWaendern,pnl_front;
	@FXML
	private JFXButton btn_BE,btn_WA,btn_PR,btn_Ph,btn_PWaendern, btn_PWgeaendert;
	@FXML
	private AnchorPane shopPane;
	@FXML
	private TextField productIdShoppingCart;
	@FXML
	private ComboBox<String> sizeCombobox;
	@FXML
	private JFXComboBox<String> categoryCombobox;
	@FXML
	private JFXTreeTableView<Produkt> treeOrderView;
	
	//Warenkorb Array
	public ArrayList<WarenkorbElement> shoppingCartList = new ArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Kategorie Combobox befüllen
		categoryCombobox.getItems().add("T-Shirt");
		categoryCombobox.getItems().add("Pullover");
		categoryCombobox.getItems().add("Sonstiges");
		
		JFXTreeTableColumn<Produkt, String> jfxProductNameColumn = new JFXTreeTableColumn<>("Produktname");
		jfxProductNameColumn.setPrefWidth(110);
		jfxProductNameColumn.setResizable(false);
		jfxProductNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getProductName();
			}
		});
		
		JFXTreeTableColumn<Produkt, String> jfxProductPriceColumn = new JFXTreeTableColumn<>("Preis");
		jfxProductPriceColumn.setResizable(false);
		jfxProductPriceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getPrice();
			}
		});
		JFXTreeTableColumn<Produkt, String> jfxProductIDColumn = new JFXTreeTableColumn<>("Bestellnummer");
		jfxProductIDColumn.setPrefWidth(98);
		jfxProductIDColumn.setResizable(false);
		jfxProductIDColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getId();
			}
		});
		treeOrderView.getColumns().setAll(jfxProductNameColumn,jfxProductPriceColumn, jfxProductIDColumn);
		

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

		ObservableList<Produkt> productObservableList = sqlDatabase.getProductIDNameandPrice(category);
		final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(productObservableList,RecursiveTreeObject::getChildren);
		treeOrderView.setRoot(root);
		treeOrderView.setShowRoot(false);
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
		String user = State.getInstance().getUser();
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
			passwordNew.setText(null);
			passwordNewSafe.setText(null);
			pnl_PR.toFront();
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

