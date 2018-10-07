package datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatenbankVerbindung {	
	public static String host;
	public static String port;
	public static String database;
	public static String username;
	public static String password;
	public static Connection con;
	
	public static void connect() {
		if(!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				System.out.println("[BuildObjects DatenbankVerbindung] Verbindung aufgebaut!");
			} catch (SQLException e) {	
				e.printStackTrace();
			}
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				System.out.println("[BuildObjects DatenbankVerbindung] Verbindung geschlossen!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static Connection getConnection() {
		return con;
	}
}
