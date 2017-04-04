/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoty.desktop.app.UserInterface;

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


public class AddUserAccountGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private ComboBox<?> cmbAccountType;

    @FXML
    private Label lblMessage;

    @FXML
    private AnchorPane paneAddUserAccountGUI;

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
    void onAction_btnAdd(ActionEvent event) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); 
        Date birthDate=df.parse(txtBirthdate.getText());

        birthDate.setHours(birthDate.getHours()+24);
        birthDate.setMonth(birthDate.getMonth()+1);
        long millisDate = birthDate.getTime();
        UserAccountHelper userAccount = new UserAccountHelper(UserAccountDatabase.getInstance().getNewUserAccountID(), txtUsername.getText(), txtPassword.getText(), txtFirstname.getText(), txtLastname.getText(), millisDate, cmbAccountType.getSelectionModel().getSelectedIndex()+1);
        
        System.out.println("UserAccount-add: " + userAccount.toString());
        UserAccountDatabase.getInstance().addUserAccount(userAccount);
    }

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert cmbAccountType != null : "fx:id=\"cmbAccountType\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert lblMessage != null : "fx:id=\"lblMessage\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert paneAddUserAccountGUI != null : "fx:id=\"paneAddUserAccountGUI\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert txtBirthdate != null : "fx:id=\"txtBirthdate\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert txtFirstname != null : "fx:id=\"txtFirstname\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert txtLastname != null : "fx:id=\"txtLastname\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'AddUserAccountGUI.fxml'.";

        paneAddUserAccountGUI.getStyleClass().add("pane"); 
    }

}
