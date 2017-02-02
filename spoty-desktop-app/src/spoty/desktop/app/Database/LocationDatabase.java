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


import java.util.Vector;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;
import spoty.desktop.app.Service.LocationService;
import spoty.desktop.app.data.UserAccount;


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
		}
		return db;
	}     
        
        public Vector<Location> getLocations()
        {
            return LocationService.getInstance().getLocations();
        }
        
        public Vector<LocationType> getLocationTypes()
        {
            return LocationService.getInstance().getLocationTypes();
        }
        
        public int getNewLocationID()
        {
            return LocationService.getInstance().getNewLocationID();
        }
        
        public int getNewLocationTypeID() {
            return LocationService.getInstance().getNewLocationTypeID();
        }
        
        public void deleteLocation(Location deleteLocation)
        {
            LocationService.getInstance().deleteLocation(deleteLocation);
        }
        
         public Location getLocation(int idLocation)
        {
            return LocationService.getInstance().getLocation(idLocation);
        }
         
        public LocationType getLocationType(int idLocationType)
        {
            return LocationService.getInstance().getLocationType(idLocationType);
        }

         public void addLocationType(LocationType newType) {
             LocationService.getInstance().postLocationType(newType);
        }

    public void addLocation(Location newLocation) {
        LocationService.getInstance().postLocation(newLocation);
    }
    
    public void updateLocation(Location updateLocation)
    {
        LocationService.getInstance().putLocation(updateLocation);
    }
   
}
