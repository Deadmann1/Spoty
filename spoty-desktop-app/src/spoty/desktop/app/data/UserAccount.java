/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.data;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lampr
 */
public class UserAccount {
    @SerializedName("IdUserAccount")
    private int idUserAccount;
    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("Firstname")
    private String firstname;
    @SerializedName("Lastname")
    private String lastname;
    @SerializedName("Birthdate")
    private String birthdate;
    @SerializedName("IdAccountType")
    private int idAccountType;

    public UserAccount(int idUserAccount, String username, String password, String firstname, String lastname, String birthdate, int idAccountType) {
        System.out.println("asdf");
        this.idUserAccount = idUserAccount;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.idAccountType = idAccountType;
    }

    
    
    
    public int getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(int idUserAccount) {
        this.idUserAccount = idUserAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getIdAccountType() {
        return idAccountType;
    }

    public void setIdAccountType(int idAccountType) {
        this.idAccountType = idAccountType;
    }
    
    
}
