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
import java.util.Vector;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import spoty.desktop.app.data.Address;
import spoty.desktop.app.data.City;
import spoty.desktop.app.data.Country;
import spoty.desktop.app.data.County;
import spoty.desktop.app.data.Constants;
import spoty.desktop.app.data.IdWrapper;

/**
 *
 * @author lampr
 */
public class AddressService {
    private static AddressService s = null;
    private static String url;
    
    private AddressService() {
        this.url = Constants.getInstance().getUrl();
    }

    public static AddressService getInstance() {
            if (s == null) {
                    s = new AddressService();
            }
            return s;
    }
    
    
    //-----------------Address---------------------
    public Vector<Address> getAddresses()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/addresses").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        
        return gson.fromJson(s, new TypeToken<Vector<Address>>(){}.getType()); 
    }
    
    public Address getAddress(int idAddress)
    {
        Client client = Client.create();
        
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/addresses/"+idAddress).build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        Gson gson = new Gson();
       
        return gson.fromJson(s, Address.class); 
    }

    public void postAddress(Address newAddress)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/addresses").build());

        Gson gson = new Gson();
        //service.accept(MediaType.APPLICATION_JSON).post(String.class, gson.toJson(newAddress, Address.class));
        service.header("Content-Type", "application/json").post(String.class, gson.toJson(newAddress, Address.class));
    }
    
    public void putAddress(Address updateAddress)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/addresses/"+updateAddress.getIdAddress()).build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").put(String.class, gson.toJson(updateAddress, Address.class));
    }
    
    
    
    
    //-----------------Country---------------------
    public Vector<Country> getCountries() {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/countries").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, new TypeToken<Vector<Country>>(){}.getType()); 
    }
    
    public Country getCountry(int idCountry)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/countries/"+idCountry).build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, Country.class); 
    }
    
    public void postCountry(Country newCountry)
    {
       Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/countries").build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").post(String.class, gson.toJson(newCountry, Country.class));
    }
    
    //-----------------County---------------------
     public Vector<County> getCounties() {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/counties").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, new TypeToken<Vector<County>>(){}.getType()); 
    }
     
      public County getCounty(int idCounty)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/counties/"+idCounty).build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, County.class); 
    }
      
    public void postCounty(County newCounty)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/counties").build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").post(String.class, gson.toJson(newCounty, County.class));
    }
      
    //-----------------City---------------------
    public Vector<City> getCities() {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/cities").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        
        Vector<City> vec = gson.fromJson(s, new TypeToken<Vector<City>>(){}.getType());
        return vec;
    }
    
    public City getCity(int idCity)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/cities/"+idCity).build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, City.class); 
    }
    
    public void postCity(City newCity)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/cities").build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").post(String.class, gson.toJson(newCity, City.class));
    }

    public int getNewAddressID() {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/addresses/new/id").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        Gson gson = new Gson();
        IdWrapper idWrapperObject = gson.fromJson(s, IdWrapper.class); 
        
        return idWrapperObject.getId();
    }

    public int getNewCountryID() {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/countries/new/id").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        Gson gson = new Gson();
        IdWrapper idWrapperObject = gson.fromJson(s, IdWrapper.class); 
        
        return idWrapperObject.getId();
    }

    public int getNewCountyID() {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/counties/new/id").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        Gson gson = new Gson();
        IdWrapper idWrapperObject = gson.fromJson(s, IdWrapper.class); 
        
        return idWrapperObject.getId();
    }

    public int getNewCityID() {
         Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/cities/new/id").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        Gson gson = new Gson();
        IdWrapper idWrapperObject = gson.fromJson(s, IdWrapper.class); 
        
        return idWrapperObject.getId();
    }
}
