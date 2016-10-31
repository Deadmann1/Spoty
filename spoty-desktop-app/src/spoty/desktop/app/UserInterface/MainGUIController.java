package spoty.desktop.app.UserInterface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


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
    void onAction_btnAddLocation(ActionEvent event) throws Exception {
        Parent rootframeTable = FXMLLoader.load(getClass().getResource("resources/AddLocationGUI.fxml"));
        Scene sceneTable = new Scene(rootframeTable);
        Stage stage = new Stage();
        stage.setScene(sceneTable);
        stage.setTitle("Location hinzufügen");
        stage.showAndWait();

    }

    @FXML
    void onAction_btnUpdateLocation(ActionEvent event) throws Exception {
        Parent rootframeTable = FXMLLoader.load(getClass().getResource("resources/UpdateLocationGUI.fxml"));
        Scene sceneTable = new Scene(rootframeTable);
        Stage stage = new Stage();
        stage.setScene(sceneTable);
        stage.setTitle("Hauptmenü");
        stage.showAndWait();
    }

    @FXML
    void initialize() {
        assert btnAddLocation != null : "fx:id=\"btnAddLocation\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert btnUpdateLocation != null : "fx:id=\"btnUpdateLocation\" was not injected: check your FXML file 'MainGUI.fxml'.";
        assert listViewLocations != null : "fx:id=\"listViewLocations\" was not injected: check your FXML file 'MainGUI.fxml'.";


    }

}
