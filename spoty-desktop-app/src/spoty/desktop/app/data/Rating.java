/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.data;

import com.google.gson.annotations.SerializedName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author lampr
 */
public class Rating {
    @SerializedName("Grade")
    private int grade;
    @SerializedName("Feedback")
    private String feedback;
    @SerializedName("Date")
    private String ratingDate;
    @SerializedName("IdUserAccount")
    private int IdUserAccount;
    @SerializedName("IdLocation")
    private int IdLocation;


    public Rating() {
    }

    public Rating(int grade, String feedback, String rDate, int IdUserAccount, int IdLocation) throws ParseException {
        this.grade = grade;
        this.feedback = feedback;
        setRatingDate(rDate);
        this.IdUserAccount = IdUserAccount; 
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getIdUserAccount() {
        return IdUserAccount;
    }

    public void setIdUserAccount(int IdUserAccount) {
        this.IdUserAccount = IdUserAccount;
    }

    public int getIdLocation() {
        return IdLocation;
    }

    public void setIdLocation(int IdLocation) {
        this.IdLocation = IdLocation;
    }

    public String getRatingDate() {
        return ratingDate;
    }
    
    public Date getRatingDateAsDate() throws ParseException
    {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd.MM.yyyy");
        
        Date originalDate = originalFormat.parse(ratingDate);
        String formattedString = targetFormat.format(originalDate);
        Date formattedDate = targetFormat.parse(formattedString);
        
        return formattedDate;
    }

    public void setRatingDate(String ratingDate) throws ParseException {
        //DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        this.ratingDate = ratingDate;//format.parse(ratingDate);
    }

    @Override
    public String toString() {
        if (feedback == null)
            feedback = "";
        return ratingDate + ": " + grade + " (" + feedback + ")";
    }
    
    
}
