package DataMod;
import java.io.IOException;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SQLConnector {
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/kevinkr_project_x";
	private static String username = "kevinkr_project_x";
	private static String password = "1234";
	private static Connection connection;
	
	// Metode for aa hente en SQL connection
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			try {
				System.out.println("Connecting database...");
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("Successfully connected to the database");
			}
			catch(SQLException e) {
				System.out.println("Could not connect to database");
				throw e;
			}
		}
		return connection;
	}
	
	// Metode for aa lukke en SQL connection
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Metode for aa hente ut resultatet fra en SQL spoerring
	public static ResultSet getResultSet(String query) throws SQLException {
		Connection connection;
		try {
			connection = SQLConnector.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch(SQLException e) {
			throw e;
		}
	}
	
	public static void createApparatus(String name, String desc) {
		Statement statement;
		try {
			Connection conn = SQLConnector.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(String.format("INSERT INTO Apparat VALUES('%s', '%s')", name, desc));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	
	public static void createSession(String date, String time, int form, int performance, String note, int duration) {
		Statement statement;
		try {
			Connection conn = SQLConnector.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(String.format("INSERT INTO Økt VALUES('%s','%s', '%d','%d','%s','%d')", date, time, form, performance, note, duration));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	
	public static void createNonAppExercise() {
		
	}
	
}

