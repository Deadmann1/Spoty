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
public class Address {
    @SerializedName("IdAddress")
    private int idAddress;
    @SerializedName("IdCity")
    private int idCity;
    @SerializedName("StreetName")
    private String streetname;
    @SerializedName("HouseNumber")
    private int housenumber;

    public Address(int idAddress, int idCity, String streetname, int housenumber) {
        this.idAddress = idAddress;
        this.idCity = idCity;
        this.streetname = streetname;
        this.housenumber = housenumber;
    }

    
    
    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    @Override
    public String toString() {
        return "Address{" + "idAddress=" + idAddress + ", idCity=" + idCity + ", streetname=" + streetname + ", housenumber=" + housenumber + '}';
    }
    
    
}
