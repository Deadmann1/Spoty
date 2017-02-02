package spoty.desktop.app.UserInterface;

import static com.google.gson.internal.bind.TypeAdapters.LOCALE;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import spoty.desktop.app.Database.LocationDatabase;
import spoty.desktop.app.Database.RatingDatabase;
import spoty.desktop.app.Database.UserAccountDatabase;
import spoty.desktop.app.data.FilterCriteria;
import spoty.desktop.app.data.Rating;


public class RatingGUIController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblInfoMessageRatings;

    @FXML
    private ListView<Rating> listViewRatings;

    @FXML
    private AnchorPane paneRatingGUI;

    @FXML
    private PieChart pieChartRatings;
    
    @FXML
    private Button btnFilterRatings;

    @FXML
    private Button btnGenerateCSVFile;
    
    @FXML
    private ComboBox cmbGradeFilter;
    
    @FXML
    private TextField txtFromDate;

    @FXML
    private TextField txtToDate;
    
    @FXML
    private Label lblLocationname;
    
    private FilterCriteria criteria;
    
    private int idLocation;
    
    



    @FXML
    void initialize() {
        assert lblInfoMessageRatings != null : "fx:id=\"lblInfoMessageRatings\" was not injected: check your FXML file 'RatingGUI.fxml'.";
        assert listViewRatings != null : "fx:id=\"listViewRatings\" was not injected: check your FXML file 'RatingGUI.fxml'.";
        assert paneRatingGUI != null : "fx:id=\"paneRatingGUI\" was not injected: check your FXML file 'RatingGUI.fxml'.";
        assert pieChartRatings != null : "fx:id=\"pieChartRatings\" was not injected: check your FXML file 'RatingGUI.fxml'.";

        lblInfoMessageRatings.getStyleClass().add("labelInfo");
        paneRatingGUI.getStyleClass().add("pane");
        
        cmbGradeFilter.getItems().addAll("Alle", "Sehr Gut", "Gut", "Befriedigend", "Genügend", "Nicht Genügend");    
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    void fillRatings() throws ParseException {
        lblLocationname.setText(LocationDatabase.getInstance().getLocation(idLocation).getLocationname());
        
        listViewRatings.getItems().clear();
        
        listViewRatings.setItems(FXCollections.observableArrayList(RatingDatabase.getInstance().getRatingsByLocation(idLocation)));
        
        fillChartRatings();
        
        criteria = criteria.Normal;
    }
    
    void fillRatingsBetweenDates(Date fromDate, Date toDate) throws ParseException
    {
        listViewRatings.getItems().clear();
        listViewRatings.setItems(FXCollections.observableArrayList(RatingDatabase.getInstance().getRatingsBetweenDatesByLocatation(idLocation, fromDate, toDate)));
        fillChartRatingsBetweenDates(fromDate, toDate);
        
        criteria = criteria.Date;
    }
    
    void fillRatingsByGrade(int grade)
    {
        listViewRatings.getItems().clear();
        listViewRatings.setItems(FXCollections.observableArrayList(RatingDatabase.getInstance().getRatingsByGradeAndLocation(idLocation, grade)));
        
        criteria = criteria.Grade;
    }
    
    private void fillChartRatings(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Sehr Gut", countRatings(1)),
                new PieChart.Data("Gut", countRatings(2)),
                new PieChart.Data("Befriedigend", countRatings(3)),
                new PieChart.Data("Genügend", countRatings(4)),
                new PieChart.Data("Nicht genügend", countRatings(5)));

        pieChartRatings.setData(pieChartData);
    }
    
    private void fillChartRatingsBetweenDates(Date fromDate, Date toDate) throws ParseException{
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Sehr Gut", countRatingsBetweenDates(1, fromDate, toDate)),
                new PieChart.Data("Gut", countRatingsBetweenDates(2, fromDate, toDate)),
                new PieChart.Data("Befriedigend", countRatingsBetweenDates(3, fromDate, toDate)),
                new PieChart.Data("Genügend", countRatingsBetweenDates(4, fromDate, toDate)),
                new PieChart.Data("Nicht genügend", countRatingsBetweenDates(5, fromDate, toDate)));

        pieChartRatings.setData(pieChartData);
    }
    
    private int countRatings(int grade)
    {
        int count = -1;
        
        count = RatingDatabase.getInstance().getCountOfGrade(idLocation, grade);
       
        return count;
    }
    
    private int countRatingsBetweenDates(int grade, Date fromDate, Date toDate) throws ParseException
    {
        int count = -1;
        
        count = RatingDatabase.getInstance().getCountOfGradeBetweenDates(idLocation, grade, fromDate, toDate);
        
        return count;
    }
    
    @FXML
    void onAction_btnFilterRatings(ActionEvent event) throws Exception {
        System.out.println("Filter Ratings");
        
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

        if (txtFromDate.getText().compareTo("")!=0 && txtToDate.getText().compareTo("")!=0)
        {
            Date fromDate = null;
            Date toDate = null;
            
            try
            {
                fromDate = format.parse(txtFromDate.getText());
                toDate = format.parse(txtToDate.getText());
                
                fillRatingsBetweenDates(fromDate, toDate);
        
                lblInfoMessageRatings.setText("Bewertungen nach Datum gefiltert!");
            }
            
            catch(Exception e)
            {
                lblInfoMessageRatings.setText("Es muss ein passendes Datum eingegeben werden, Format: [Tag].[Monat].[Jahr]");
            } 
        }
        
        else
            lblInfoMessageRatings.setText("Die Datum-Felder müssen Werte enthalten!");
        
        
        
    }

    @FXML
    void onAction_btnGenerateCSVFile(ActionEvent event) throws FileNotFoundException, ParseException {
        System.out.println("Generate CSV File");
        
        FileChooser fileChooser = new FileChooser();
  
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV-Files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);


        if(file != null){
            try {
                generateCSVFileFromRatings(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RatingGUIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(RatingGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        generateCSVFileFromRatings(file);
    }
    
    @FXML
    void onMouseClicked_pieChartRatings(MouseEvent event) {
        System.out.println("Clicked!");
    }

    @FXML
    void onAction_cmbGradeFilter(ActionEvent event) throws ParseException {
        System.out.println(cmbGradeFilter.getSelectionModel().getSelectedItem().toString());
        String selectedItem = cmbGradeFilter.getSelectionModel().getSelectedItem().toString();
        
        switch(selectedItem)
        {
            case "Alle":
            {
                fillRatings();
                
                criteria = criteria.Normal;
            }
            break;
            
            case "Sehr Gut":
            {
                fillRatingsByGrade(1);
            }
            break;
               
            case "Gut":
            {
                fillRatingsByGrade(2);
            }
            break;
            
            case "Befriedigend":
            {
                fillRatingsByGrade(3);
            }
            break;
            
            case "Genügend":
            {
                fillRatingsByGrade(4);
            }
            break;
            
            case "Nicht Genügend":
            {
                fillRatingsByGrade(5);
            }
            break;
        }
        
        lblInfoMessageRatings.setText("Bewertungen nach Note gefiltert!");
    }
    
    private void generateCSVFileFromRatings(File file) throws FileNotFoundException, ParseException
    {
        Vector<Rating> vecRatings = new Vector<Rating>();
        
        switch(criteria)
        {
            case Normal:
            {
                vecRatings = RatingDatabase.getInstance().getRatingsByLocation(idLocation);
            }
            break;
            
            case Date:
            {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                Date fromDate = format.parse(txtFromDate.getText());
                Date toDate = format.parse(txtToDate.getText());
                
                vecRatings = RatingDatabase.getInstance().getRatingsBetweenDatesByLocatation(idLocation, fromDate, toDate);
            }
            break;
            
            case Grade:
            {
                vecRatings = RatingDatabase.getInstance().getRatingsByGradeAndLocation(idLocation, (cmbGradeFilter.getSelectionModel().getSelectedIndex()));
            }
            break;
        }
        
        
        PrintWriter pw = new PrintWriter(file);
        StringBuilder sb = new StringBuilder();
        sb.append("Note");
        sb.append(';');
        sb.append("Feedback");
        sb.append(';');
        sb.append("Datum");
        sb.append(';');
        sb.append("Benutzername");
        sb.append(';');
        sb.append("Location-Name");
        sb.append('\n');
        
        
        
        for (Rating r : vecRatings)
        {
            sb.append(Integer.toString(r.getGrade()) + ";");
            sb.append(r.getFeedback() + ";");
            sb.append(r.getRatingDate().toString() + ";");
            sb.append((UserAccountDatabase.getInstance().getUserAccount(r.getIdUserAccount()).getUsername()) + ";");
            sb.append(LocationDatabase.getInstance().getLocation(r.getIdLocation()).getLocationname() + ";");
            sb.append('\n');
        }
        
        pw.write(sb.toString());
        pw.close();
        
        lblInfoMessageRatings.setText("CSV-File erstellt!");
    }
    
}