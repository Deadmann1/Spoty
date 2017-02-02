/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;
import java.util.Objects;
import java.util.Vector;
import spoty.desktop.app.Service.UserAccountService;
import spoty.desktop.app.data.AccountType;
import spoty.desktop.app.data.UserAccount;

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


    private UserAccountDatabase() {
    }

    public static UserAccountDatabase getInstance() {
            if (db == null) {
                    db = new UserAccountDatabase();
            }
            return db;
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
}
