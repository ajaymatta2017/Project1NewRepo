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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SocietyScreens_SingleEventController extends Application implements Initializable {
    
    @FXML
    Stage stage;
    @FXML
    Parent root;
    
    @FXML
    private Circle societyPage;
    @FXML
    private TextField email;
    @FXML
    private TextField eventName;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField endTime;
    @FXML
    private RadioButton onCampus;
    @FXML
    private RadioButton offCampus;
    @FXML
    private TextField streetNo;
    @FXML
    private TextField streetName;
    @FXML
    private TextField buildingName;
    @FXML
    private TextField suburb;
    @FXML
    private TextField postcode;
    @FXML
    private TextField roomNo;
    @FXML
    private TextField eventDescription;
    @FXML
    private Button createButton;
    @FXML        
    private Button cancelButton;
    @FXML
    private Text errorText;
    @FXML
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
       
    @FXML
    private void select0(MouseEvent event) {
        radioButtonChoice = 0;
    }
    
    @FXML
    private void select1(MouseEvent event) {
        radioButtonChoice = 1;
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

    @FXML
    private void populateEmail() {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }
    
    @FXML
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
        currentQuery2 = "SELECT event_id FROM event ORDER BY event_id DESC";
        ResultSet rs = statement.executeQuery(currentQuery2);
        while (rs.next()) {
            maxEventID = rs.getInt(1);
            break;
        }
        newMaxEventID = maxEventID + 1;
        
        currentQuery3 = "SELECT building_id FROM campus ORDER BY building_id DESC";
        ResultSet rs1 = statement.executeQuery(currentQuery3);
        while (rs.next()) {
            maxBuildingID = rs1.getInt(1);
            break;
        }
        newMaxBuildingID = maxBuildingID + 1;
        
        statement = openConnection();
        
        //NEED TO TEST
        if (radioButtonChoice == 0) {       
        currentQuery = "INSERT INTO event(event_id, event_title, location_type, event_text, event_start, event_end, society_id, building_id, room_no) VALUES(" + newMaxEventID + ", '" + eventName.getText() + "', '" + onCampus.getText() + "', '" + eventDescription.getText() + "', '" + startDate.getValue().toString() + " " + startTime.getText() + "', '" + endDate.getValue().toString() + " " + endTime.getText() + "', " + societyID + ", '" + eventType.getValue().toString() + "', " + newMaxBuildingID + ", " + roomNo + ")";
        System.out.print(currentQuery);
        int update = statement.executeUpdate(currentQuery);
        return true;
}
        else if (radioButtonChoice == 1) {
        currentQuery1 = "INSERT INTO event(event_id, event_title, location_type, event_text, event_start, event_end, street_no, street_name, postcode, suburb, society_id, event_type) VALUES(" + newMaxEventID + ", '" + eventName.getText() + "', '" + offCampus.getText() + "', '" + eventDescription.getText() + "', '" + startDate.getValue().toString() + " " + startTime.getText() + "', '" + endDate.getValue().toString() + " " + endTime.getText() + "', " + streetNo.getText() + ", '" + streetName.getText() + "', '" + postcode.getText()+ "', '" + suburb.getText() + "', " + societyID + ", '" + eventType.getValue().toString() + "', " + newMaxBuildingID + ", " + roomNo + ")";
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
        
    public void displayEventData() {
        
        
        
    }
    
    public void start(Stage primaryStage) throws Exception {
        stage = (Stage) societyPage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}