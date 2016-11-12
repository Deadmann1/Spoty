package spoty.desktop.app.UserInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spoty.desktop.app.Database.UserAccountDatabase;


public class LoginGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;
    
    @FXML
    private Label lblInfoMessageLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;


    @FXML
    void onAction_btnLogin(ActionEvent event) throws Exception {
        if (UserAccountDatabase.getInstance().existsUsername(txtUsername.getText())==true)
        {
            int idUserAccount = UserAccountDatabase.getInstance().getIDOfAccount(txtUsername.getText());
            
            if (txtPassword.getText().compareTo(UserAccountDatabase.getInstance().getPasswordOfAccount(idUserAccount))==0)
            {
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/MainGUI.fxml"));
                Parent root = (Parent) fxmlLoader.load();
            
                Scene sceneTable = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(sceneTable);
                stage.setTitle("Hauptmenü");
                Stage stageLogin = (Stage) btnLogin.getScene().getWindow();
                stageLogin.close();
             
                stage.showAndWait();  
            }
            
            else
                lblInfoMessageLogin.setText("Passwort falsch");
        }
        
        else
            lblInfoMessageLogin.setText("Username nicht vorhanden");
    }

    @FXML
    void initialize() throws Exception {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'LoginGUI.fxml'.";

        UserAccountDatabase.getInstance().generateTestUserAccounts();

    }

}
