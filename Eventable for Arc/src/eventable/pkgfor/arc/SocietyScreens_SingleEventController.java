/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.ARCSocietyHomeController.loggedInUser;
import static eventable.pkgfor.arc.ARCSocietyHomeController.societyID;
import static eventable.pkgfor.arc.ARCSocietyHomeController.statement;
import static eventable.pkgfor.arc.DBController.openConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SocietyScreens_SingleEventController extends Application implements Initializable {
    
    Stage stage;
    Parent root;
    
    @FXML
    private Circle societyPage;
    @FXML
    private Text email;
    @FXML
    private TextField eventName;
    private DatePicker startDate;
    private TextField startTime;
    private DatePicker endDate;
    private TextField endTime;
    private TextField streetNo;
    private TextField streetName;
    private TextField buildingName;
    private TextField suburb;
    private TextField postcode;
    private TextField roomNo;
    private TextField eventDescription;
    @FXML
    private Text errorText;
    private ComboBox eventType;
    
    public static Connection conn;

    public String currentQuery;
    
    public String currentQuery1;

    public static ResultSet rs;

    public static Statement statement;
    
    public Integer radioButtonChoice;
    private String currentQuery2;
    private ObservableList<Events> eventTypeData;
    private int maxEventID;
    private int newMaxEventID;
    private String currentQuery3;
    private int maxBuildingID;
    private int newMaxBuildingID;
    private String eventTypeWording;
    private String currentQuery4;
    private int societyIDValue;
    private String currentQuery5;
    @FXML
    private Button backButton;
    @FXML
    private TextField averageRating;
    @FXML
    private Text percentageTotalYes;
    @FXML
    private Text totalYes;
    @FXML
    private Text percentageTotalNo;
    @FXML
    private Text totalNo;
    @FXML
    private TableView<?> tableOfEventsFeedbackQ;
    @FXML
    private TableColumn<?, ?> eventNameAll;
    @FXML
    private TableColumn<?, ?> startDateAll;
    @FXML
    private TableColumn<?, ?> locationTypeAll;
    @FXML
    private TableColumn<?, ?> eventTypeAll;
       
    private void select0(MouseEvent event) {
        radioButtonChoice = 0;
    }
    
    private void select1(MouseEvent event) {
        radioButtonChoice = 1;
    }
    private void setComboBoxValue(MouseEvent event) {
        eventType.setValue(stage);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populatingComboBox();
            populateEmail();
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreens_SingleEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void populateEmail() {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }
    
    private void createEventButton (MouseEvent event) throws SQLException {
        if (validateFields()) {
            loadNext("SocietyScreensEvents.fxml");
        }
    }
    
    @FXML
    private void cancelButton (MouseEvent event) {
            loadNext("SocietyScreensEvents.fxml");
    }
       
    public void loadNext(String destination) {
        stage = (Stage) societyPage.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setError(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisible(true);
    }
    
    public int getEventID() throws SQLException {
        statement = openConnection();
        currentQuery2 = "SELECT event_id FROM event ORDER BY event_id DESC";
        ResultSet rs = statement.executeQuery(currentQuery2);
        while (rs.next()) {
            maxEventID = rs.getInt(1);
            break;
        }
        newMaxEventID = maxEventID + 1;
        return newMaxEventID;
    }
    
    public int getBuildingID() throws SQLException {
        statement = openConnection();
        currentQuery3 = "SELECT CAST(building_id as INT) building_id FROM campus ORDER BY building_id DESC";
        ResultSet rs1 = statement.executeQuery(currentQuery3);
        while (rs1.next()) {
            maxBuildingID = rs1.getInt(1);
            break;
        }
        newMaxBuildingID = maxBuildingID + 1;
        return newMaxBuildingID;
    }
    
    public int getSocietyID() throws SQLException {
        statement = openConnection();
        currentQuery5 = "SELECT society_id FROM APP_USER WHERE EMAIL = lower('" + loggedInUser + "')";
        ResultSet rs2 = statement.executeQuery(currentQuery5);
        while (rs2.next()) {
            societyIDValue = rs2.getInt(1);
            break;
        }
        return societyIDValue;
    }
        
   public Boolean validateFields() throws SQLException {
        errorText.setVisible(false);

        if (Utils.extractStringIsEmpty(eventName)) {
            setError("Event name cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(startTime)) {
            setError("Start time cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(endTime)) {
            setError("End time cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(eventDescription)) {
            setError("Event Description cannot be empty");
            return false;
        }
        
        int maxEventID = getEventID();
        int maxBuildingID = getBuildingID();
        int societyIDCurrentValue = getSocietyID();
        
        if (radioButtonChoice == 0) {
            statement = openConnection();
//            System.out.print("Entered radiobutton = 0 method");
            currentQuery4 = "INSERT INTO CAMPUS VALUES('" + maxBuildingID + "', '" + buildingName.getText() + "', '" + roomNo.getText() + "')";
            int update2 = statement.executeUpdate(currentQuery4);
            String startDateString = startDate.getValue().toString() + " " + startTime.getText();
            String endDateString = endDate.getValue().toString() + " " + endTime.getText();
            currentQuery = "INSERT INTO event(event_id, event_title, location_type, event_text, event_start, event_end, society_id, event_type, building_id, room_no) VALUES(" + maxEventID + ", '" + eventName.getText() + "', 'On Campus', '" + eventDescription.getText() + "', " + "TO_TIMESTAMP('" + startDateString + "','yyyy/mm/dd hh24:mi:ss')" + ", " + "TO_TIMESTAMP('" + endDateString + "','yyyy/mm/dd hh24:mi:ss'), " + societyIDCurrentValue + ", '" + eventTypeWording + "', '" + maxBuildingID + "', '" + roomNo.getText() + "')";
            System.out.print(currentQuery);
            int update = statement.executeUpdate(currentQuery);
            return true;
}
        else if (radioButtonChoice == 1) {
            statement = openConnection();
//            System.out.print("Entered radiobutton = 1 method");
            String startDateString = startDate.getValue().toString() + " " + startTime.getText();
            String endDateString = endDate.getValue().toString() + " " + endTime.getText();
            currentQuery1 = "INSERT INTO event(event_id, event_title, location_type, event_text,"
                    + " event_start, event_end, street_no, street_name, postcode, suburb, society_id, event_type)"
                    + " VALUES(" + maxEventID + ", '" + eventName.getText() + "', 'Off Campus', '" + eventDescription.getText()
                    + "', " + "TO_TIMESTAMP('" + startDateString + "','yyyy/mm/dd hh24:mi:ss')"
                    + ", " + "TO_TIMESTAMP('" + endDateString + "','yyyy/mm/dd hh24:mi:ss'), "
                    + streetNo.getText() + ", '" + streetName.getText() + "', '" + postcode.getText()+ "', '" +
                    suburb.getText() + "', " + societyIDCurrentValue + ", '" + eventTypeWording + "')";
            System.out.print(currentQuery1);
            int update1 = statement.executeUpdate(currentQuery1);
            return true;
        }
        return false;
   }
    
        public void populatingComboBox() throws SQLException {
        statement = openConnection();
        currentQuery2 = "SELECT distinct event_type FROM event";
        ResultSet rs = statement.executeQuery(currentQuery2);

        eventTypeData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                eventTypeData.add(new Events(rs.getString(i)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreens_SingleEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Format the strings in the ComboBox
        eventType.setConverter(new StringConverter<Events>(){
            public String toString(Events object) {
                eventTypeWording = object.getEventType();
                return object.getEventType();
            }

            @Override
            public Events fromString(String string) {
                return null;
            }
        });
        
        //Data added to comboBox
        try {
            eventType.setItems(eventTypeData);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }
            
    public void start(Stage primaryStage) throws Exception {
        stage = (Stage) societyPage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void tableviewItemAllClicked(MouseEvent event) {
    }
}