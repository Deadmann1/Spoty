/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.data;

import spoty.desktop.app.Database.AddressDatabase;
import spoty.desktop.app.Service.AddressService;

/**
 *
 * @author lampr
 */
public class Constants {
    private static Constants c = null;
    private String url = "https://spotyweb-backend.azurewebsites.net";//"http://spotyweb-backend.azurewebsites.net"
    
    private Constants() {
    }

    public static Constants getInstance() {    
        if (c == null)
        {
            c = new Constants();
        }
        
        return c;
    }

    public String getUrl() {
        return url;
    }
}
