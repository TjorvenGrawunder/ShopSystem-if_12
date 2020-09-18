package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;;

public class ShopWindowController implements Initializable {
	

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	
	@FXML
	private PasswordField passwordNew,passwordNewSafe;
	@FXML
	private Label userLabel,creditValueLabel,creditValueLabel2,creditValueLabel3,totalPrice;
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
	private JFXTreeTableView<Produkt> treeOrderView, treeWAview;
	
	//Warenkorb Array
	public ObservableList<Produkt> shoppingCartList = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Kategorie Combobox befüllen
		categoryCombobox.getItems().add("T-Shirt");
		categoryCombobox.getItems().add("Pullover");
		categoryCombobox.getItems().add("Sonstiges");
		//Größen-Combobox befüllen
		sizeCombobox.getItems().add("XS");
		sizeCombobox.getItems().add("S");
		sizeCombobox.getItems().add("M");
		sizeCombobox.getItems().add("L");
		sizeCombobox.getItems().add("XL");
		userLabel.setText(State.getInstance().getUser());
		
		
		JFXTreeTableColumn<Produkt, String> jfxProductNameColumn = new JFXTreeTableColumn<>("Produktname");
		jfxProductNameColumn.setPrefWidth(110);
		jfxProductNameColumn.setResizable(false);
		jfxProductNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getProductName();
			}
		});
		
		JFXTreeTableColumn<Produkt, String> jfxProductPriceColumn = new JFXTreeTableColumn<>("Preis in €");
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
		
		JFXTreeTableColumn<Produkt, String> warenkorbProduktNameSpalte = new JFXTreeTableColumn<>("Produktname");
		warenkorbProduktNameSpalte.setPrefWidth(98);
		warenkorbProduktNameSpalte.setResizable(false);
		warenkorbProduktNameSpalte.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getProductName();
			}
		});
		JFXTreeTableColumn<Produkt, String> warenkorbGroeßeSpalte = new JFXTreeTableColumn<>("Größe");
		warenkorbGroeßeSpalte.setPrefWidth(98);
		warenkorbGroeßeSpalte.setResizable(false);
		warenkorbGroeßeSpalte.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getGroeße();
			}
		});
		JFXTreeTableColumn<Produkt, String> warenkorbPreisSpalte = new JFXTreeTableColumn<>("Preis in €");
		warenkorbPreisSpalte.setPrefWidth(98);
		warenkorbPreisSpalte.setResizable(false);
		warenkorbPreisSpalte.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getPrice();
			}
		});
		treeWAview.getColumns().setAll(warenkorbProduktNameSpalte,warenkorbGroeßeSpalte,warenkorbPreisSpalte);

	}
	
	public void logoutButtonClick(ActionEvent event) throws IOException {
		AnchorPane loginPane = FXMLLoader.load(getClass().getResource("LoginWindowTest.fxml"));
		shopPane.getChildren().setAll(loginPane);
	}
	
	public void addToShoppingCartButtonClick(ActionEvent event) throws IOException {
		int id = 0;
		String productIDTextField = productIdShoppingCart.getText();
		if(productIDTextField != null && !productIDTextField.isEmpty()) {
			id = Integer.parseInt(productIdShoppingCart.getText());
		}
		String groeße = sizeCombobox.getValue();
		if(groeße != null && !groeße.isEmpty() && id != 0) {
			Produkt produkt = new Produkt();
			produkt = sqlDatabase.getProductAndPrice(id, groeße);
			int price = Integer.parseInt(produkt.getPrice().get());
			shoppingCartList.add(produkt);
			final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(shoppingCartList,RecursiveTreeObject::getChildren);
			treeWAview.setRoot(root);
			treeWAview.setShowRoot(false);
			if(totalPrice.getText() != null && !totalPrice.getText().isEmpty()) {
				int currentPrice = Integer.parseInt(totalPrice.getText());
				int nextPrice = currentPrice + price;
				String priceString = Integer.toString(nextPrice);
				totalPrice.setText(priceString);
			}else {
				totalPrice.setText(Integer.toString(price));
			}
			Alert addedToShoppingcart = new Alert(AlertType.INFORMATION);
			addedToShoppingcart.setTitle("Info");
			addedToShoppingcart.setHeaderText(null);
			String info = "Ihre Auswahl wurde zum Warenkorb hinzugefügt!";
			addedToShoppingcart.setContentText(info);
			addedToShoppingcart.showAndWait();
		}else {
			Alert noSize = new Alert(AlertType.WARNING);
			noSize.setTitle("Keine Größe oder Bestellnummer ausgewählt");
			noSize.setHeaderText(null);
			String error = "Bitte wählen Sie eine Größe oder Bestellnummer aus!";
			noSize.setContentText(error);
			noSize.showAndWait();
		}
	}
	
	public void deleteShoppingCartListButtonClick(ActionEvent event) {
		shoppingCartList.clear();
		final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(shoppingCartList,RecursiveTreeObject::getChildren);
		treeWAview.setRoot(root);
		treeWAview.setShowRoot(false);
		Alert clearedShoppingcart = new Alert(AlertType.INFORMATION);
		clearedShoppingcart.setTitle("Info");
		clearedShoppingcart.setHeaderText(null);
		String info = "Erfolgreich den Warenkorb geleert!";
		clearedShoppingcart.setContentText(info);
		clearedShoppingcart.showAndWait();
	}
	
	public void buyButtonClick(ActionEvent event) {
		boolean isShoppingCartListEmpty = shoppingCartList.isEmpty();
		shoppingCartList.clear();
		final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(shoppingCartList,RecursiveTreeObject::getChildren);
		treeWAview.setRoot(root);
		treeWAview.setShowRoot(false);
		if(isShoppingCartListEmpty == true) {
			
			Alert clearedShoppingcart = new Alert(AlertType.INFORMATION);
			clearedShoppingcart.setTitle("Info");
			clearedShoppingcart.setHeaderText(null);
			String info = "Erfolgreich eingekauft! Vielen Dank für ihren Einkauf";
			clearedShoppingcart.setContentText(info);
			clearedShoppingcart.showAndWait();
		}else {
			Alert clearedShoppingcartempty = new Alert(AlertType.WARNING);
			clearedShoppingcartempty.setTitle("Leerer Warenkorb");
			clearedShoppingcartempty.setHeaderText(null);
			String info = "Einkauf Fehlgeschlagen! Der Warenkorb ist leer!";
			clearedShoppingcartempty.setContentText(info);
			clearedShoppingcartempty.showAndWait();
		}
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
			creditValueLabel.setText(Integer.toString(sqlDatabase.getCreditValue(State.getInstance().getUser())));
		}else {
			if(event.getSource() == btn_WA) {
				pnl_WA.toFront();
				creditValueLabel2.setText(Integer.toString(sqlDatabase.getCreditValue(State.getInstance().getUser())));
			}else {
				if(event.getSource() == btn_PR) {
					pnl_PR.toFront();
					creditValueLabel3.setText(Integer.toString(sqlDatabase.getCreditValue(State.getInstance().getUser())));
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
		if(passwortNeu.equals(passwortbestaetigt) && !passwortNeu.isEmpty()) {
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