/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.ARCSocietyHomeController.emailAddress;
import java.util.Random;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SocietyScreensEventsController extends Application implements Initializable {

    @FXML
    Stage stage;
    @FXML
    Parent root;
    @FXML
    private TextField societyName;
    @FXML
    private TableView<Events> tableOfEventsAll;
    @FXML
    private TableColumn<Events, String> eventNameAll;
    @FXML
    private TableColumn<Events, String> startDateAll;
    @FXML
    private TableColumn<Events, String> locationTypeAll;
    @FXML
    private TableColumn<Events, String> eventTypeAll;

    @FXML
    private TextField societyName1;
    @FXML
    private TableView<Events> tableOfEventsUpcoming;
    @FXML
    private TableColumn<Events, String> eventNameUpcoming;
    @FXML
    private TableColumn<Events, String> startDateUpcoming;
    @FXML
    private TableColumn<Events, String> locationTypeUpcoming;
    @FXML
    private TableColumn<Events, String> eventTypeUpcoming;

    @FXML
    private TextField societyName2;
    @FXML
    private TextField societyName3;

    @FXML
    private TableView<Events> tableOfEventsPast;
    @FXML
    private TableColumn<Events, String> eventNamePast;
    @FXML
    private TableColumn<Events, String> startDatePast;
    @FXML
    private TableColumn<Events, String> locationTypePast;
    @FXML
    private TableColumn<Events, String> eventTypePast;
    @FXML
    private TableView<Codes> tableOfEventsCodes;
    @FXML
    private TableColumn<Codes, String> societyNameCodes;
    @FXML
    private TableColumn<Codes, String> eventNameCodes;
    @FXML
    private TableColumn<Codes, String> emailCodes;

    @FXML
    private Text email;
    @FXML
    public TextField societyName4;
    @FXML
    private Text newEvent;

    @FXML
    private Circle societyPage;

    public String currentQuery;

    public String currentQuery1;

    public String currentQuery2;

    public String currentQuery5;

    private String currentQuery3;
    private String currentQuery4;

    ObservableList<Events> eventsData;
    ObservableList<Events> eventsDataUpcoming;
    ObservableList<Events> eventsDataPast;
    ObservableList<Codes> eventsCodeData;

    public static Connection conn;

    public static ResultSet rs;

    public static Statement statement;

    @FXML
    private Button editAccountButton;

    @FXML
    public TextField societyDescription;

    @FXML
    private DatePicker startDate;
    @FXML
    private TextField startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField endTIme;
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

    private String currentQuery6;
    private ObservableList<Events> eventsDataDisplayAll;

    @FXML
    private Text newEvent1;
    @FXML
    private Text newEvent2;
    @FXML
    private Text newEvent3;
//    private ObservableList<Object> eventsDataAllColumns;
    public static String eventName;
    public static String eventLocation;
    public static String eventStartDate;
    public static String eventEndDate;
    public static String eventText;
    public static String eventId;
    public static String eventSocietyName;
    public static String eventRoomNo;
    public static String eventBuildingName;
    public static String eventStreetNo;
    public static String eventStreetName;
    public static String eventSuburb;
    public static String eventPostcode;
    public static String eventStartTime;
    public static String eventEndTime;
    public static String eventLocationType;
    public static String eventTypeValue;
    public static int eventStartDateDay;
    public static int eventStartDateMonth;
    public static int eventStartDateYear;
    public static int eventEndDateDay;
    public static int eventEndDateMonth;
    public static int eventEndDateYear;
    public static int eventStartDateMonthInteger;
    public static int eventEndDateMonthInteger;

    private Random rand = new Random();
    
    //Input variables
    @FXML
    private AnchorPane inputPane;
    @FXML
    private TextField codeInput;
    @FXML
    private Text errorMessage;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statement = openConnection();

        try {
            System.out.println("Method0");
            populateSocietyPageTitle();
            System.out.println("Method1");
            populateEmail();
            System.out.println("Method2");
            populateTableViewAll();
            System.out.println("Method3");
            populateTableViewUpcoming();
            System.out.println("Method4");
            populateTableViewCodes();
            System.out.println("Method5");
            populateTableViewPast();
            System.out.println("Method6");
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createNewEvent(MouseEvent event) throws SQLException {
        addNewEvent("SocietyScreen_SingleEvent.fxml");
    }

    @FXML
    private void createNewEvent1(MouseEvent event) throws SQLException {
        addNewEvent1("SocietyScreen_SingleEvent.fxml");
    }

    @FXML
    private void createNewEvent2(MouseEvent event) throws SQLException {
        addNewEvent2("SocietyScreen_SingleEvent.fxml");
    }

    @FXML
    private void createNewEvent3(MouseEvent event) throws SQLException {
        addNewEvent3("SocietyScreen_SingleEvent.fxml");
    }
    
//    @FXML
//    private void updateSocietyAccountNavigation(MouseEvent event) throws SQLException {
//            updateSocietyPage("SocietyScreens_SingleSociety.fxml");
//    }
//    
    public void populateSocietyPageTitle() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT UPPER(society_name), society_description from society JOIN app_user USING (society_id) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "')";
        System.out.println(currentQuery);
        ResultSet rs = statement.executeQuery(currentQuery);
        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            societyName.setText(rs.getString(1));
            societyName1.setText(rs.getString(1));
            societyName2.setText(rs.getString(1));
            societyName3.setText(rs.getString(1));
            societyName4.setText(rs.getString(1));
            societyDescription.setText(rs.getString(2));
        }
    }

    @FXML
    public void populateEmail() throws SQLException {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }

    public void populateTableViewCodes() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT society_name, event_title, a.email FROM app_user JOIN society USING(SOCIETY_ID) JOIN event USING(SOCIETY_ID) JOIN attendance a USING(EVENT_ID) WHERE UPPER(society_name) = '" + societyName4.getText() + "'" + " AND event_theoretical_attendance = 'Y'";
        ResultSet rs = statement.executeQuery(currentQuery);
        societyNameCodes.setCellValueFactory(new PropertyValueFactory<>("society"));
        eventNameCodes.setCellValueFactory(new PropertyValueFactory<>("event"));
        emailCodes.setCellValueFactory(new PropertyValueFactory<>("email"));

        //Data added to observable List
        eventsCodeData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                eventsCodeData.add(new Codes(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        societyNameCodes.setCellFactory(tc -> {
            TableCell<Codes, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyNameCodes.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        eventNameCodes.setCellFactory(tc -> {
            TableCell<Codes, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventNameCodes.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        emailCodes.setCellFactory(tc -> {
            TableCell<Codes, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(emailCodes.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableOfEventsCodes.setItems(eventsCodeData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    public void populateTableViewAll() throws SQLException {
        statement = openConnection();
        //currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "')";
        currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/mm/yyyy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, CAST(TO_CHAR(EVENT_END, 'dd/mm/yyyy') AS VARCHAR2(50)), cast(to_char(cast(event_end as date),'hh:mi AM') AS VARCHAR2(50)), cast(to_char(cast(event_start as date),'hh:mi AM') AS VARCHAR2(50)), event_text, event_id, event_type FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) join app_user USING(society_id) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "')";
        ResultSet rs = statement.executeQuery(currentQuery);

        eventNameAll.setCellValueFactory(new PropertyValueFactory<>("event"));
        startDateAll.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationTypeAll.setCellValueFactory(new PropertyValueFactory<>("locationType"));
        eventTypeAll.setCellValueFactory(new PropertyValueFactory<>("eventType"));

        //Data added to observable List
        eventsData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                //eventsData.add(new Events(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2), rs.getString(i + 3)));
                eventsData.add(new Events(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2), rs.getString(i + 3), rs.getString(i + 4),
                        rs.getString(i + 5), rs.getString(i + 6), rs.getString(i + 7), rs.getString(i + 8), rs.getString(i + 9),
                        rs.getString(i + 10), rs.getString(i + 11), rs.getString(i + 12), rs.getString(i + 13), rs.getInt(i + 14), rs.getString(i + 15)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        eventNameAll.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventNameAll.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        startDateAll.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(startDateAll.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        locationTypeAll.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(locationTypeAll.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        eventTypeAll.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventTypeAll.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        //Data added to TableView
        try {
            tableOfEventsAll.setItems(eventsData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    public void populateTableViewUpcoming() throws SQLException {
        statement = openConnection();
//        currentQuery1 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start >= '10/MAY/2018'";
        currentQuery1 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/mm/yyyy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, CAST(TO_CHAR(EVENT_END, 'dd/mm/yyyy') AS VARCHAR2(50)), cast(to_char(cast(event_end as date),'hh:mi AM') AS VARCHAR2(50)), cast(to_char(cast(event_start as date),'hh:mi AM') AS VARCHAR2(50)), event_text, event_id, event_type FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) join app_user USING(society_id) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start >= '10/MAY/2018'";
        ResultSet rs1 = statement.executeQuery(currentQuery1);

        eventNameUpcoming.setCellValueFactory(new PropertyValueFactory<>("event"));
        startDateUpcoming.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationTypeUpcoming.setCellValueFactory(new PropertyValueFactory<>("locationType"));
        eventTypeUpcoming.setCellValueFactory(new PropertyValueFactory<>("eventType"));

        //Data added to observable List
        eventsDataUpcoming = FXCollections.observableArrayList();
        try {
            while (rs1.next()) {
                int i = 1;
//                eventsDataUpcoming.add(new Events(rs1.getString(i), rs1.getString(i + 1), rs1.getString(i + 2), rs1.getString(i + 3)));
                eventsDataUpcoming.add(new Events(rs1.getString(i), rs1.getString(i + 1), rs1.getString(i + 2), rs1.getString(i + 3), rs1.getString(i + 4),
                        rs1.getString(i + 5), rs1.getString(i + 6), rs1.getString(i + 7), rs1.getString(i + 8), rs1.getString(i + 9),
                        rs1.getString(i + 10), rs1.getString(i + 11), rs1.getString(i + 12), rs1.getString(i + 13), rs1.getInt(i + 14), rs1.getString(i + 15)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        eventNameUpcoming.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventNameUpcoming.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        startDateUpcoming.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(startDateUpcoming.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        locationTypeUpcoming.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(locationTypeUpcoming.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        eventTypeUpcoming.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventTypeUpcoming.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        //Data added to TableView
        try {
            tableOfEventsUpcoming.setItems(eventsDataUpcoming);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    public void populateTableViewPast() throws SQLException {
        statement = openConnection();
//        currentQuery3 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start <= '10/MAY/2018'";
        currentQuery3 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/mm/yyyy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_ID, BUILDING_NAME, ROOM_NO, CAST(TO_CHAR(EVENT_END, 'dd/mm/yyyy') AS VARCHAR2(50)), cast(to_char(cast(event_end as date),'hh:mi AM') AS VARCHAR2(50)), cast(to_char(cast(event_start as date),'hh:mi AM') AS VARCHAR2(50)), event_text, event_id, event_type FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_ID) join app_user USING(society_id) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start <= '10/MAY/2018'";
        ResultSet rs3 = statement.executeQuery(currentQuery3);

        eventNamePast.setCellValueFactory(new PropertyValueFactory<>("event"));
        startDatePast.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        locationTypePast.setCellValueFactory(new PropertyValueFactory<>("locationType"));
        eventTypePast.setCellValueFactory(new PropertyValueFactory<>("eventType"));

        //Data added to observable List
        eventsDataPast = FXCollections.observableArrayList();
        try {
            while (rs3.next()) {
                int i = 1;
                eventsDataPast.add(new Events(rs3.getString(i), rs3.getString(i + 1), rs3.getString(i + 2), rs3.getString(i + 3), rs3.getString(i + 4),
                        rs3.getString(i + 5), rs3.getString(i + 6), rs3.getString(i + 7), rs3.getString(i + 8), rs3.getString(i + 9),
                        rs3.getString(i + 10), rs3.getString(i + 11), rs3.getString(i + 12), rs3.getString(i + 13), rs3.getInt(i + 14), rs3.getString(i + 15)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        eventNamePast.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventNamePast.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        startDatePast.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(startDatePast.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        locationTypePast.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(locationTypePast.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        eventTypePast.setCellFactory(tc -> {
            TableCell<Events, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventTypePast.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        //Data added to TableView
        try {
            tableOfEventsPast.setItems(eventsDataPast);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    public void addNewEvent(String destination) {
        stage = (Stage) newEvent.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(ARCSocietyHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addNewEvent1(String destination) {
        stage = (Stage) newEvent1.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(ARCSocietyHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addNewEvent2(String destination) {
        stage = (Stage) newEvent2.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(ARCSocietyHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addNewEvent3(String destination) {
        stage = (Stage) newEvent3.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(ARCSocietyHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateSocietyPage(String destination) {
        stage = (Stage) societyPage.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(ARCSocietyHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = (Stage) societyName.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void enableEditAccount(MouseEvent event) {
        if (!societyName4.isEditable()) {
            societyName4.setEditable(true);
            societyDescription.setEditable(true);
            editAccountButton.setText("Save");
        } else {
            societyName4.setEditable(false);
            societyDescription.setEditable(false);
            editAccountButton.setText("Edit Account");
        }
    }

    @FXML
    private void updateAccount(MouseEvent event) throws SQLException {
        currentQuery4 = "UPDATE society SET society_name = '" + societyName4.getText() + "', " + "society_description = '" + societyDescription.getText() + " WHERE society_name = '" + societyName4.getText() + "'";
        System.out.println(currentQuery4);
        int rs4 = statement.executeUpdate(currentQuery4);
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

//    public void displayUserDataAll() throws SQLException {
//        statement = openConnection();
//        currentQuery6 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start <= '10/MAY/2018'";
//        ResultSet rs4 = statement.executeQuery(currentQuery6);
//
//        eventNamePast.setCellValueFactory(new PropertyValueFactory<>("event"));
//        startDatePast.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        locationTypePast.setCellValueFactory(new PropertyValueFactory<>("locationType"));
//        eventTypePast.setCellValueFactory(new PropertyValueFactory<>("eventType"));
//
//        //Data added to observable List
//        eventsDataDisplayAll = FXCollections.observableArrayList();
////        try {
////            while (rs4.next()) {
////                int i = 1;
////                eventsDataPast.add(new Events(rs4.getString(i), rs4.getString(i + 1), rs4.getString(i + 2), rs4.getString(i + 3)));
////            }        
////    }
////    }
    @FXML
    private void tableviewItemAllClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            Events eventSelected = tableOfEventsAll.getSelectionModel().getSelectedItem();
            String eventLocationTypeString = eventSelected.getLocationType();
            System.out.println(eventLocationTypeString);
            if (eventLocationTypeString.matches("On Campus")) {
                eventRoomNo = eventSelected.getRoomNo();
                eventBuildingName = eventSelected.getBuildingName();
            } else {
                eventStreetNo = eventSelected.getStreetNo();
                eventStreetName = eventSelected.getStreetName();
                eventSuburb = eventSelected.getSuburb();
                eventPostcode = eventSelected.getPostcode();
            }
            eventLocationType = eventSelected.getLocationType();
            eventName = eventSelected.getEvent();

            eventStartDate = eventSelected.getStartDate();
            String[] eventStartDateArray = eventStartDate.split("/");
            eventStartDateDay = Integer.parseInt(eventStartDateArray[0]);
            eventStartDateMonth = Integer.parseInt(eventStartDateArray[1]);
            eventStartDateYear = Integer.parseInt(eventStartDateArray[2]);

            eventEndDate = eventSelected.getEventEnd();
            String[] eventEndDateArray = eventEndDate.split("/");
            eventEndDateDay = Integer.parseInt(eventEndDateArray[0]);
            eventEndDateMonth = Integer.parseInt(eventEndDateArray[1]);
            eventEndDateYear = Integer.parseInt(eventEndDateArray[2]);

            eventStartTime = eventSelected.getEventStartTime();
            eventEndTime = eventSelected.getEventEndTime();
            eventText = eventSelected.getEventText();
            eventId = eventSelected.getId();
            eventTypeValue = eventSelected.getEventType();
//            System.out.println(eventStartDateMonth + "..." + eventType + "..." + eventEndDateYear + "...");
            loadNext("SocietyScreen_UpdateSingleEvent.fxml");
        }
    }

    @FXML
    private void tableviewItemUpcomingClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            Events eventSelected = tableOfEventsUpcoming.getSelectionModel().getSelectedItem();
            String eventLocationTypeString = eventSelected.getLocationType();
            System.out.println(eventLocationTypeString);
            if (eventLocationTypeString.matches("On Campus")) {
                eventRoomNo = eventSelected.getRoomNo();
                eventBuildingName = eventSelected.getBuildingName();
            } else {
                eventStreetNo = eventSelected.getStreetNo();
                eventStreetName = eventSelected.getStreetName();
                eventSuburb = eventSelected.getSuburb();
                eventPostcode = eventSelected.getPostcode();
            }
            eventLocationType = eventSelected.getLocationType();
            eventName = eventSelected.getEvent();

            eventStartDate = eventSelected.getStartDate();
            String[] eventStartDateArray = eventStartDate.split("/");
            eventStartDateDay = Integer.parseInt(eventStartDateArray[0]);
            eventStartDateMonth = Integer.parseInt(eventStartDateArray[1]);
            eventStartDateYear = Integer.parseInt(eventStartDateArray[2]);

            eventEndDate = eventSelected.getEventEnd();
            String[] eventEndDateArray = eventEndDate.split("/");
            eventEndDateDay = Integer.parseInt(eventEndDateArray[0]);
            eventEndDateMonth = Integer.parseInt(eventEndDateArray[1]);
            eventEndDateYear = Integer.parseInt(eventEndDateArray[2]);

            eventStartTime = eventSelected.getEventStartTime();
            eventEndTime = eventSelected.getEventEndTime();
            eventText = eventSelected.getEventText();
            eventId = eventSelected.getId();
            eventTypeValue = eventSelected.getEventType();
//            System.out.println(eventStartDateMonth + "..." + eventType + "..." + eventEndDateYear + "...");
            loadNext("SocietyScreen_UpdateSingleEvent.fxml");
        }
    }

    @FXML
    private void tableviewItemPastClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            Events eventSelected = tableOfEventsPast.getSelectionModel().getSelectedItem();
            String eventLocationTypeString = eventSelected.getLocationType();
            System.out.println(eventLocationTypeString);
            if (eventLocationTypeString.matches("On Campus")) {
                eventRoomNo = eventSelected.getRoomNo();
                eventBuildingName = eventSelected.getBuildingName();
            } else {
                eventStreetNo = eventSelected.getStreetNo();
                eventStreetName = eventSelected.getStreetName();
                eventSuburb = eventSelected.getSuburb();
                eventPostcode = eventSelected.getPostcode();
            }
            eventLocationType = eventSelected.getLocationType();
            eventName = eventSelected.getEvent();

            eventStartDate = eventSelected.getStartDate();
            String[] eventStartDateArray = eventStartDate.split("/");
            eventStartDateDay = Integer.parseInt(eventStartDateArray[0]);
            eventStartDateMonth = Integer.parseInt(eventStartDateArray[1]);
            eventStartDateYear = Integer.parseInt(eventStartDateArray[2]);

            eventEndDate = eventSelected.getEventEnd();
            String[] eventEndDateArray = eventEndDate.split("/");
            eventEndDateDay = Integer.parseInt(eventEndDateArray[0]);
            eventEndDateMonth = Integer.parseInt(eventEndDateArray[1]);
            eventEndDateYear = Integer.parseInt(eventEndDateArray[2]);

            eventStartTime = eventSelected.getEventStartTime();
            eventEndTime = eventSelected.getEventEndTime();
            eventText = eventSelected.getEventText();
            eventId = eventSelected.getId();
            eventTypeValue = eventSelected.getEventType();
//            System.out.println(eventStartDateMonth + "..." + eventType + "..." + eventEndDateYear + "...");
            loadNext("SocietyScreen_UpdateSingleEvent.fxml");
        }
    }

    @FXML
    private void codeItemClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) { // double click
            //generate code
            int newCode = rand.nextInt(9999) + 1000;
            currentQuery = "SELECT EVENT_ID FROM EVENT WHERE EVENT_TITLE = '" + tableOfEventsCodes.getSelectionModel().getSelectedItem().event.get() + "'";
            ResultSet rs = statement.executeQuery(currentQuery);
            rs.next();
            int eventID = Integer.parseInt(rs.getString("EVENT_ID"));
            currentQuery = "UPDATE ATTENDANCE SET GENERATED_CODE = " + newCode + " WHERE EMAIL = '" + tableOfEventsCodes.getSelectionModel().getSelectedItem().email.get() + "' AND EVENT_ID = " + eventID;
            rs = statement.executeQuery(currentQuery);
            
            //open input
            inputPane.setVisible(true);
            errorMessage.setVisible(false);
        }
    }
    
    @FXML
    private void closeInputPane(MouseEvent event){
        inputPane.setVisible(false);
    }
    
    @FXML
    private void confClicked (MouseEvent event) throws SQLException{
        //getDBcode
        currentQuery = "SELECT EVENT_ID FROM EVENT WHERE EVENT_TITLE = '" + tableOfEventsCodes.getSelectionModel().getSelectedItem().event.get() + "'";
        ResultSet rs = statement.executeQuery(currentQuery);
        rs.next();
        int eventID = Integer.parseInt(rs.getString("EVENT_ID"));
        currentQuery = "SELECT GENERATED_CODE FROM ATTENDANCE WHERE EMAIL = '" + tableOfEventsCodes.getSelectionModel().getSelectedItem().email.get() + "' AND EVENT_ID = " + eventID;
        rs = statement.executeQuery(currentQuery);
        rs.next();
        if(rs.getString("GENERATED_CODE").equals(codeInput.getText())){
            errorMessage.setVisible(false);
            currentQuery = "UPDATE ATTENDANCE SET EVENT_ACTUAL_ATTENDANCE = 'Y' WHERE EMAIL = '" + tableOfEventsCodes.getSelectionModel().getSelectedItem().email.get() + "' AND EVENT_ID = " + eventID;
            rs = statement.executeQuery(currentQuery);
            inputPane.setVisible(false);
        }
        else{
            errorMessage.setVisible(true);
        }

    }
    
}