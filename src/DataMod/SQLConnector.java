package DataMod;

import java.io.IOException;
import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SQLConnector {
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/kevinkr_project_x?useSSL=false";
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
			statement.executeUpdate(String.format("INSERT INTO Apparat(`Navn`, `Beskrivelse`) VALUES('%s', '%s')", name, desc));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	
	public static void createSession(String date, String time, int form, int performance, String note, int duration) {
		Statement statement;
		try {
			Connection conn = SQLConnector.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(String.format("INSERT INTO Okt(`Dato`, `Tidspunkt`, `Personlig_form`, `Prestasjon`, `Notat`, `Varighet`) VALUES('%s','%s', '%d','%d','%s','%d')", date, time, form, performance, note, duration));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	
	public static int createExercise(String name, int id) throws SQLException{
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(String.format("INSERT INTO Okt VALUES('%s', '%d')", name, id));
		ResultSet genKey = statement.getGeneratedKeys();
		if (genKey.next()) {
        	return genKey.getInt(1);
        }
		throw new SQLException("wut");

	}
	public static void createNonAppExercise(String name, int id, String desc) {
		Statement statement;
		try {
			int ex_id = SQLConnector.createExercise(name, id);
			Connection conn = SQLConnector.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(String.format("INSERT INTO Okt VALUES('%d','%s')", ex_id, desc));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	
	public static void createAppExercise(String name, int id, int set, int weight, int apparatus) {
		Statement statement;
		try {
			int ex_id = SQLConnector.createExercise(name, id);
			Connection conn = SQLConnector.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(String.format("INSERT INTO Okt VALUES('%d','%d', '%d', '%d')", ex_id, set, weight, apparatus));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	public static void main(String [] args) {
		//SQLConnector.createApparatus("Bench", "BENCH THAT SHIT");
		SQLConnector.createSession("2018-03-12", "20:00", 5, 4, "HEI", 10);
	}
	
	public static void getSessions(int antall) throws SQLException {
		ResultSet rs = getResultSet(String.format("SELECT * FROM Okt ORDER BY OktID DESC LIMIT %d", antall));
		while (rs.next()) {
			System.out.println("ID    Dato    Tidspunkt   Varighet PF P Notat");
			System.out.println(String.format("%d %s %s %d %d %d %s", rs.getInt("OktID"),
			rs.getString("Dato"),
			rs.getString("Tidspunkt"),
			rs.getInt("Varighet"),
			rs.getInt("Personlig_form"),
			rs.getInt("Prestasjon"),
			rs.getString("Notat")));	
		}
	}
	
	public static void getSessions() throws SQLException {
		ResultSet rs = getResultSet("SELECT OktID, Dato, Tidspunkt FROM Okt");
		System.out.println("ID    Dato    Tidspunkt");
		while (rs.next()) {
			System.out.println(String.format("%d %s %s", rs.getInt("OktID"),
			rs.getString("Dato"),
			rs.getString("Tidspunkt")));
		}
	}
	
	public static void getApparatus() throws SQLException {
		ResultSet rs = getResultSet("SELECT ApparatID, Navn FROM Apparat");
		System.out.println("ID    Navn");
		while (rs.next()) {
			System.out.println(String.format("%d %s", rs.getInt("ApparatID"),
					rs.getString("Navn")));
		}
	}
	
	public static void getAppExercise(String navn, String start, String slutt) throws SQLException {
		ResultSet rs = getResultSet(String.format("SELECT Ovelse.OvelseNavn, Okt.OktID, Okt.Dato, Okt.Tidspunkt, AppOvelse.Sett, AppOvelse.Vekt, Apparat.Navn FROM ((Okt JOIN Ovelse ON Okt.OktID = Ovelse.OktID) JOIN AppOvelse ON Ovelse.OvelseID = AppOvelse.OvelseID) JOIN Apparat ON AppOvelse.ApparatID = Apparat.ApparatID WHERE Ovelse.OvelseNavn = '%s' AND (Okt.Dato >= '%s' AND Okt.Dato <= '%s')", navn, java.sql.Date.valueOf(start), java.sql.Date.valueOf(slutt)));
		System.out.println(navn + ":");
		System.out.println("Økt ID    Dato   Tidspunkt   Sett   Vekt   Apparat");
		while (rs.next()) {
			System.out.println(String.format("%d %s %s %d %d %s", rs.getInt("OktID"),
					rs.getString("Dato"),
					rs.getString("Tidspunkt"),
					rs.getInt("Sett"),
					rs.getInt("Vekt"),
					rs.getString("Navn")));
		}
	}
	
	public static void getNonAppExercise(String navn, String start, String slutt) throws SQLException {
		ResultSet rs = getResultSet(String.format("SELECT Ovelse.OvelseNavn, Okt.OktID, Okt.Dato, Okt.Tidspunkt, UtenAppOvelse.Beskrivelse FROM ((Okt JOIN Ovelse ON Okt.OktID = Ovelse.OktID) JOIN UtenAppOvelse ON Ovelse.OvelseID = UtenAppOvelse.OvelseID WHERE Ovelse.OvelseNavn = '%s' AND (Okt.Dato >= '%s' AND Okt.Dato <= '%s')", navn, java.sql.Date.valueOf(start), java.sql.Date.valueOf(slutt)));
		System.out.println(navn + ":");
		System.out.println("Økt ID    Dato   Tidspunkt   Beskrivelse");
		while (rs.next()) {
			System.out.println(String.format("%d %s %s %d %d %s", rs.getInt("OktID"),
					rs.getString("Dato"),
					rs.getString("Tidspunkt"),
					rs.getString("Beskrivelse")));
		}
	}
	
	
	
	
	
}

