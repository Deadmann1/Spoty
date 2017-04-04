/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.helper;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author lampr
 */
public class TokenHelper {
    @SerializedName("token")
    private String Token;

    public TokenHelper(String Token) {
        this.Token = Token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    @Override
    public String toString() {
        return "TokenHelper{" + "Token=" + Token + '}';
    }
    
    
}
