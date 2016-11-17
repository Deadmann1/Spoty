package spoty.desktop.app.UserInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spoty.desktop.app.Database.AddressDatabase;
import spoty.desktop.app.data.Country;
import javafx.scene.layout.AnchorPane;


public class AddCountryGUIController {

    @FXML
    private AnchorPane paneAddCountryGUI;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddCountry;

    @FXML
    private Label lblInfoMessageAddCountry;

    @FXML
    private TextField txtCountryname;


    @FXML
    void onAction_btnAddCountry(ActionEvent event) {
        if (txtCountryname.getText().compareTo("")!=0)
        {
            int idCountry = AddressDatabase.getInstance().getNewCountryID();
            Country newCountry = new Country(idCountry, txtCountryname.getText());
            AddressDatabase.getInstance().addCountry(newCountry);
            
            Stage stage = (Stage) btnAddCountry.getScene().getWindow();
            stage.close();
        }
        
        else
            lblInfoMessageAddCountry.setText("Es muss ein Name eingegeben werden!");
    }

    @FXML
    void initialize() {
        assert btnAddCountry != null : "fx:id=\"btnAddCountry\" was not injected: check your FXML file 'AddCountryGUI.fxml'.";
        assert lblInfoMessageAddCountry != null : "fx:id=\"lblInfoMessageAddCountry\" was not injected: check your FXML file 'AddCountryGUI.fxml'.";
        assert txtCountryname != null : "fx:id=\"txtCountryname\" was not injected: check your FXML file 'AddCountryGUI.fxml'.";

        lblInfoMessageAddCountry.getStyleClass().add("labelInfo");
        paneAddCountryGUI.getStyleClass().add("pane");
    }

}

