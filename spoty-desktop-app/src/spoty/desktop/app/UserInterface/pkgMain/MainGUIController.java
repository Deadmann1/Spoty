package pkgMain;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class MainGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddLocation;

    @FXML
    private Button btnUpdateLocation;

    @FXML
    private ListView<?> listViewLocations;


    @FXML
    void onAction_btnAddLocation(ActionEvent event) {
    }

    @FXML
    void onAction_btnUpdateLocation(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert btnAddLocation != null : "fx:id=\"btnAddLocation\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnUpdateLocation != null : "fx:id=\"btnUpdateLocation\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert listViewLocations != null : "fx:id=\"listViewLocations\" was not injected: check your FXML file 'MainGUI.fxml'.";


    }

}
