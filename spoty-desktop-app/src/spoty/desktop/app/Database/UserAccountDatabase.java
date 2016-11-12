/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;
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


    public static Vector<UserAccount> vecUserAccounts = new Vector<UserAccount>();
    public static Vector<AccountType> vecAccountTypes = new Vector<AccountType>();

    private UserAccountDatabase() {
    }

    public static UserAccountDatabase getInstance() {
            if (db == null) {
                    db = new UserAccountDatabase();
            }
            return db;
    }
    
    public void generateTestUserAccounts() throws Exception
    {
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdf.parse("30/12/1997");
        UserAccount userAccount1 = new UserAccount(1, "admin", "admin", "Daniel", "Lamprecht", d1, 1);
        
        Date d2 = sdf.parse("04/05/1998");
        UserAccount userAccount2 = new UserAccount(2, "sama", "sama", "Manuel", "Sammer", d2, 2);
        
        vecUserAccounts.add(userAccount1);
        vecUserAccounts.add(userAccount2);*/
    }
    public Vector<UserAccount> getUserAccounts()
    {
        return UserAccountService.getInstance().getUserAccounts();
        //return vecUserAccounts;
    }
    
    public String getPasswordOfAccount(int idUserAccount)
    {
        return UserAccountService.getInstance().getUserAccount(idUserAccount).getPassword();
        /*
        String returnPassword = "";
        
        for (UserAccount u : vecUserAccounts)
        {
            if (u.getIdUserAccount() == idUserAccount)
                returnPassword = u.getPassword();
        }
        
        //System.out.println("Password: " + returnPassword);
        
        return returnPassword;*/
    }
    
    public boolean existsUsername(String username)
    {
        boolean exists = false;
        
        for (UserAccount u : this.getUserAccounts())
        {
            if (u.getUsername().compareTo(username)==0)
                exists = true;
        }
        
        //System.out.println("Username exists: " + exists);
        return exists;
    }

    public int getIDOfAccount(String username) {
        int id=-1;
        
        for (UserAccount u : this.getUserAccounts())
        {
            if (u.getUsername().compareTo(username)==0)
                id = u.getIdUserAccount();
        }
        
        return id;
    }
}
