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
import java.util.Vector;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;


public class LocationDatabase {
	private static LocationDatabase db = null;
	private String connectionString = "";
	private String username = "";
	private String password = "";

	private Connection conn = null;
        
        
        public static Vector<Location> vecLocations = new Vector<Location>();
        public static Vector<LocationType> vecLocationTypes = new Vector<LocationType>();
        
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
        
        
        public static void generateTestLocations()
        {
            LocationType locType1 = new LocationType(1, "Unterhaltung");
            LocationType locType2 = new LocationType(2, "Einkaufen");
            LocationType locType3 = new LocationType(3, "Restaurant");
            LocationType locType4 = new LocationType(4, "Tanken");
            
            Location location1 = new Location(1, "Cineplexx Villach", 1, 1);
            Location location2 = new Location(2, "China Restaurant (Zum gerösteten Hund)", 3, 2);
            Location location3 = new Location(3, "Eni Tankstelle", 4, 3);
            Location location4 = new Location(4, "Atrio", 2, 4);
            Location location5 = new Location(5, "H&M Kleidung" , 2, 5);
            
            vecLocations.add(location1);
            vecLocations.add(location2);
            vecLocations.add(location3);
            vecLocations.add(location4);
            vecLocations.add(location5);
            
            vecLocationTypes.add(locType1);
            vecLocationTypes.add(locType2);
            vecLocationTypes.add(locType3);
            vecLocationTypes.add(locType4);
        }
        
        public Vector<Location> getLocations()
        {
            return vecLocations;
        }
        
        public Vector<LocationType> getLocationTypes()
        {
            return vecLocationTypes;
        }
        
        public int getNewLocationID()
        {
            int highestID = -1;
            
            for (Location l : vecLocations)
            {
                if (l.getIdLocation()>highestID)
                    highestID = l.getIdLocation();
            }
            
            return highestID+1;
        }
        
        public int getNewLocationTypeID() {
            int highestID = -1;
            
            for (Location l : this.getLocations())
            {
                if (l.getIdLocation()>highestID)
                    highestID = l.getIdLocation();
            }
            
            return highestID+1;
        }
        
        public void deleteLocation(int idLocation)
        {
            int index = 0;
            int indexToDelete = -1;
            //bug
            for (Location location : this.vecLocations)
            {
                if (location.getIdLocation()==idLocation)
                {
                    indexToDelete = index;
                }
                
                index++;
            }
            
            if (indexToDelete != -1)
                this.getLocations().remove(indexToDelete);
        }
        
        public LocationType getLocationType(int idLocationType)
        {
            LocationType returnType = null;
            
            for (LocationType type : this.getLocationTypes())
            { 
                if (type.getIdType() == idLocationType)
                    returnType = type;
            }
            
            return returnType;
        }

         public void addLocationType(LocationType newType) {
             this.getLocationTypes().add(newType);
        }

    public void addLocation(Location newLocation) {
        this.getLocations().add(newLocation);
    }
   
}
