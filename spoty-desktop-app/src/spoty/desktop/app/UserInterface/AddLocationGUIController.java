package spoty.desktop.app.UserInterface;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private Label lblInfoMessageAddLocation;


    @FXML
    void cmbCity(ActionEvent event) {
        
    }

    @FXML
    void cmbCounty(ActionEvent event) {
       
    }
    
    @FXML
    void cmbCountry(ActionEvent event) {
        
    }

    @FXML
    void onAction_btnAddCity(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/AddCityGUI.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AddCityGUIController controller = fxmlLoader.<AddCityGUIController>getController();
        controller.setIdCounty(cmbCounty.getSelectionModel().getSelectedItem().getIdCounty());
        Scene sceneTable = new Scene(root);
        Stage stage = new Stage();     
        stage.setScene(sceneTable);
        stage.setTitle("Stadt hinzufügen");
        stage.showAndWait();
        fillCmbCity(cmbCounty.getSelectionModel().getSelectedItem().getIdCounty());
    }

    @FXML
    void onAction_btnAddCountry(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/AddCountryGUI.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene sceneTable = new Scene(root);
        Stage stage = new Stage();     
        stage.setScene(sceneTable);
        stage.setTitle("Staat hinzufügen");
        stage.showAndWait();
        fillCmbCountry();
    }

    @FXML
    void onAction_btnAddCounty(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/AddCountyGUI.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AddCountyGUIController controller = fxmlLoader.<AddCountyGUIController>getController();
        controller.setIdCountry(cmbCountry.getSelectionModel().getSelectedItem().getIdCountry());
        Scene sceneTable = new Scene(root);
        Stage stage = new Stage();     
        stage.setScene(sceneTable);
        stage.setTitle("Land hinzufügen");
        stage.showAndWait();
        fillCmbCounty(cmbCountry.getSelectionModel().getSelectedItem().getIdCountry());
    }

    @FXML
    void onAction_btnAddLocationtype(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/AddLocationTypeGUI.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene sceneTable = new Scene(root);
        Stage stage = new Stage();     
        stage.setScene(sceneTable);
        stage.setTitle("Typ hinzufügen");
        stage.showAndWait();
        fillCmbLocationType();
    }
    
    @FXML
    void onAction_btnAddLocation(ActionEvent event)
    {
        if(cmbCity.getSelectionModel().getSelectedItem()!=null && cmbLocationtype.getSelectionModel().getSelectedItem()!=null && txtLocationname.getText().compareTo("")!=0 && txtHousenumber.getText().compareTo("")!=0 && txtStreetname.getText().compareTo("")!=0)
        {
            int id_selectedType = cmbLocationtype.getSelectionModel().getSelectedItem().getIdType();
            //int id_selectedCountry = cmbCountry.getSelectionModel().getSelectedItem().getIdCountry();
            //int id_selectedCounty = cmbCounty.getSelectionModel().getSelectedItem().getIdCounty();
            int id_selectedCity = cmbCity.getSelectionModel().getSelectedItem().getIdCity();

            Address newAddress = new Address(AddressDatabase.getInstance().getNewAddressID(), id_selectedCity, txtStreetname.getText(), Integer.parseInt(txtHousenumber.getText()));
            Location newLocation = new Location(LocationDatabase.getInstance().getNewLocationID(), txtLocationname.getText(), id_selectedType, newAddress.getIdAddress());

            AddressDatabase.getInstance().addAddress(newAddress);
            LocationDatabase.getInstance().addLocation(newLocation);
        }
        
        else 
            lblInfoMessageAddLocation.setText("Alle Felder müssen ausgefüllt sein!");
        
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

        fillCmbCountry();
        fillCmbLocationType();
        
        cmbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {
            @Override
            public void changed(ObservableValue ov, Country t, Country t1) {
                if (t1 != null) {
                    cmbCounty.setVisible(true);
                    btnAddCounty.setVisible(true);
                    fillCmbCounty(t1.getIdCountry());
                }
            } 
        });
        
        cmbCounty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<County>() {
            @Override
            public void changed(ObservableValue ov, County t, County t1) {
                if (t1 != null) {
                    cmbCity.setVisible(true);
                    btnAddCity.setVisible(true);
                    fillCmbCity(t1.getIdCounty());
                }
            } 
        });
    }
    
    private void fillCmbCity(int idCounty)
    {
        cmbCity.getItems().clear();
        cmbCity.getItems().addAll((Collection<City>)AddressDatabase.getInstance().getCitiesByCounty(idCounty));
    }
    
    private void fillCmbLocationType()
    {
        cmbLocationtype.getItems().clear();
        cmbLocationtype.getItems().addAll((Collection<LocationType>)LocationDatabase.getInstance().getLocationTypes());
    }
    
    private void fillCmbCounty(int idCountry)
    {
        cmbCounty.getItems().clear();
        cmbCounty.getItems().addAll((Collection<County>)AddressDatabase.getInstance().getCountiesByCountry(idCountry));
    }
    
    private void fillCmbCountry()
    {
        cmbCountry.getItems().clear();
        cmbCountry.getItems().addAll((Collection<Country>)AddressDatabase.getInstance().getCountries());
    }
    
    
        

}
