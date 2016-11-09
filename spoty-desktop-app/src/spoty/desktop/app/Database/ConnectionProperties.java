/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

public class ConnectionProperties {

	private static ConnectionProperties connProp = null;
	private String connectionString;
	private String username;
	private String password;

	private ConnectionProperties() {
		connectionString = "jdbc:oracle:thin:@10.0.0.20:1521:xe";
		username = "DB";
		password = "admin";
	}

	public static ConnectionProperties getInstance() {
		if (connProp == null) {
			connProp = new ConnectionProperties();
		}
		return connProp;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
