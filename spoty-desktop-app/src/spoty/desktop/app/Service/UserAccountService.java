/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.Vector;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import spoty.desktop.app.data.UserAccount;
import spoty.desktop.app.data.Constants;

/**
 *
 * @author lampr
 */
public class UserAccountService {
     private static UserAccountService s = null;
    private static String url;
    
    private UserAccountService() {
        this.url = Constants.getInstance().getUrl();
    }

    public static UserAccountService getInstance() {
            if (s == null) {
                    s = new UserAccountService();
            }
            return s;
    }
    
    public Vector<UserAccount> getUserAccounts()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        Gson gson = new Gson();
        
        return gson.fromJson(s, new TypeToken<Vector<UserAccount>>(){}.getType()); 
    }
    
    public UserAccount getUserAccount(int idUserAccount)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users/"+idUserAccount).build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, UserAccount.class); 
    }
}
