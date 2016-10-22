/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.data;

/**
 *
 * @author lampr
 */
public class Country {
    private int idCountry;
    private String countryname;

    public Country(int idCountry, String countryname) {
        this.idCountry = idCountry;
        this.countryname = countryname;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    @Override
    public String toString() {
        return "Country{" + "idCountry=" + idCountry + ", countryname=" + countryname + '}';
    }
    
    
}
