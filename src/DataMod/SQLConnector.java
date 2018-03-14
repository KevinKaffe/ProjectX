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
import java.util.concurrent.TimeUnit;


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
	
	public static void createExerciseGroupLink(int ex_id, int group_id) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(String.format("INSERT INTO OvGruppe(`GruppeID`, `OvelseID`) VALUES('%d', '%d')", group_id, ex_id));

	}
	
	public static int createExercise(String name, int session_id) throws SQLException{
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(String.format("INSERT INTO Ovelse(`OvelseNavn`, `OktID`) VALUES('%s', '%d')", name, session_id),Statement.RETURN_GENERATED_KEYS);
		ResultSet genKey = statement.getGeneratedKeys();
		if (genKey.next()) {
        	return genKey.getInt(1);
        }
		throw new SQLException("wut");
	}
	
	public static int createExerciseGroup(String name) throws SQLException {
		Connection conn = SQLConnector.getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(String.format("INSERT INTO Ovelsesgruppe(`GruppeNavn`) VALUES('%s')", name),Statement.RETURN_GENERATED_KEYS);
		ResultSet genKey = statement.getGeneratedKeys();
		if (genKey.next()) {
        	return genKey.getInt(1);
        }
		throw new SQLException("wut");
	}
	
	public static void createNonAppExercise(String name, int session_id, int group_id, String desc) {
		Statement statement;
		try {
			int ex_id = SQLConnector.createExercise(name, session_id);
			SQLConnector.createExerciseGroupLink(ex_id, group_id);
			Connection conn = SQLConnector.getConnection();
			statement = conn.createStatement();
			statement.executeUpdate(String.format("INSERT INTO UtenAppOvelse VALUES('%d','%s')", ex_id, desc));
		} catch (SQLException e) {
			System.out.println("Noe gikk galt :(");
		}
	}
	
	public static void createAppExercise(String name, int session_id, int group_id, int set, int weight, int apparatus) {
		Statement statement;
			try {
				int ex_id = SQLConnector.createExercise(name, session_id);
				Connection conn;
				conn = SQLConnector.getConnection();
				statement = conn.createStatement();
				statement.executeUpdate(String.format("INSERT INTO AppOvelse VALUES('%d','%d', '%d', '%d')", ex_id, set, weight, apparatus));
				System.out.println(ex_id);
				SQLConnector.createExerciseGroupLink(ex_id, session_id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("oops");
			}
}
	public static void main(String [] args) {
		//SQLConnector.createApparatus("Bench", "BENCH THAT SHIT");
			//System.out.println(SQLConnector.createExercise("KE", 1));
			 SQLConnector.createAppExercise("ME", 2, 1,1,1, 1);
			 try {
				System.out.println(SQLConnector.createExerciseGroup("Chest Exercises"));

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		while (rs.next()) {
			System.out.println("ID    Dato    Tidspunkt");
			System.out.println(String.format("%d %s %s", rs.getInt("OktID"),
			rs.getString("Dato"),
			rs.getString("Tidspunkt")));
		}
	}
	
	public static void getApparatus() throws SQLException {
		ResultSet rs = getResultSet("SELECT ApparatID, Navn FROM Apparat");
		while (rs.next()) {
			System.out.println("ID    Navn");
			System.out.println(String.format("%d %s", rs.getInt("ApparatID"),
					rs.getString("Navn")));
		}
	}
	
	
	
}

