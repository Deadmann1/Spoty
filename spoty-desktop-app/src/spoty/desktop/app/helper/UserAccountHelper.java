/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.helper;

import spoty.desktop.app.data.*;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import spoty.desktop.app.Database.UserAccountDatabase;

/**
 *
 * @author lampr
 */
public class UserAccountHelper {
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
    private long birthdate;
    @SerializedName("IdAccountType")
    private int idAccountType;

    public UserAccountHelper(int idUserAccount, String username, String password, String firstname, String lastname, long birthdate, int idAccountType) {
        System.out.println("asdf");
        this.idUserAccount = idUserAccount;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.idAccountType = idAccountType;
    }

    public UserAccountHelper(String username) {
        this.username = username;
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

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }


    public int getIdAccountType() {
        return idAccountType;
    }

    public void setIdAccountType(int idAccountType) {
        this.idAccountType = idAccountType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserAccountHelper other = (UserAccountHelper) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + username + ", " + firstname + ", " + lastname;
    }
    
    
    
    
}
