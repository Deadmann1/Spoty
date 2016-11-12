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

public class Location {
    @SerializedName("IdLocation")
    private int IdLocation;
    @SerializedName("LocationName")
    private String Locationname;
    @SerializedName("IdLocationType")
    private int IdLocationType;
    @SerializedName("IdAddress")
    private int IdAddress;

    public Location(int IdLocation, String Locationname, int IdLocationType, int IdAddress) {
        this.IdLocation = IdLocation;
        this.Locationname = Locationname;
        this.IdLocationType = IdLocationType;
        this.IdAddress = IdAddress;
    }

    public Location() {
    }
    
    
    

    public int getIdLocation() {
        return IdLocation;
    }

    public void setIdLocation(int idLocation) {
        this.IdLocation = idLocation;
    }

    public String getLocationname() {
        return Locationname;
    }

    public void setLocationname(String locationname) {
        this.Locationname = locationname;
    }

    public int getIdType() {
        return IdLocationType;
    }

    public void setIdType(int idType) {
        this.IdLocationType = idType;
    }

    public int getIdAddress() {
        return IdAddress;
    }

    public void setIdAddress(int idAddress) {
        this.IdAddress = idAddress;
    }

    @Override
    public String toString() {
        return Locationname;
    }
    
    
}
