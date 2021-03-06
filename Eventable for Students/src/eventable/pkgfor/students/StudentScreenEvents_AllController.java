/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.LoginController.conn;
import static eventable.pkgfor.students.LoginController.rs;
import static eventable.pkgfor.students.LoginController.statement;
import static eventable.pkgfor.students.LoginController.userInSystem;
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
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenEvents_AllController extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Text society;
    @FXML
    private TextField searchField;
    @FXML
    private Button aToZButton;
    @FXML
    private Button applyButton;
    @FXML
    private ComboBox bySociety;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private Hyperlink today;
    @FXML
    private Text code;
    @FXML
    private Text events;
    @FXML
    private Text feedback;
    @FXML
    private Text profile;
    @FXML
    private Text all;
    @FXML
    private Text favourites;
    @FXML
    private Text going;
    @FXML
    private Text past;
    @FXML
    public TableView<Events> tableofEvents;
    @FXML
    public TableColumn<Events, String> event;
    @FXML
    public TableColumn<Events, String> startDate;
    @FXML
    public TableColumn<Events, String> location;

    Date currentDate;

    ObservableList<Events> eventsData;
    ObservableList<FavouriteSocieties> listOfSocieties;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;

    public static String eventName;
    public static String eventLocation;
    public static String eventStartDate;
    public static String eventEndDate;
    public static String eventSocietyName;
    public static String eventText;
    public static String eventId;
    private boolean aToZ = true;
    private String bySocietyFilterChoice;
    private boolean filtersApplied = false;

    public void populateTableView() throws SQLException {
        //Only display events that are in the future
        statement = openConnection();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        currentDate = new Date();
        currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE EVENT_START >= '05/MAY/2018'";
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
            tableofEvents.setItems(eventsData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateTableView();
            populateComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void bottomNavSocietyButton(MouseEvent event) throws SQLException {
        loadNext("StudentScreenSociety_All.fxml");
    }

    @FXML
    private void bottomNavCodeButton(MouseEvent event) throws SQLException {
        if (userInSystem) {
            loadNext("StudentScreenCode.fxml");
        }
    }

    @FXML
    private void bottomNavEventsButton(MouseEvent event) throws SQLException {
        loadNext("StudentScreenEvents_All.fxml");
    }

    @FXML
    private void bottomNavFeedbackButton(MouseEvent event) throws SQLException {
        if (userInSystem) {
            loadNext("StudentScreenFeedback_Feedback.fxml");
        }
    }

    @FXML
    private void bottomNavProfileButton(MouseEvent event) throws SQLException {
        if (userInSystem) {
            loadNext("StudentScreenProfile.fxml");
        }
    }

    @FXML
    private void topNavAll(MouseEvent event) throws SQLException {
        loadNext("StudentScreenEvents_All.fxml");
    }

    @FXML
    private void topNavFavourites(MouseEvent event) throws SQLException {
        loadNext("StudentScreenEvents_Favourites.fxml");
    }

    @FXML
    private void topNavGoing(MouseEvent event) throws SQLException {
        loadNext("StudentScreenEvents_Going.fxml");
    }

    @FXML
    private void topNavPast(MouseEvent event) throws SQLException {
        loadNext("StudentScreenEvents_Past.fxml");
    }

    @FXML
    private void search(KeyEvent event) throws SQLException {
        populateTableView("SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, "
                + "STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, "
                + "CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) "
                + "LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE EVENT_START >= '13/MAY/2018' AND (lower(event_title) "
                + "LIKE '%" + searchField.getText().trim().toLowerCase() + "%' OR lower(event_text) LIKE '%"
                + searchField.getText().trim().toLowerCase() + "')");
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
                    + "LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) WHERE event_title LIKE '%' ";

            //Checks society combo box
            if (!(bySociety.getValue() == null)) {
                currentQuery += " AND society_name = '" + bySocietyFilterChoice + "'";
            }

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
            populateTableView();
            filtersApplied = false;
            applyButton.setText("Apply");
            searchField.setText("");
        }
    }

    @FXML
    private void tableviewItemClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            Events eventSelected = tableofEvents.getSelectionModel().getSelectedItem();
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

    public void loadNext(String destination) {
        stage = (Stage) society.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        populateTableView();
        stage = (Stage) society.getScene().getWindow();
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
            tableofEvents.setItems(eventsData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}

    }

    private void populateComboBox() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT society_name, society_description, society_id FROM SOCIETY";
        ResultSet rs = statement.executeQuery(currentQuery);

        listOfSocieties = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                listOfSocieties.add(new FavouriteSocieties(rs.getString(i), rs.getString(i + 1), Integer.parseInt(rs.getString(i + 2))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Format the strings in the ComboBox
        bySociety.setConverter(new StringConverter<FavouriteSocieties>() {
            @Override
            public String toString(FavouriteSocieties object) {
                bySocietyFilterChoice = object.getSocietyName();
                return object.getSocietyName();
            }

            @Override
            public FavouriteSocieties fromString(String string) {
                return null;
            }
        });

        //Data added to comboBox
        try {
            bySociety.setItems(listOfSocieties);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }
}
