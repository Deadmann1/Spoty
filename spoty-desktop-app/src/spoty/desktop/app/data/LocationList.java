/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.data;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lampr
 */
//@XmlRootElement (name="Locations")
@XmlRootElement (name = "locations")
public class LocationList {
    @XmlElement
    private ArrayList<Location> locList = new ArrayList<Location>();

    public LocationList(ArrayList<Location> locList) {
        this.locList = locList;
    }

    public LocationList() {
    }
    
    public ArrayList<Location> getLocList() {
        return locList;
    }

    public void setLocList(ArrayList<Location> locList) {
        this.locList = locList;
    }
    
    
}
