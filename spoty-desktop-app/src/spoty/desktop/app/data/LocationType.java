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
public class LocationType {
    private int idType;
    private String locationtypename;

    public LocationType(int idType, String locationtypename) {
        this.idType = idType;
        this.locationtypename = locationtypename;
    }

    
    
    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getLocationtypename() {
        return locationtypename;
    }

    public void setLocationtypename(String locationtypename) {
        this.locationtypename = locationtypename;
    }

    @Override
    public String toString() {
        return "LocationType{" + "idType=" + idType + ", locationtypename=" + locationtypename + '}';
    }
    
    
}
