/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.Database;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import spoty.desktop.app.data.AccountType;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;
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
                    db.connectionString = ConnectionProperties.getInstance().getConnectionString();
                    db.username = ConnectionProperties.getInstance().getUsername();
                    db.password = ConnectionProperties.getInstance().getPassword();
            }
            return db;
    }
    
    public void generateTestUserAccounts() throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdf.parse("30/12/1997");
        UserAccount userAccount1 = new UserAccount(1, "admin", "admin", "Daniel", "Lamprecht", d1, 1);
        
        Date d2 = sdf.parse("04/05/1998");
        UserAccount userAccount2 = new UserAccount(1, "sama", "sama", "Manuel", "Sammer", d2, 2);
        
        vecUserAccounts.add(userAccount1);
        vecUserAccounts.add(userAccount2);
    }
    public Vector<UserAccount> getUserAccounts()
    {
        return vecUserAccounts;
    }
    
    public String getPasswordOfAccount(int idUserAccount)
    {
        String returnPassword = "";
        
        for (UserAccount u : vecUserAccounts)
        {
            if (u.getIdUserAccount() == idUserAccount)
                returnPassword = u.getPassword();
        }
        
        return returnPassword;
    }
}
