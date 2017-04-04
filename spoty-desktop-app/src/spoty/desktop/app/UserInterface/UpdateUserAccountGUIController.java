/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.UserInterface;

/**
 *
 * @author lampr
 */

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import spoty.desktop.app.Database.UserAccountDatabase;
import spoty.desktop.app.data.UserAccount;
import spoty.desktop.app.helper.UserAccountHelper;


public class UpdateUserAccountGUIController {

    private int idUserAccount;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbAccountType;

    @FXML
    private AnchorPane paneUpdateUserAccountGUI;

    @FXML
    private TextField txtBirthdate;

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;
    
    @FXML
    private Label lblInfoMessageUpdateUserAccount;


    public int getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(int idUserAccount) {
        this.idUserAccount = idUserAccount;
    }

    

    @FXML
    void onAction_btnUpdate(ActionEvent event) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 
        Date birthDate=df.parse(txtBirthdate.getText());
        birthDate.setHours(birthDate.getHours()+24);
        birthDate.setMonth(birthDate.getMonth()+1);
        long millisDate = birthDate.getTime();
        
        UserAccountHelper userAccount = new UserAccountHelper(this.getIdUserAccount(), txtUsername.getText(), txtPassword.getText(), txtFirstname.getText(), txtLastname.getText(), millisDate, cmbAccountType.getSelectionModel().getSelectedIndex()+1);
        System.out.println("UserAccount-update: " + userAccount.toString());
        UserAccountDatabase.getInstance().updateUserAccount(userAccount);
    }

    @FXML
    void initialize() {
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert cmbAccountType != null : "fx:id=\"cmbAccountType\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert paneUpdateUserAccountGUI != null : "fx:id=\"paneUpdateUserAccountGUI\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert txtBirthdate != null : "fx:id=\"txtBirthdate\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert txtFirstname != null : "fx:id=\"txtFirstname\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert txtLastname != null : "fx:id=\"txtLastname\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'UpdateUserAccountGUI.fxml'.";

        paneUpdateUserAccountGUI.getStyleClass().add("pane"); 
    }
    
    public void fillUserAccountValues()
    {
        UserAccount updateUserAccount = UserAccountDatabase.getInstance().getUserAccount(idUserAccount);
        
        txtUsername.setText(updateUserAccount.getUsername());
        txtPassword.setText(updateUserAccount.getPassword());
        txtFirstname.setText(updateUserAccount.getFirstname());
        txtLastname.setText(updateUserAccount.getLastname());
        //txtBirthdate.setText(updateUserAccount.getBirthdate());
        cmbAccountType.getSelectionModel().select(updateUserAccount.getIdAccountType()-1);
    }

}
