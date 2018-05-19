/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventEndDate;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventId;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventLocation;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventName;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventSocietyName;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventStartDate;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventText;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenEvent_SingleEvent_OtherController implements Initializable {

    @FXML
    Stage stage;
    Parent root;
    
    @FXML
    private Text society;
    @FXML
    private Text code;
    @FXML
    private Text events;
    @FXML
    private Text feedback;
    @FXML
    private Text profile;
    @FXML
    private Text eventTopNav;
    @FXML
    private Text other;
    @FXML
    private Button backButton;
    @FXML
    private Text societyText;
    @FXML
    private TextField searchField;
    @FXML
    private Button aToZButton;
    @FXML
    private Button applyButton;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private Hyperlink today;

    @FXML
    public TableView<Events> tableOfEvents_SingleOther;
    @FXML
    public TableColumn<Events, String> event;
    @FXML
    public TableColumn<Events, String> startDate;
    @FXML
    public TableColumn<Events, String> location;
    ObservableList<Events> eventsData;
    
    Date currentDate;
    
    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;
    private boolean aToZ = true;
    private boolean filtersApplied = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            societyText.setText(StudentScreenEvents_AllController.eventSocietyName);
            populateTableView("SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE EVENT_START >= '22/MAY/2018' AND society_name = '" + StudentScreenEvents_AllController.eventSocietyName + "'");
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvent_SingleEvent_OtherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void bottomNavSocietyButton(MouseEvent event) {
        loadNext("StudentScreenSociety_All.fxml");
    }

    @FXML
    private void bottomNavCodeButton(MouseEvent event) {
        loadNext("StudentScreenCode.fxml");
    }

    @FXML
    private void bottomNavEventsButton(MouseEvent event) {
        loadNext("StudentScreenEvents_All.fxml");
    }

    @FXML
    private void bottomNavFeedbackButton(MouseEvent event) {
        loadNext("StudentScreenFeedback_Feedback.fxml");
    }

    @FXML
    private void bottomNavProfileButton(MouseEvent event) {
        loadNext("StudentScreenProfile.fxml");
    }

    @FXML
    private void topNavEvent(MouseEvent event) {
        loadNext("StudentScreenEvent_SingleEvent.fxml");
    }

    @FXML
    private void topNavOther(MouseEvent event) {
        loadNext("StudentScreenEvent_SingleEvent_Other.fxml");
    }

    @FXML
    private void topNavBackButton(MouseEvent event) {
        loadNext("StudentScreenEvents_All.fxml");
    }
    
    public void loadNext(String destination){
        stage=(Stage) society.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination)); //putting it to 'Seek a Ride' for now, before we know what type of user each person is
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void populateTableView(String currentQuery) throws SQLException {
        //Only display events that are in the future
        statement = openConnection();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        currentDate = new Date();
        ResultSet rs = statement.executeQuery(currentQuery);

        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        location.setCellValueFactory(new PropertyValueFactory<>("locationType"));

        //Data added to observable List
        eventsData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                eventsData.add(new Events(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2), rs.getString(i + 3), rs.getString(i + 4),
                        rs.getString(i + 5), rs.getString(i + 6), rs.getString(i + 7), rs.getString(i + 8), rs.getString(i + 9),
                        rs.getString(i + 10), rs.getString(i + 11), rs.getString(i + 12), rs.getString(i + 13), rs.getString(i + 14), Integer.parseInt(rs.getString(i + 15))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }

        event.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(event.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        startDate.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(startDate.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        location.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(location.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableOfEvents_SingleOther.setItems(eventsData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}

    }   
    
    @FXML
    private void search(KeyEvent event) throws SQLException {
        populateTableView("SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, "
                + "STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, "
                + "CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) "
                + "LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE EVENT_START >= '22/MAY/2018' AND society_name = '"
                + StudentScreenEvents_AllController.eventSocietyName + "' AND (lower(event_title) LIKE '%" + searchField.getText().trim().toLowerCase()
                + "%' OR lower(event_text) LIKE '%" + searchField.getText().trim().toLowerCase() + "')");
    }
    
    @FXML
    private void today (ActionEvent event) throws SQLException {
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
    }

    @FXML
    private void alphabeticalSort(ActionEvent event) throws SQLException {
        if (!aToZ) {
            aToZ = true;
            aToZButton.setText("A-Z");
        } else {
            aToZ = false;
            aToZButton.setText("Z-A");
        }
    }

    @FXML
    private void applyFilters() throws SQLException {
        if (!filtersApplied) {
            currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, "
                    + "STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, "
                    + "CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), "
                    + "CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) "
                    + "LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE society_name = '" + StudentScreenEvents_AllController.eventSocietyName + "'";

            //Checks datepicker objects
            if (!(dateFrom.getValue() == null)) {
                currentQuery += " AND event_start >= '" + dateFrom.getValue().format(DateTimeFormatter.ofPattern("d/MMM/yyyy")) + "'";
            }
            if (!(dateTo.getValue() == null)) {
                currentQuery += " AND event_end <= '" + dateTo.getValue().format(DateTimeFormatter.ofPattern("d/MMM/yyyy")) + "'";
            }

            //Appends the a-z classifier
            if (aToZButton.getText().toLowerCase().equals("a-z")) {
                currentQuery += " ORDER BY event_title ASC";
            } else {
                currentQuery += " ORDER BY event_title DESC";
            }
            populateTableView(currentQuery);
            filtersApplied = true;
            applyButton.setText("Clear");
            searchField.setText("");
        }
        else {
            populateTableView("SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE EVENT_START >= '22/MAY/2018' AND society_name = '" + StudentScreenEvents_AllController.eventSocietyName + "'");
            filtersApplied = false;
            applyButton.setText("Apply");
            searchField.setText("");
        }
    }
    
    @FXML
    private void tableviewItemClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            Events eventSelected = tableOfEvents_SingleOther.getSelectionModel().getSelectedItem();
            eventName = eventSelected.getEvent();
            String eventLocationType = eventSelected.getLocationType();
            if (eventLocationType.equals("On Campus")) {
                eventLocation = eventSelected.getRoomNo() + " " + eventSelected.getBuildingName();
            } else {
                eventLocation = eventSelected.getStreetNo() + " " + eventSelected.getStreetName() + ", " + eventSelected.getSuburb() + ", "
                        + eventSelected.getPostcode();
            }
            eventStartDate = eventSelected.getStartDate() + " " + eventSelected.getEventStartTime();
            eventEndDate = eventSelected.getEventEnd() + " " + eventSelected.getEventEndTime();
            eventSocietyName = eventSelected.getSocietyName();
            eventText = eventSelected.getEventText();
            eventId = eventSelected.getId();
            System.out.println(eventName + "..." + eventLocation + "..." + eventStartDate + "..." + eventSocietyName + "..." + eventSelected.getLocationType());
            loadNext("StudentScreenEvent_SingleEvent.fxml");
        }
    }
}
