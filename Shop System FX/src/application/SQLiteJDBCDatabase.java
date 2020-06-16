package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBCDatabase {
	private final String databaseName;
	private Connection connection;

	public SQLiteJDBCDatabase(String databaseName) {
		this.databaseName = databaseName;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

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

	public void createLoginTable(String nameTable) {

		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlCommand = "CREATE TABLE LOGIN " + "(USERNAME STRING PRIMARY KEY     NOT NULL,"
					+ " PASSWORD STRING    NOT NULL )";
			statement.executeUpdate(sqlCommand);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setLoginData(String username, String password) {
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			String sqlUserData = "INSERT INTO LOGIN (USERNAME,PASSWORD) VALUES('" + username+ "', '" + password + "')";
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getPasswordFromUser(String user) {
		String password = null;
		Statement statement = null;
		try {
			statement = getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT PASSWORD FROM LOGIN WHERE USERNAME = '" + user + "'");
			password = resultSet.getString("password");
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;
	}
}
