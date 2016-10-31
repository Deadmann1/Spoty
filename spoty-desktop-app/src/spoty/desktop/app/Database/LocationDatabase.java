/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

/**
 *
 * @author lampr
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LocationDatabase {
	private static LocationDatabase db = null;
	private String connectionString = "";
	private String username = "";
	private String password = "";

	private Connection conn = null;

	private LocationDatabase() {
	}

	public static LocationDatabase getInstance() {
		if (db == null) {
			db = new LocationDatabase();
			db.connectionString = ConnectionProperties.getInstance().getConnectionString();
			db.username = ConnectionProperties.getInstance().getUsername();
			db.password = ConnectionProperties.getInstance().getPassword();
		}
		return db;
	}

        /*
	private Connection createConnection() throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		return DriverManager.getConnection(connectionString, username, password);
	}*/
}
