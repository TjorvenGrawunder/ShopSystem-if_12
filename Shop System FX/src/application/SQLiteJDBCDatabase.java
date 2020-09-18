package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SQLiteJDBCDatabase {
	private final String databaseName;
	private Connection connection;
	private static SQLiteJDBCDatabase instance;

	public SQLiteJDBCDatabase(String databaseName) {
		this.databaseName = databaseName;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Verbindung zu bestehender Datenbank oder Erstellen einer neuen Datenbank
	private Connection getConnection() {
		if (connection == null) {

			try {
				connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	//Tabelle zum Speichern der Login-Daten erstellen
	public void createLoginTable() {

		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlCommand = "CREATE TABLE IF NOT EXISTS LOGIN " + "(USERNAME STRING PRIMARY KEY     NOT NULL,"
					+ " PASSWORD INT    NOT NULL, ADMIN STRING NOT NULL, VALUE INT)";
			statement.executeUpdate(sqlCommand);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Tabelle zum Speichern der Produkt-Daten erstellen
	public void createProductsTable() {

		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlCommand = "CREATE TABLE IF NOT EXISTS PRODUCTS " + "(PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " PRODUCTNAME STRING    NOT NULL, PRICE INT NOT NULL, CATEGORY STRING NOT NULL)";
			statement.executeUpdate(sqlCommand);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Daten in Tabellen schreiben
	
	//Speichern der Login-Daten im LoginTable
	public void setLoginData(String username, int passw) {
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlUserData = "INSERT INTO LOGIN (USERNAME,PASSWORD,ADMIN) VALUES('" + username + "', '" + passw + "','FALSE')";
			statement.execute(sqlUserData);
			statement.close();
		} catch (SQLException e) {
			Alert usernameTaken = new Alert(AlertType.WARNING);
			usernameTaken.setTitle("Benutzername bereits vergeben");
			usernameTaken.setHeaderText(null);
			String error = "Bitte wählen Sie einen anderen Benutzernamen!";
			usernameTaken.setContentText(error);
			usernameTaken.showAndWait();
		}
	}
	//Passwort des Users wird geändert
	public void changePassword(String username, int passw) {
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlUserData = "UPDATE LOGIN SET PASSWORD = "+ passw + " WHERE USERNAME = '"+ username +"'";
			statement.execute(sqlUserData);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Produkte werden zur Tabelle hinzugefügt
	public void addProducts(String productname, int price, String category) {
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlUserData = "INSERT INTO PRODUCTS (PRODUCTNAME,PRICE,CATEGORY) VALUES('" + productname + "', '" + price + "', '" + category +"')";
			statement.execute(sqlUserData);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//Daten aus Tabelle ziehen
	
	//Passwort des Users wird gesucht und zurückgegeben
	public int getPasswordFromUser(String user) {
		int password = 0;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PASSWORD FROM LOGIN WHERE USERNAME = '" + user + "'");
			resultSet.next();
			password = resultSet.getInt("password");
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;
	}
	
	//Es werden Eigenschaften von Produkten zu einer Kategorie gesucht und in als Produkte in einer Liste gespeichert und zurückgegeben
	public ObservableList<Produkt> getProductIDNameandPrice(String category) {
		ObservableList<Produkt> productList = FXCollections.observableArrayList();
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PRODUCTID, PRODUCTNAME, PRICE FROM PRODUCTS WHERE CATEGORY = '" + category + "'");
			while(resultSet.next()) {
				Produkt product = new Produkt();
				product.setId(new SimpleStringProperty(Integer.toString(resultSet.getInt(1))));
				product .setProductName(new SimpleStringProperty(resultSet.getString(2)));
				product.setPrice(new SimpleStringProperty(Integer.toString(resultSet.getInt(3))));
				productList.add(product);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
		
	}
	//Ein Benutzername wird darauf überprüft, ob er den Admin Rang besitzt, oder nicht
	public String isAdmin(String user) {
		String admin = "";
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT ADMIN FROM LOGIN WHERE USERNAME = '" + user + "'");
			resultSet.next();
			admin = resultSet.getString("ADMIN");
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}
	//ProduktIds werden gesucht und in einer ArrayList gespeichert
	public ArrayList<Integer> getProducts(String category) {
		ArrayList<Integer> productList = new ArrayList<Integer>();
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PRODUCTID FROM PRODUCTS WHERE CATEGORY = '" + category + "'");
			
			while(resultSet.next()) {
				productList.add(resultSet.getInt("productid"));
				
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productList;
	}
	//Aus der Tabelle werden Produktname und Preis zu einer bestimmten Id gesucht und in einem Produktobjekt gespeichert, welches zurückgegeben wird
	public Produkt getProductAndPrice(int id, String groeße) {
		Produkt elem = new Produkt();
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PRODUCTNAME, PRICE FROM PRODUCTS WHERE PRODUCTID = "+id);
			elem.setProductName(new SimpleStringProperty(resultSet.getString(1)));
			elem.setPrice(new SimpleStringProperty(Integer.toString(resultSet.getInt(2))));
			elem.setGroeße(new SimpleStringProperty(groeße));
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elem;
	}
	//Der Kontostand eines Users wird ausgegeben
	public int getCreditValue(String user) {
		int creditValue = 0;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT VALUE FROM LOGIN WHERE USERNAME = '"+ user + "'");
			creditValue = resultSet.getInt(1);
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return creditValue;
	}
	//Der Kontostand eines Users wird um einen Transfer korriegiert(negativer Transfer bei eine Abbuchung und positiver Transfer bei einer Einzahlung)
	public void changeCreditValue(String user, int creditTransfer) {
		Statement statement = null;
		int newCreditValue;
		try {
			newCreditValue = getCreditValue(user) + creditTransfer;
			statement = getConnection().createStatement();
			String update = "UPDATE LOGIN SET VALUE = " + newCreditValue + " WHERE USERNAME = '"+ user + "'";
			statement.execute(update);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Instanz der aktuellen Datenbank wird erstellt
	public static synchronized SQLiteJDBCDatabase getInstance() {
		if (SQLiteJDBCDatabase.instance == null) {
			SQLiteJDBCDatabase.instance = new SQLiteJDBCDatabase("ShopSystem");
		}
		return SQLiteJDBCDatabase.instance;
	}
	//Instanz der aktuellen Datenbank wird zurückgegeben
	public String getDatabaseName() {
		return databaseName;
	}
	
}
