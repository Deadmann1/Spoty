package spoty.dekstop.app.UserInterface.pkgLogin;

import java.io.IOException;
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
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;


    @FXML
    void onAction_btnLogin(ActionEvent event) throws Exception {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../pkgMain/MainGUI.fxml"), resources);
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
    }

    @FXML
    void initialize() {
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'LoginGUI.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'LoginGUI.fxml'.";


    }

}
