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
import spoty.desktop.app.data.County;
import javafx.scene.layout.AnchorPane;


public class AddCountyGUIController {

    private static int idCountry;
    
    @FXML
    private AnchorPane paneAddCountyGUI;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddCounty;

    @FXML
    private Label lblInfoMessageAddCounty;

    @FXML
    private TextField txtCountyname;

    public static int getIdCountry() {
        return idCountry;
    }

    public static void setIdCountry(int idCountry) {
        AddCountyGUIController.idCountry = idCountry;
    }

    

    @FXML
    void onAction_btnAddCounty(ActionEvent event) {
        if (txtCountyname.getText().compareTo("")!=0)
        {
            int idCounty = AddressDatabase.getInstance().getNewCountyID();
            County newCounty = new County(idCounty, txtCountyname.getText(), idCountry);
            AddressDatabase.getInstance().addCounty(newCounty);
            
            Stage stage = (Stage) btnAddCounty.getScene().getWindow();
            stage.close();
        }
        
        else
            lblInfoMessageAddCounty.setText("Es muss ein Name eingegeben werden!");
    }

    @FXML
    void initialize() {
        assert btnAddCounty != null : "fx:id=\"btnAddCountry\" was not injected: check your FXML file 'AddCountyGUI.fxml'.";
        assert lblInfoMessageAddCounty != null : "fx:id=\"lblInfoMessageAddCounty\" was not injected: check your FXML file 'AddCountyGUI.fxml'.";
        assert txtCountyname != null : "fx:id=\"txtCountryname\" was not injected: check your FXML file 'AddCountyGUI.fxml'.";

        lblInfoMessageAddCounty.getStyleClass().add("labelInfo");
        paneAddCountyGUI.getStyleClass().add("pane");
    }

}

