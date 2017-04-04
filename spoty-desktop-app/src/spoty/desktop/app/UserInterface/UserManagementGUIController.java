/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spoty.desktop.app.UserInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import spoty.desktop.app.Database.LocationDatabase;
import spoty.desktop.app.Database.UserAccountDatabase;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.UserAccount;
import javafx.scene.input.MouseEvent;


public class UserManagementGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblAccountType;

    @FXML
    private Label lblBirthdate;

    @FXML
    private Label lblFirstname;

    @FXML
    private Label lblLastname;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private ListView<UserAccount> listViewUsers;

    @FXML
    private AnchorPane paneUserManagementGUI;
    
    @FXML
    private Button btnAddUser;
    
    @FXML
    private Button btnChangeUser;
    
    @FXML
    private Button btnDeleteUser;
    
    @FXML
    private Label lblInfoMessageUsermanagement;

    
    
    @FXML
    void onAction_btnAddUser(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/AddUserAccountGUI.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            
            Scene sceneTable = new Scene(root);
            Stage stage = new Stage();     
            stage.setScene(sceneTable);
            stage.setTitle("UserAccount hinzufügen");

            stage.showAndWait();
            
            fillListViewUserAccounts();
    }
    
    @FXML
    void onAction_btnChangeUser(ActionEvent event) throws Exception {
        if (listViewUsers.getSelectionModel().getSelectedItem()!=null)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/UpdateUserAccountGUI.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            UpdateUserAccountGUIController controller = fxmlLoader.<UpdateUserAccountGUIController>getController();
            controller.setIdUserAccount(listViewUsers.getSelectionModel().getSelectedItem().getIdUserAccount());
            Scene sceneTable = new Scene(root);
            Stage stage = new Stage();     
            stage.setScene(sceneTable);
            stage.setTitle("UserAccount ändern");
            stage.setOnShown(new EventHandler<WindowEvent>() {
              public void handle(WindowEvent we) {
                 controller.fillUserAccountValues();
              }
            });  

            stage.showAndWait();
            
            fillListViewUserAccounts();
        }
        
        else
            lblInfoMessageUsermanagement.setText("Es muss ein User ausgewählt werden, um ihn zu bearbeiten!");
    }
    
    @FXML
    void onAction_btnDeleteUser(ActionEvent event) throws Exception {
        if (listViewUsers.getSelectionModel().getSelectedItem()!=null)
        {
            UserAccountDatabase.getInstance().deleteUserAccount(listViewUsers.getSelectionModel().getSelectedItem());
            listViewUsers.setItems(FXCollections.observableArrayList(UserAccountDatabase.getInstance().getUserAccounts()));
        }
        
        else
            lblInfoMessageUsermanagement.setText("Es muss ein User ausgewählt werden, um ihn zu löschen!");
    }
    
    public void fillListViewUserAccounts()
    {
        listViewUsers.setItems(FXCollections.observableArrayList(UserAccountDatabase.getInstance().getUserAccounts()));
    }
    
    @FXML
    void onMouseClicked_ListViewUsers(MouseEvent event) {
        fillInformationData();
    }
    
    private void fillInformationData()
    {
        UserAccount currentlySelectedUser = listViewUsers.getSelectionModel().getSelectedItem();
        
        lblUsername.setText(currentlySelectedUser.getUsername());
        lblPassword.setText(currentlySelectedUser.getPassword());
        lblBirthdate.setText(currentlySelectedUser.getBirthdate());
        lblFirstname.setText(currentlySelectedUser.getFirstname());
        lblLastname.setText(currentlySelectedUser.getLastname());
        
        switch (currentlySelectedUser.getIdAccountType())
        {
            case 1:
            {
                lblAccountType.setText("Admin");
            }
            break;
            
            case 2:
            {
                lblAccountType.setText("Guest");
            }
            
            case 3:
            {
                lblAccountType.setText("User");
            }
        }
    }

    @FXML
    void initialize() {
        assert lblAccountType != null : "fx:id=\"lblAccountType\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert lblBirthdate != null : "fx:id=\"lblBirthdate\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert lblFirstname != null : "fx:id=\"lblFirstname\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert lblLastname != null : "fx:id=\"lblLastname\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert listViewUsers != null : "fx:id=\"listViewUsers\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";
        assert paneUserManagementGUI != null : "fx:id=\"paneUserManagementGUI\" was not injected: check your FXML file 'UserManagementGUI.fxml'.";

        paneUserManagementGUI.getStyleClass().add("pane"); 
    }

}
