/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.Vector;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.Rating;
import spoty.desktop.app.data.Constants;

/**
 *
 * @author lampr
 */
public class RatingService {
    private static RatingService s = null;
    private static String url;
    
    private RatingService() {
        this.url = Constants.getInstance().getUrl();
    }

    public static RatingService getInstance() {
            if (s == null) {
                    s = new RatingService();
            }
            return s;
    }
    
    public Vector<Rating> getRatings()
    {
        Client client = Client.create();
           
        WebResource service;
        service = client.resource(UriBuilder.fromUri(url + "/api/ratings").build());

        String s = service.accept(MediaType.APPLICATION_JSON).get(String.class);
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        
        return gson.fromJson(s, new TypeToken<Vector<Rating>>(){}.getType()); 
    }
}
