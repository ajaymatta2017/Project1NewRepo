/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.DBController.openConnection;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventEndDateDay;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventEndDateMonth;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventEndDateMonthInteger;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventEndDateYear;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventStartDateDay;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventStartDateMonth;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventStartDateMonthInteger;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventStartDateYear;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventStartTime;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventTypeValue;
import static eventable.pkgfor.arc.SocietyScreens_SingleEventController.statement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class SocietyScreens_UpdateSingleEventController implements Initializable {

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
    private ComboBox<Events> eventType;
    @FXML
    private TextField postcode;
    @FXML
    private TextField roomNo;
    @FXML
    private TextField eventDescription;
    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton;
    @FXML
    private Text errorText;
    public static String eventTypeArray[] = {"", "", ""};

    public int locationTypeInt;
    private String currentQuery2;
    private ObservableList<Events> eventTypeData;
    private String eventTypeWording;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateEmail();
        try {
            try {
                populatingComboBox();
            } catch (SQLException ex) {
                Logger.getLogger(SocietyScreens_UpdateSingleEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
            displayUserData();
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreens_UpdateSingleEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (locationTypeInt == 0) {
            select0();
        } else {
            select1();
        }
    }

    @FXML
    private void populateEmail() {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }

    @FXML
    private void select0() {
        onCampus.setSelected(true);
        offCampus.setSelected(false);
    }

    @FXML
    private void select1() {
        offCampus.setSelected(true);
        onCampus.setSelected(false);
    }

    @FXML
    private void cancelButton(MouseEvent event) {
        eventName.clear();
        eventDescription.clear();
//        System.out.println("cleared values");
        roomNo.clear();
        buildingName.clear();
        streetNo.clear();
        streetName.clear();
        suburb.clear();
        postcode.clear();
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

    @FXML
    private void updateEventButton(MouseEvent event) {
    }

    public void displayUserData() throws SQLException {
        eventName.setText(SocietyScreensEventsController.eventName);
        eventDescription.setText(SocietyScreensEventsController.eventText);
        //eventType.setValue();
        System.out.println(SocietyScreensEventsController.eventLocationType);
        if (SocietyScreensEventsController.eventLocationType.matches("On Campus")) {
//            System.out.println("Enter Oncampus Address Details");
            locationTypeInt = 0;
            roomNo.setText(SocietyScreensEventsController.eventRoomNo);
            buildingName.setText(SocietyScreensEventsController.eventBuildingName);
        } else {
            locationTypeInt = 1;
            streetNo.setText(SocietyScreensEventsController.eventStreetNo);
            streetName.setText(SocietyScreensEventsController.eventStreetName);
            suburb.setText(SocietyScreensEventsController.eventSuburb);
            postcode.setText(SocietyScreensEventsController.eventPostcode);
        }
        startDate.setValue(LocalDate.of(eventStartDateYear, eventStartDateMonth, eventStartDateDay));
        endDate.setValue(LocalDate.of(eventEndDateYear, eventEndDateMonth, eventEndDateDay));
        startTime.setText(SocietyScreensEventsController.eventStartTime);
        endTime.setText(SocietyScreensEventsController.eventEndTime);

        statement = openConnection();
        currentQuery2 = "SELECT distinct event_type FROM event";
        ResultSet rs3 = statement.executeQuery(currentQuery2);
        int j = 0;
        try {
            while (rs3.next()) {
                int i = 1;
                eventTypeArray[j] = rs3.getString(i);
//                System.out.println(eventTypeArray[j]);
                j++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreens_UpdateSingleEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int k = 0; k < eventTypeArray.length; k++) {
            if (SocietyScreensEventsController.eventTypeValue.matches(eventTypeArray[k])) {
                eventType.getSelectionModel().select(k);
                break;
            }
        }
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
            Logger.getLogger(SocietyScreens_UpdateSingleEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        eventType.setConverter(new StringConverter<Events>() {
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
    }
}
