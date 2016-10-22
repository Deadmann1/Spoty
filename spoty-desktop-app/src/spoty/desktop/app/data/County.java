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
public class County {
    private int idCounty;
    private String countyname;
    private int idCountry;

    public County(int idCounty, String countyname, int idCountry) {
        this.idCounty = idCounty;
        this.countyname = countyname;
        this.idCountry = idCountry;
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
        return "County{" + "idCounty=" + idCounty + ", countyname=" + countyname + ", idCountry=" + idCountry + '}';
    }
    
    
}
