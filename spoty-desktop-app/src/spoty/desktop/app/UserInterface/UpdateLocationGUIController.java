package spoty.desktop.app.UserInterface;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import spoty.desktop.app.Database.AddressDatabase;
import spoty.desktop.app.Database.LocationDatabase;
import spoty.desktop.app.data.City;
import spoty.desktop.app.data.Country;
import spoty.desktop.app.data.County;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;


public class UpdateLocationGUIController {

    private Location updateLocation;
    
    
    @FXML
    private TextField txtLocationname;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnUpdateLocation;
    
    @FXML
    private ComboBox<City> cmbCity;

    @FXML
    private ComboBox<Country> cmbCountry;
    
    @FXML
    private ComboBox<County> cmbCounty;

    @FXML
    private ComboBox<LocationType> cmbLocationtype;


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
    void onAction_btnUpdateLocation(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert btnUpdateLocation != null : "fx:id=\"btnUpdateLocation\" was not injected: check your FXML file 'UpdateLocationGUI.fxml'.";


        fillCmbCounty();
        fillCmbCity();
        fillCmbCountry();
        fillCmbLocationType();
        fillUpdateLocationValues();
    }
    
    private void fillCmbCity()
    {
        cmbCity.getItems().addAll((Collection<City>)AddressDatabase.getInstance().getCities());
    }
    
    private void fillCmbLocationType()
    {
        cmbLocationtype.getItems().addAll((Collection<LocationType>)LocationDatabase.getInstance().getLocationTypes());
    }
    
    private void fillCmbCounty()
    {
        cmbCounty.getItems().addAll((Collection<County>)AddressDatabase.getInstance().getCounties());
    }
    
    private void fillCmbCountry()
    {
        cmbCountry.getItems().addAll((Collection<Country>)AddressDatabase.getInstance().getCountries());
    }
    
    private void fillUpdateLocationValues()
    {
        System.out.println(updateLocation.getLocationname());
        txtLocationname.setText(updateLocation.getLocationname());
    }
    

    public Location getUpdateLocation() {
        return updateLocation;
    }

    public void setUpdateLocation(Location updateLocation) {
        System.out.println("UpdateLocationSet");
        this.updateLocation = updateLocation;
        System.out.println("UpdateLocation: " + updateLocation.toString());
    }
    
    
}
