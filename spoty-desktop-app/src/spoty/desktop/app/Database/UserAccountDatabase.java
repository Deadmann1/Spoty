/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;
import java.util.Objects;
import java.util.Vector;
import spoty.desktop.app.Service.LocationService;
import spoty.desktop.app.Service.UserAccountService;
import spoty.desktop.app.data.AccountType;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.UserAccount;
import spoty.desktop.app.helper.LoginHelper;
import spoty.desktop.app.helper.TokenHelper;
import spoty.desktop.app.helper.UserAccountHelper;

/**
 *
 * @author lampr
 */
public class UserAccountDatabase {
    
    private static UserAccountDatabase db = null;
    private String connectionString = "";
    private String username = "";
    private String password = "";

    private Connection conn = null;

    public TokenHelper token = null;

    private UserAccountDatabase() {
    }

    public static UserAccountDatabase getInstance() {
            if (db == null) {
                    db = new UserAccountDatabase();
            }
            return db;
    }
    
    public int getAuthenticationToken(LoginHelper userAccount)
    {
        TokenHelper token = null;
        token = UserAccountService.getInstance().getAuthenticationToken(userAccount);
        //System.out.println(UserAccountService.getInstance().getAuthenticationToken(userAccount).getToken());
        if (token!=null)
        {
            this.token = token;
            return 1;
        }
        
        
        else
            return -1;

    }
    
    public Vector<UserAccount> getUserAccounts()
    {
        return UserAccountService.getInstance().getUserAccounts();
    }
    
    public String getPasswordOfAccount(int idUserAccount)
    {
        return UserAccountService.getInstance().getUserAccount(idUserAccount).getPassword();
    }
    
    public boolean existsUsername(String username)
    {
        boolean exists = false;
        
        for (UserAccount u : this.getUserAccounts())
        {
            if (u.getUsername().compareTo(username)==0)
                exists = true;
        }
        
        return exists;
    }

    
    public int getIDOfAccount(String username) {
        int id=-1;
        
        int index = this.getUserAccounts().indexOf(new UserAccount(username));
        UserAccount userAccount = this.getUserAccounts().elementAt(index);
        
        if (username.compareTo(userAccount.getUsername())==0)
        {
                id = userAccount.getIdUserAccount();
        }
        
        return id;
    }
    
    public UserAccount getUserAccount(int idUserAccount)
    {
        return UserAccountService.getInstance().getUserAccount(idUserAccount);
    } 
    
    public void deleteUserAccount(UserAccount deleteUserAccount)
    {
        UserAccountService.getInstance().deleteUserAccount(deleteUserAccount);
    }
    
    public int getNewUserAccountID()
    {
        return UserAccountService.getInstance().getNewUserAccountID().getIdUserAccount();
    }
    
    public void addUserAccount(UserAccountHelper newUserAccount) {
        UserAccountService.getInstance().postUserAccount(newUserAccount);
    }
    
    public void updateUserAccount(UserAccountHelper updateUserAccount) {
        UserAccountService.getInstance().putUserAccount(updateUserAccount);
    }

    
    
}
