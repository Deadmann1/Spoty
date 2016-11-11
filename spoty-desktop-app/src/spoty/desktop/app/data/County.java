/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.data;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author lampr
 */
public class County {
    @SerializedName("IdCounty")
    private int idCounty;
    @SerializedName("CountyName")
    private String countyname;
    @SerializedName("IdCountry")
    private int idCountry;

    public County(int idCounty, String countyname, int idCountry) {
        this.idCounty = idCounty;
        this.countyname = countyname;
        this.idCountry = idCountry;
    }

    public County(int idLocationType, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdCounty() {
        return idCounty;
    }

    public void setIdCounty(int idCounty) {
        this.idCounty = idCounty;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    @Override
    public String toString() {
        return countyname;
    }
    
    
}
