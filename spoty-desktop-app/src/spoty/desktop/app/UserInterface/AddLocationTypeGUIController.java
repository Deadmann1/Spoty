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
import spoty.desktop.app.Database.LocationDatabase;
import spoty.desktop.app.data.LocationType;


public class AddLocationTypeGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddLocationType;

    @FXML
    private Label lblInfoMessageAddLocationType;

    @FXML
    private TextField txtTypename;


    @FXML
    void onAction_btnAddLocationType(ActionEvent event) {
        if (txtTypename.getText().compareTo("")!=0)
        {
            int idLocationType = LocationDatabase.getInstance().getNewLocationTypeID();
            LocationType newType = new LocationType(idLocationType, txtTypename.getText());
            LocationDatabase.getInstance().addLocationType(newType);
            
            Stage stage = (Stage) btnAddLocationType.getScene().getWindow();
            stage.close();
        }
        
        else
            lblInfoMessageAddLocationType.setText("Es muss ein Name eingegeben werden!");
    }

    @FXML
    void initialize() {
        assert btnAddLocationType != null : "fx:id=\"btnAddLocationType\" was not injected: check your FXML file 'AddLocationTypeGUI.fxml'.";
        assert lblInfoMessageAddLocationType != null : "fx:id=\"lblInfoMessageAddLocationType\" was not injected: check your FXML file 'AddLocationTypeGUI.fxml'.";
        assert txtTypename != null : "fx:id=\"txtTypename\" was not injected: check your FXML file 'AddLocationTypeGUI.fxml'.";


    }

}
