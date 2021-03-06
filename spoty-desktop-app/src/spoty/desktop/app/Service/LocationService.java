/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.lang.reflect.Array;
import java.util.Vector;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import spoty.desktop.app.Database.UserAccountDatabase;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;
import spoty.desktop.app.data.Constants;
import spoty.desktop.app.data.IdWrapper;

/**
 *
 * @author lampr
 */
public class LocationService {
    
    private static LocationService s = null;
    private static String url;
    
    private LocationService() {
        this.url = Constants.getInstance().getUrl();
    }

    public static LocationService getInstance() {
            if (s == null) {
                    s = new LocationService();
            }
            return s;
    }
    
    
    //Location
    public Vector<Location> getLocations()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locations").build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        Gson gson = new Gson();
        
        return gson.fromJson(s, new TypeToken<Vector<Location>>(){}.getType()); 
    }
    
    public Location getLocation(int idLocation)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locations/"+idLocation).build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, Location.class); 
    }
    
    public int getNewLocationID()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locations/new/id").build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        Gson gson = new Gson();
        IdWrapper idWrapperObject = gson.fromJson(s, IdWrapper.class); 
        
        return idWrapperObject.getId();
    }
    
    public void postLocation(Location newLocation)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locations").build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).post(String.class, gson.toJson(newLocation, Location.class));
      
    
    }
    
    public void putLocation (Location updateLocation)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locations/"+updateLocation.getIdLocation()).build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).put(String.class, gson.toJson(updateLocation, Location.class));
    }
    
    public void deleteLocation(Location deleteLocation)
    {
       Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locations/"+deleteLocation.getIdLocation()).build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).delete(String.class, gson.toJson(deleteLocation, Location.class));
    }
    
    
    
    //LocationType
    public Vector<LocationType> getLocationTypes()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locationTypes").build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, new TypeToken<Vector<LocationType>>(){}.getType()); 
    }
    
    public LocationType getLocationType(int idLocationType)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locationTypes/"+idLocationType).build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, LocationType.class); 
    }
    
    public int getNewLocationTypeID() {
       Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locationTypes/new/id").build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        Gson gson = new Gson();
        IdWrapper idWrapperObject = gson.fromJson(s, IdWrapper.class); 
        
        return idWrapperObject.getId();
    }
    
    public void postLocationType(LocationType newLocationType)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/locationtypes").build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).post(String.class, gson.toJson(newLocationType, LocationType.class));
    }

    
}
