package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
					+ " PASSWORD INT    NOT NULL, ADMIN STRING NOT NULL )";
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
			e.printStackTrace();
		}
	}
	
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
	
	public ObservableList<Produkt> getProductIDNameandPrice(String category) {
		ObservableList<Produkt> productList = FXCollections.observableArrayList();
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PRODUCTID, PRODUCTNAME, PRICE FROM PRODUCTS WHERE CATEGORY = '" + category + "'");
			while(resultSet.next()) {
				Produkt product = new Produkt();
				product.setId(resultSet.getInt(1));
				product .setProductName(resultSet.getString(2));
				product.setPrice(resultSet.getInt(3));
				productList.add(product);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
		
	}
	
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
	

	public static synchronized SQLiteJDBCDatabase getInstance() {
		if (SQLiteJDBCDatabase.instance == null) {
			SQLiteJDBCDatabase.instance = new SQLiteJDBCDatabase("ShopSystem");
		}
		return SQLiteJDBCDatabase.instance;
	}

	public String getDatabaseName() {
		return databaseName;
	}
}
