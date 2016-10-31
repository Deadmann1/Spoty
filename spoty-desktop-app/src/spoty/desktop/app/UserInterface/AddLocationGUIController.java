package spoty.desktop.app.UserInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class AddLocationGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddCity;

    @FXML
    private Button btnAddCountry;

    @FXML
    private Button btnAddCounty;

    @FXML
    private Button btnAddLocationtype;
    
    @FXML
    private Button btnAddLocation;

    @FXML
    private ComboBox<?> cmbCity;

    @FXML
    private ComboBox<?> cmbCountry;

    @FXML
    private ComboBox<?> cmbLocationtype;

    @FXML
    private TextField txtHousenumber;

    @FXML
    private TextField txtStreetname;


    @FXML
    void cmbCity(ActionEvent event) {
    }

    @FXML
    void cmbCounty(ActionEvent event) {
    }

    @FXML
    void onAction_btnAddCity(ActionEvent event) {
    }

    @FXML
    void onAction_btnAddCountry(ActionEvent event) {
    }

    @FXML
    void onAction_btnAddCounty(ActionEvent event) {
    }

    @FXML
    void onAction_btnAddLocationtype(ActionEvent event) {
    }
    
    @FXML
    void onAction_btnAddLocation(ActionEvent event)
    {
        
    }

    @FXML
    void initialize() {
        assert btnAddCity != null : "fx:id=\"btnAddCity\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert btnAddCountry != null : "fx:id=\"btnAddCountry\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert btnAddCounty != null : "fx:id=\"btnAddCounty\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert btnAddLocationtype != null : "fx:id=\"btnAddLocationtype\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert cmbCity != null : "fx:id=\"cmbCity\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert cmbCountry != null : "fx:id=\"cmbCountry\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert cmbLocationtype != null : "fx:id=\"cmbLocationtype\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert txtHousenumber != null : "fx:id=\"txtHousenumber\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";
        assert txtStreetname != null : "fx:id=\"txtStreetname\" was not injected: check your FXML file 'AddLocationGUI.fxml'.";


    }

}
