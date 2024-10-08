package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;

/*
 * Controller f�r das ShopWindow der User mit Admin-Rechten.
 * Soll neben den Funktionen f�r den normalen User auch noch
 * weitere Funktionen aufweisen, die nur User mit Admin-Rechten nutzen k�nnen
 */
public class ShopWindow2Controller extends ShopWindowController {
	

	SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
	
	
	@FXML
	private TextField productNameField,priceField,categoryField,productIdShoppingCart2;
	@FXML
	private Label userLabel,creditValueLabel,creditValueLabel2,creditValueLabel3,totalPrice;
	@FXML
	private JFXComboBox<String> sizeCombobox2;
	@FXML
	private JFXComboBox<String> categoryCombobox2;
	@FXML
	private JFXTreeTableView<Produkt> treeOrderView,treeWAview;
	
	public ObservableList<Produkt> shoppingCartList2 = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Kategorie-Combobox bef�llen
		categoryCombobox2.getItems().add("T-Shirt");
		categoryCombobox2.getItems().add("Pullover");
		categoryCombobox2.getItems().add("Sonstiges");
		//Gr��en-Combobox bef�llen
		sizeCombobox2.getItems().add("XS");
		sizeCombobox2.getItems().add("S");
		sizeCombobox2.getItems().add("M");
		sizeCombobox2.getItems().add("L");
		sizeCombobox2.getItems().add("XL");
		//Benutzer wird unter dem Unterreiter Profil angezeigt
		userLabel.setText(State.getInstance().getUser());
		//TreeTable Coloumns werden sowohl f�r die Bestellungen, als auch f�r den Warenkorb erstellt
		JFXTreeTableColumn<Produkt, String> jfxProductNameColumn = new JFXTreeTableColumn<>("Produktname");
		jfxProductNameColumn.setPrefWidth(110);
		jfxProductNameColumn.setResizable(false);
		jfxProductNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getProductName();
			}
		});
		
		JFXTreeTableColumn<Produkt, String> jfxProductPriceColumn = new JFXTreeTableColumn<>("Preis in �");
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
		JFXTreeTableColumn<Produkt, String> warenkorbGroe�eSpalte = new JFXTreeTableColumn<>("Gr��e");
		warenkorbGroe�eSpalte.setPrefWidth(98);
		warenkorbGroe�eSpalte.setResizable(false);
		warenkorbGroe�eSpalte.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getGroe�e();
			}
		});
		JFXTreeTableColumn<Produkt, String> warenkorbPreisSpalte = new JFXTreeTableColumn<>("Preis in �");
		warenkorbPreisSpalte.setPrefWidth(98);
		warenkorbPreisSpalte.setResizable(false);
		warenkorbPreisSpalte.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getPrice();
			}
		});
		treeWAview.getColumns().setAll(warenkorbProduktNameSpalte,warenkorbGroe�eSpalte,warenkorbPreisSpalte);
		
	}
	
	//neue Produkte k�nnen vom Admin zur Datenbank in die Produkt Tabelle hinzugef�gt werden. Name, Preis und Kategorie m�ssen zuvor angegeben werden
	public void addProductButtonClick(ActionEvent event) throws IOException {
		String productname = productNameField.getText();
		String category = categoryField.getText();
		if(priceField.getText() != null && !priceField.getText().isEmpty() && category != null && !category.isEmpty() && productname != null && !productname.isEmpty() ) {
			int price = Integer.parseInt(priceField.getText());
			sqlDatabase.addProducts( productname, price, category);
		}else {
			Alert noProduct = new Alert(AlertType.WARNING);
			noProduct.setTitle("Nicht ausreichend Produktdaten angegeben");
			noProduct.setHeaderText(null);
			String error = "Bitte geben Sie alle notwendigen Produktdaten an!";
			noProduct.setContentText(error);
			noProduct.showAndWait();
		}
	}
	//Produkte werden vom Admin aus zur Warenkorbliste hinzugef�gt und in der Warenkorbtabelle angezeigt. Hierf�r muss sowohl eine ID, als auch eine Gr��e angegeben werden
	public void addToShoppingCartButtonClick2(ActionEvent event) throws IOException {
		int id = 0;
		String productIDTextField = productIdShoppingCart2.getText();
		if(productIDTextField != null && !productIDTextField.isEmpty()) {
			id = Integer.parseInt(productIdShoppingCart2.getText());
		}
		String groe�e = sizeCombobox2.getValue();
		if(groe�e != null && !groe�e.isEmpty() && id != 0) {
			Produkt produkt = new Produkt();
			produkt = sqlDatabase.getProductAndPrice(id, groe�e);
			int price = Integer.parseInt(produkt.getPrice().get());
			shoppingCartList2.add(sqlDatabase.getProductAndPrice(id, groe�e));
			final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(shoppingCartList2,RecursiveTreeObject::getChildren);
			treeWAview.setRoot(root);
			treeWAview.setShowRoot(false);
			int currentPrice = Integer.parseInt(totalPrice.getText());
			int nextPrice = currentPrice + price;
			String priceString = Integer.toString(nextPrice);
			totalPrice.setText(priceString);
			Alert addedToShoppingcart = new Alert(AlertType.INFORMATION);
			addedToShoppingcart.setTitle("Info");
			addedToShoppingcart.setHeaderText(null);
			String info = "Ihre Auswahl wurde zum Warenkorb hinzugef�gt!";
			addedToShoppingcart.setContentText(info);
			addedToShoppingcart.showAndWait();
		}else {
			Alert noSize = new Alert(AlertType.WARNING);
			noSize.setTitle("Keine Gr��e oder Bestellnummer ausgew�hlt");
			noSize.setHeaderText(null);
			String error = "Bitte w�hlen Sie eine Gr��e oder Bestellnummer aus!";
			noSize.setContentText(error);
			noSize.showAndWait();
		}
	}
	
	//Der K�ufer best�tigt seinen Kauf. Kann nur durchgef�hrt werden, wenn der Warenkorb nicht leer ist und sich genug Geld auf dem Konto des K�ufers befindet
	//Der zu zahlende Betrag wird vom Konto des K�ufers abgezogen
	public void buyButtonClick(ActionEvent event) {
		boolean isShoppingCartListEmpty = shoppingCartList2.isEmpty();
		int price = Integer.parseInt(totalPrice.getText());
		shoppingCartList2.clear();
		final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(shoppingCartList2,RecursiveTreeObject::getChildren);
		treeWAview.setRoot(root);
		treeWAview.setShowRoot(false);
		if(isShoppingCartListEmpty == false && price <= sqlDatabase.getCreditValue(State.getInstance().getUser())) {
			int creditTransfer = price;
			sqlDatabase.changeCreditValue(State.getInstance().getUser(), -creditTransfer);
			creditValueLabel2.setText(Integer.toString(sqlDatabase.getCreditValue(State.getInstance().getUser())));
			totalPrice.setText("0");
			Alert clearedShoppingcart = new Alert(AlertType.INFORMATION);
			clearedShoppingcart.setTitle("Info");
			clearedShoppingcart.setHeaderText(null);
			String info = "Erfolgreich eingekauft! Vielen Dank f�r ihren Einkauf";
			clearedShoppingcart.setContentText(info);
			clearedShoppingcart.showAndWait();
		}else {
			Alert clearedShoppingcartempty = new Alert(AlertType.WARNING);
			clearedShoppingcartempty.setTitle("Leerer Warenkorb");
			clearedShoppingcartempty.setHeaderText(null);
			String info = "Einkauf Fehlgeschlagen! Der Warenkorb oder ihr Konto ist leer!";
			clearedShoppingcartempty.setContentText(info);
			clearedShoppingcartempty.showAndWait();
		}
	}
	//Der gew�hlte Kategoriefilter wird benutzt um die passenden Produkte aus der Datenbank zu suchen und in der Tabelle anzuzeigen
	public void searchButtonClick(ActionEvent event) throws IOException {
		String category;
		SQLiteJDBCDatabase sqlDatabase = SQLiteJDBCDatabase.getInstance();
		category = categoryCombobox2.getValue();

		ObservableList<Produkt> productObservableList = sqlDatabase.getProductIDNameandPrice(category);
		final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(productObservableList,RecursiveTreeObject::getChildren);
		treeOrderView.setRoot(root);
		treeOrderView.setShowRoot(false);
	}
	
}