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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import spoty.desktop.app.Database.UserAccountDatabase;
import spoty.desktop.app.data.UserAccount;
import spoty.desktop.app.data.Constants;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.helper.LoginHelper;
import spoty.desktop.app.helper.TokenHelper;
import spoty.desktop.app.helper.UserAccountHelper;

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
    
    public TokenHelper getAuthenticationToken (LoginHelper userAccount)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/authenticate").build());

        
        Gson gson = new Gson();
        String user = gson.toJson(userAccount, LoginHelper.class);
        
        String response = null;
        
        try
        {
            response = service.header("Content-Type", "application/json").post(String.class, user);
        
            System.out.println("Response: " + response);
        }
        catch(Exception e)
        {
            
        }

        if (response!=null)
            return gson.fromJson(response, TokenHelper.class); 
        
        else
            return null;
    }
    
    public Vector<UserAccount> getUserAccounts()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users").build());
        
        //.setProperty("x-access-token", UserAccountDatabase.getInstance().token.getToken());
        System.out.println("Token: " + UserAccountDatabase.getInstance().token.getToken());
        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        Gson gson = new Gson();
        
        return gson.fromJson(s, new TypeToken<Vector<UserAccount>>(){}.getType()); 
    }
    
    public UserAccount getUserAccount(int idUserAccount)
    {   
        System.out.println("in getUserAccount(id)");
        
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users/"+idUserAccount).build());

        System.out.println("Token: " + UserAccountDatabase.getInstance().token.getToken());
        String s = "";
        try{
            s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println("danach");
        Gson gson = new Gson();
        return gson.fromJson(s, UserAccount.class); 
    }
    
    public void deleteUserAccount(UserAccount deleteUserAccount)
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users/"+deleteUserAccount.getIdUserAccount()).build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).delete(String.class, gson.toJson(deleteUserAccount, UserAccount.class));
    }
    
    //TODO
    public UserAccount getNewUserAccountID()
    {
         Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/newUserId").build());

        String s = service.accept(MediaType.APPLICATION_JSON).header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).get(String.class);
        
        Gson gson = new Gson();
        return gson.fromJson(s, UserAccount.class);  
    }

    
    public void postUserAccount(UserAccountHelper newUserAccount) {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users").build());

        
        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).post(String.class, gson.toJson(newUserAccount, UserAccountHelper.class));
    }
    
    
    public void putUserAccount(UserAccountHelper updateUserAccount) {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/users/"+updateUserAccount.getIdUserAccount()).build());

        Gson gson = new Gson();
        service.header("Content-Type", "application/json").header("x-access-token", UserAccountDatabase.getInstance().token.getToken()).put(String.class, gson.toJson(updateUserAccount, UserAccountHelper.class));
    }
}
