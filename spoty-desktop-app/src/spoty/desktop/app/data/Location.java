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
public class Location {
    private int idLocation;
    private String locationname;
    private int idType;
    private int idAddress;

    public Location(int idLocation, String locationname, int idType, int idAddress) {
        this.idLocation = idLocation;
        this.locationname = locationname;
        this.idType = idType;
        this.idAddress = idAddress;
    }
    
    

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public String toString() {
        return locationname;
    }
    
    
}
