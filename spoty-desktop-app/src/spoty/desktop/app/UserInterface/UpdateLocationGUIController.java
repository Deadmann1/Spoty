package spoty.desktop.app.UserInterface;

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


public class UpdateLocationGUIController {

    private int idLocation;
    private int idAddress;
    
    
    @FXML
    private TextField txtLocationname;
    
    @FXML
    private TextField txtStreet;
    
    @FXML
    private TextField txtHousenumber;
    
    @FXML
    private Label lblInfoMessageUpdateLocation;
    
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
    void onAction_btnUpdateLocation(ActionEvent event) throws Exception {
        if(cmbCity.getSelectionModel().getSelectedItem()!=null && cmbLocationtype.getSelectionModel().getSelectedItem()!=null && txtLocationname.getText().compareTo("")!=0 && txtHousenumber.getText().compareTo("")!=0 && txtStreet.getText().compareTo("")!=0)
        {
            int id_selectedType = cmbLocationtype.getSelectionModel().getSelectedItem().getIdType();
            //int id_selectedCountry = cmbCountry.getSelectionModel().getSelectedItem().getIdCountry();
            //int id_selectedCounty = cmbCounty.getSelectionModel().getSelectedItem().getIdCounty();
            int id_selectedCity = cmbCity.getSelectionModel().getSelectedItem().getIdCity();

            Location updateLocation = new Location(idLocation, txtLocationname.getText(), id_selectedType, idAddress);
            LocationDatabase.getInstance().updateLocation(updateLocation);
            
            Address updateAddress = new Address(idAddress, id_selectedCity, txtStreet.getText(), Integer.parseInt(txtHousenumber.getText()));
            AddressDatabase.getInstance().updateAddress(updateAddress);
            
            Stage stage = (Stage) btnUpdateLocation.getScene().getWindow();
            stage.close();
        }
        
        else
            lblInfoMessageUpdateLocation.setText("Es müssen alle Felder ausgefüllt werden!");
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }
    
    

    @FXML
    void initialize() {
        assert btnUpdateLocation != null : "fx:id=\"btnUpdateLocation\" was not injected: check your FXML file 'UpdateLocationGUI.fxml'.";
    }
    
    
    
    public void fillCmbLocationType()
    {
        int idLocationType = LocationDatabase.getInstance().getLocation(idLocation).getIdType();
        LocationType updateLocationType = LocationDatabase.getInstance().getLocationType(idLocationType);
        cmbLocationtype.getItems().addAll((Collection<LocationType>)LocationDatabase.getInstance().getLocationTypes());
        cmbLocationtype.setValue(updateLocationType);
    }
    
    public void fillCmbCity()
    {
        int idCity = AddressDatabase.getInstance().getAddress(idAddress).getIdCity();
        City updateCity = AddressDatabase.getInstance().getCity(idCity);
        cmbCity.getItems().addAll((Collection<City>)AddressDatabase.getInstance().getCitiesByCounty(updateCity.getIdCounty()));
        cmbCity.setValue(updateCity);
        
        
    }
    
    public void fillCmbCity(int idCounty)
    {
        cmbCity.getItems().clear();
        cmbCity.getItems().addAll((Collection<City>)AddressDatabase.getInstance().getCitiesByCounty(idCounty));
    }
    
    public void fillCmbCounty()
    {
        int idCity = AddressDatabase.getInstance().getAddress(idAddress).getIdCity();
        City updateCity = AddressDatabase.getInstance().getCity(idCity);
        County updateCounty = AddressDatabase.getInstance().getCounty(updateCity.getIdCounty());
        
        cmbCounty.getItems().addAll((Collection<County>)AddressDatabase.getInstance().getCountiesByCountry(updateCounty.getIdCountry()));
        cmbCounty.setValue(updateCounty);
        
        
        cmbCounty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<County>() {
            @Override
            public void changed(ObservableValue ov, County t, County t1) {
                if (t1 != null) {
                    fillCmbCity(t1.getIdCounty());
                }
            } 
        });
    }
    
    public void fillCmbCounty(int idCountry)
    {
        cmbCounty.getItems().clear();
        cmbCounty.getItems().addAll((Collection<County>)AddressDatabase.getInstance().getCountiesByCountry(idCountry)); 
    }
    
    public void fillCmbCountry()
    {
        int idCity = AddressDatabase.getInstance().getAddress(idAddress).getIdCity();
        City updateCity = AddressDatabase.getInstance().getCity(idCity);
        County updateCounty = AddressDatabase.getInstance().getCounty(updateCity.getIdCounty());
        Country updateCountry = AddressDatabase.getInstance().getCountry(updateCounty.getIdCountry());
        cmbCountry.getItems().addAll((Collection<Country>)AddressDatabase.getInstance().getCountries());
        cmbCountry.setValue(updateCountry);   
        
        cmbCountry.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>() {
            @Override
            public void changed(ObservableValue ov, Country t, Country t1) {
                if (t1 != null) {
                    fillCmbCounty(t1.getIdCountry());
                }
            } 
        });
    }
    
    public void fillUpdateLocationValues()
    {
        Location updateLocation = LocationDatabase.getInstance().getLocation(idLocation);
        txtLocationname.setText(updateLocation.getLocationname());
        Address updateAddress = AddressDatabase.getInstance().getAddress(idAddress);
        txtStreet.setText(updateAddress.getStreetname());
        txtHousenumber.setText(Integer.toString(updateAddress.getHousenumber()));
        
    }  
}
