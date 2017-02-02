/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import spoty.desktop.app.Service.RatingService;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;
import spoty.desktop.app.data.Rating;

/**
 *
 * @author lampr
 */
public class RatingDatabase {
    private static RatingDatabase db = null;

    private RatingDatabase() {
    }

    public static RatingDatabase getInstance() {
            if (db == null) {
                    db = new RatingDatabase();
            }
            return db;
    }

    public Vector<Rating> getRatingsByLocation(int idLocation) {
        Vector<Rating> vecRatings = RatingService.getInstance().getRatings();
        Vector<Rating> vecRatingsOfLocation = new Vector<Rating>();
        
        for (Rating r : vecRatings)
        {
            if (r.getIdLocation() == idLocation)
                vecRatingsOfLocation.add(r);
        }
                
            
         return vecRatingsOfLocation;
    }
    
    public Vector<Rating> getRatingsBetweenDatesByLocatation(int idLocation, Date fromDate, Date toDate) throws ParseException
    {
        Vector<Rating> vecRatings = RatingService.getInstance().getRatings();
        Vector<Rating> vecRatingsBetweenDatesByLocation = new Vector<Rating>();
        
        for (Rating r : vecRatings)
        {
            if (r.getIdLocation() == idLocation)
            {
                System.out.println("fromDate: " + fromDate.toString());
                System.out.println("toDate: " + toDate.toString());
                System.out.println("Date: " + r.getRatingDateAsDate().toString());
                if ((r.getRatingDateAsDate().after(fromDate) || r.getRatingDateAsDate().compareTo(fromDate)==0) && (r.getRatingDateAsDate().before(toDate) || r.getRatingDateAsDate().compareTo(toDate)==0))
                {
                    vecRatingsBetweenDatesByLocation.add(r);
                }
            }     
        }
        
        return vecRatingsBetweenDatesByLocation;
    }
    
    public Vector<Rating> getRatingsByGradeAndLocation(int idLocation, int grade)
    {
        Vector<Rating> vecRatings = RatingService.getInstance().getRatings();
        Vector<Rating> vecRatingsByGradeAndLocation = new Vector<Rating>();
        
        for (Rating r : vecRatings)
        {
            if (r.getIdLocation() == idLocation)
            {
                if (r.getGrade() == grade)
                {
                    vecRatingsByGradeAndLocation.add(r);
                }
            }     
        }
        
        return vecRatingsByGradeAndLocation;
    }
    
    public int getCountOfGrade (int idLocation, int grade)
    {
        int count = 0;
        Vector<Rating> vecRatings = RatingService.getInstance().getRatings();
        
        
        for (Rating r : vecRatings)
        {
            if (r.getIdLocation() == idLocation)
            {
                if (r.getGrade() == grade)
                    count++;
            }
        }
        
        return count;
    }
    
    public int getCountOfGradeBetweenDates(int idLocation, int grade, Date fromDate, Date toDate) throws ParseException
    {
        int count = 0;
        Vector<Rating> vecRatings = RatingService.getInstance().getRatings();
        
        
        for (Rating r : vecRatings)
        {
            if (r.getIdLocation() == idLocation)
            {
                if ((r.getRatingDateAsDate().after(fromDate) || r.getRatingDateAsDate().compareTo(fromDate)==0) && (r.getRatingDateAsDate().before(toDate) || r.getRatingDateAsDate().compareTo(toDate)==0))
                {
                    if (r.getGrade() == grade)
                        count++;
                }
            }
        }
        
        return count;
    }
}
