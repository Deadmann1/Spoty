/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.helper;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 *
 * @author lampr
 */
public class LoginHelper {
    private String username;
    private String password;

    public LoginHelper(String username, String password) {
        this.username = username;
        this.password = password;
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

    

    @Override
    public String toString() {
        return "LoginHelper{" + "username=" + username + ", password=" + password + '}';
    }

    
}
