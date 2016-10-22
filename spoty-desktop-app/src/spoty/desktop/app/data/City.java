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
public class City {
    private int idCity;
    private int postalcode;
    private String cityname;
    private int idCountry;

    public City(int idCity, int postalcode, String cityname, int idCountry) {
        this.idCity = idCity;
        this.postalcode = postalcode;
        this.cityname = cityname;
        this.idCountry = idCountry;
    }
    
    

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    @Override
    public String toString() {
        return "City{" + "idCity=" + idCity + ", postalcode=" + postalcode + ", cityname=" + cityname + ", idCountry=" + idCountry + '}';
    }
    
    
}
