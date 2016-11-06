package spoty.desktop.app.UserInterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import spoty.desktop.app.Database.AddressDatabase;
import spoty.desktop.app.data.City;
import spoty.desktop.app.data.County;


public class AddCityGUIController {

    private int idCounty;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddCity;

    @FXML
    private Label lblInfoMessageAddCity;

    @FXML
    private TextField txtCityname;
    
    @FXML
    private TextField txtPostalcode;

    public int getIdCounty() {
        return idCounty;
    }

    public void setIdCounty(int idCounty) {
        this.idCounty = idCounty;
    }

    
    

    @FXML
    void onAction_btnAddCity(ActionEvent event) {
        if (txtCityname.getText().compareTo("")!=0 && txtPostalcode.getText().compareTo("")!=0)
        {
            System.out.println("IDCount: " + idCounty);
            int idCity = AddressDatabase.getInstance().getNewCityID();
            City newCity = new City(idCity, Integer.parseInt(txtPostalcode.getText()), txtCityname.getText(), idCounty);
            AddressDatabase.getInstance().addCity(newCity);
        }
        
        else
            lblInfoMessageAddCity.setText("Es müssen alle Felder ausgefüllt werden!");
    }

    @FXML
    void initialize() {
        assert btnAddCity != null : "fx:id=\"btnAddCity\" was not injected: check your FXML file 'AddCityGUI.fxml'.";
        assert lblInfoMessageAddCity != null : "fx:id=\"lblInfoMessageAddCity\" was not injected: check your FXML file 'AddCityGUI.fxml'.";
        assert txtCityname != null : "fx:id=\"txtCountyname\" was not injected: check your FXML file 'AddCityGUI.fxml'.";


    }

}
