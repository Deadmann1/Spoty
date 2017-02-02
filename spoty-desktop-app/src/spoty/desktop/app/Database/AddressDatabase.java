/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;

import java.util.Vector;

import spoty.desktop.app.Service.AddressService;

import spoty.desktop.app.data.Address;
import spoty.desktop.app.data.City;
import spoty.desktop.app.data.Country;
import spoty.desktop.app.data.County;



public class AddressDatabase {
	private static AddressDatabase db = null;
	private String connectionString = "";
	private String username = "";
	private String password = "";

	private Connection conn = null;     

	private AddressDatabase() {
	}

	public static AddressDatabase getInstance() {
		if (db == null) {
			db = new AddressDatabase();
		}
		return db;
	}
        
        public Vector<County> getCounties()
        {
            return AddressService.getInstance().getCounties();
        }
        
        public Vector<City> getCities()
        {
            return AddressService.getInstance().getCities();
        }
        
        public Vector<Country> getCountries()
        {
            return AddressService.getInstance().getCountries();
        }
        
        public Vector<Address> getAddresses()
        {
            return AddressService.getInstance().getAddresses();
        }
        
        public Address getAddress(int idAddress)
        {
            return AddressService.getInstance().getAddress(idAddress);
        }

        public int getNewAddressID() {
            return AddressService.getInstance().getNewAddressID();
        }
        
        public int getNewCountryID() {
            return AddressService.getInstance().getNewCountryID();
        }
        
        public int getNewCountyID() {
            return AddressService.getInstance().getNewCountyID();
        }
        
        public int getNewCityID() {
            return AddressService.getInstance().getNewCityID();
        }
        
        

    public City getCity(int idCity) {
        
        return AddressService.getInstance().getCity(idCity);
    }

    public County getCounty(int idCounty) {
        return AddressService.getInstance().getCounty(idCounty);
    }

    public Country getCountry(int idCountry) {
        return AddressService.getInstance().getCountry(idCountry);
    }
    
    public Vector<County> getCountiesByCountry(int idCountry)
    {
        Vector<County> returnCounties = new Vector<County>();
        
        for (County c : this.getCounties())
        {
            if (c.getIdCountry() == idCountry)
                returnCounties.add(c);
        }
        
        return returnCounties;
    }
    
    public Vector<City> getCitiesByCounty(int idCounty)
    {
        Vector<City> returnCities= new Vector<City>();
        
        for (City c : this.getCities())
        {
            if (c.getIdCounty() == idCounty)
                returnCities.add(c);
        }
        
        return returnCities;
    }
    
     public Vector<County> getCountriesByCounty(int idCountry)
    {
        Vector<County> returnCounties= new Vector<County>();
        
        for (County c : this.getCounties())
        {
            if (c.getIdCountry()== idCountry)
                returnCounties.add(c);
        }
        
        return returnCounties;
    }
    

    public void addCountry(Country newCountry) {
        AddressService.getInstance().postCountry(newCountry);
    }

    public void addCounty(County newCounty) {
        AddressService.getInstance().postCounty(newCounty);
    }

    public void addCity(City newCity) {
        AddressService.getInstance().postCity(newCity);
    }

    public void addAddress(Address newAddress) {
        AddressService.getInstance().postAddress(newAddress);
    }

    public void updateAddress(Address updateAddress)
    {
        AddressService.getInstance().putAddress(updateAddress);
    }

    
}

