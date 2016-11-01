/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import static spoty.desktop.app.Database.LocationDatabase.vecLocations;
import spoty.desktop.app.data.Address;
import spoty.desktop.app.data.City;
import spoty.desktop.app.data.Country;
import spoty.desktop.app.data.County;
import spoty.desktop.app.data.Location;


public class AddressDatabase {
	private static AddressDatabase db = null;
	private String connectionString = "";
	private String username = "";
	private String password = "";

	private Connection conn = null;
        
        public static Vector<Address> vecAddresses = new Vector<Address>();
        public static Vector<City> vecCities = new Vector<City>();
        public static Vector<County> vecCounties = new Vector<County>();
        public static Vector<Country> vecCountries = new Vector<Country>();
        

	private AddressDatabase() {
	}

	public static AddressDatabase getInstance() {
		if (db == null) {
			db = new AddressDatabase();
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
        
        public static void generateTestAddresses()
        {
            Address address1 = new Address(1, 1, "Big street", 11);
            Address address2 = new Address(2, 1, "Half street",43);
            Address address3 = new Address(3, 2, "Small street", 54);
            Address address4 = new Address(4, 2, "Small street", 23);
            Address address5 = new Address(5, 3, "Richman street", 4);
            
            City city1 = new City(1, 9620, "Villach", 1);
            City city2 = new City(2, 9630, "Hermagor", 1);
            City city3 = new City(3, 9700, "Spittal", 1);
            
            County county1 = new County(1, "Kärnten", 1);
            
            Country country1 = new Country(1, "Österreich");
            
            vecAddresses.add(address1);
            vecAddresses.add(address2);
            vecAddresses.add(address3);
            vecAddresses.add(address4);
            vecAddresses.add(address5);
            
            vecCities.add(city1);
            vecCities.add(city2);
            vecCities.add(city3);
            
            vecCounties.add(county1);
            vecCountries.add(country1);
        }
        
        public Vector<County> getCounties()
        {
            return vecCounties;
        }
        
        public Vector<City> getCities()
        {
            return vecCities;
        }
        
        public Vector<Country> getCountries()
        {
            return vecCountries;
        }

        public int getNewAddressID() {
            int highestID = -1;
            
            for (Address a : vecAddresses)
            {
                if (a.getIdAddress()>highestID)
                    highestID = a.getIdAddress();
            }
            
            return highestID+1;
        }
}

