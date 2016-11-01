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
import spoty.desktop.app.data.Address;
import spoty.desktop.app.data.City;
import spoty.desktop.app.data.Country;
import spoty.desktop.app.data.County;
import spoty.desktop.app.data.Location;
import spoty.desktop.app.data.LocationType;


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
    private ComboBox<City> cmbCity;

    @FXML
    private ComboBox<Country> cmbCountry;
    
    @FXML
    private ComboBox<County> cmbCounty;

    @FXML
    private ComboBox<LocationType> cmbLocationtype;

    @FXML
    private TextField txtHousenumber;

    @FXML
    private TextField txtStreetname;
    
    @FXML
    private TextField txtLocationname;


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
        int id_selectedType = cmbLocationtype.getSelectionModel().getSelectedItem().getIdType();
        /*int id_selectedCountry = cmbCountry.getSelectionModel().getSelectedItem().getIdCountry();
        int id_selectedCounty = cmbCounty.getSelectionModel().getSelectedItem().getIdCounty();*/
        int id_selectedCity = cmbCity.getSelectionModel().getSelectedItem().getIdCity();
        
        Address newAddress = new Address(AddressDatabase.getInstance().getNewAddressID(), id_selectedCity, txtStreetname.getText(), Integer.parseInt(txtHousenumber.getText()));
        Location newLocation = new Location(LocationDatabase.getInstance().getNewLocationID(), txtLocationname.getText(), id_selectedType, newAddress.getIdAddress());
        
        AddressDatabase.vecAddresses.add(newAddress);
        LocationDatabase.vecLocations.add(newLocation);
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

        fillCmbCounty();
        fillCmbCity();
        fillCmbCountry();
        fillCmbLocationType();
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

}
