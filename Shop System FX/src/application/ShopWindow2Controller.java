package application;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

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
	private JFXComboBox<String> sizeCombobox2;
	@FXML
	private JFXComboBox<String> categoryCombobox2;
	@FXML
	private JFXTreeTableView<Produkt> treeOrderView,treeWAview;
	
	public ObservableList<Produkt> shoppingCartList2 = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Kategorie-Combobox befüllen
		categoryCombobox2.getItems().add("T-Shirt");
		categoryCombobox2.getItems().add("Pullover");
		categoryCombobox2.getItems().add("Sonstiges");
		//Größen-Combobox befüllen
		sizeCombobox2.getItems().add("XS");
		sizeCombobox2.getItems().add("S");
		sizeCombobox2.getItems().add("M");
		sizeCombobox2.getItems().add("L");
		sizeCombobox2.getItems().add("XL");
		
		JFXTreeTableColumn<Produkt, String> jfxProductNameColumn = new JFXTreeTableColumn<>("Produktname");
		jfxProductNameColumn.setPrefWidth(110);
		jfxProductNameColumn.setResizable(false);
		jfxProductNameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Produkt,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Produkt, String> param) {
				
				return param.getValue().getValue().getProductName();
			}
		});
		
		JFXTreeTableColumn<Produkt, String> jfxProductPriceColumn = new JFXTreeTableColumn<>("Preis (in €)");
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
		JFXTreeTableColumn<Produkt, String> warenkorbPreisSpalte = new JFXTreeTableColumn<>("Preis");
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
	
	public void addProductButtonClick(ActionEvent event) throws IOException {
		String productname = productNameField.getText();
		int price = Integer.parseInt(priceField.getText());
		String category = categoryField.getText();
		sqlDatabase.addProducts( productname, price, category);
	}
	public void addToShoppingCartButtonClick2(ActionEvent event) throws IOException {
		int id = Integer.parseInt(productIdShoppingCart2.getText());
		String groeße = sizeCombobox2.getValue();
		if(groeße != null) {
			shoppingCartList2.add(sqlDatabase.getProductAndPrice(id, groeße));
			final TreeItem<Produkt> root = new RecursiveTreeItem<Produkt>(shoppingCartList2,RecursiveTreeObject::getChildren);
			treeWAview.setRoot(root);
			treeWAview.setShowRoot(false);
		}else {
			Alert noSize = new Alert(AlertType.WARNING);
			noSize.setTitle("Keine Größe ausgewählt");
			noSize.setHeaderText(null);
			String error = "Bitte wählen Sie eine Größe aus!";
			noSize.setContentText(error);
			noSize.showAndWait();
		}
	}
	
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