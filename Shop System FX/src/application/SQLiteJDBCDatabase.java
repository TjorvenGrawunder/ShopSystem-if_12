package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	//Tabellen erstellen
	public void createLoginTable() {

		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlCommand = "CREATE TABLE IF NOT EXISTS LOGIN " + "(USERNAME STRING PRIMARY KEY     NOT NULL,"
					+ " PASSWORD INT    NOT NULL )";
			statement.executeUpdate(sqlCommand);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createProductsTable() {

		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlCommand = "CREATE TABLE IF NOT EXISTS PRODUCTS " + "(PRODUCTID INT PRIMARY KEY   NOT NULL AUTO_INCREMENT,"
					+ " PRODUCT STRING    NOT NULL, PRICE INT NOT NULL )";
			statement.executeUpdate(sqlCommand);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setLoginData(String username, int passw) {
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlUserData = "INSERT INTO LOGIN (USERNAME,PASSWORD) VALUES('" + username + "', '" + passw + "')";
			statement.execute(sqlUserData);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
