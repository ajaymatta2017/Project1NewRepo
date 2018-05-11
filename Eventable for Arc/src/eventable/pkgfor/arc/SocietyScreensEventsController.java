/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.ARCSocietyHomeController.emailAddress;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SocietyScreensEventsController extends Application implements Initializable {
    
    Stage stage;
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
    private TableView<Events> tableOfEventsCodes;
    @FXML
    private TableColumn<Events, String> eventNameCodes;
    @FXML
    private TableColumn<Events, String> startDateCodes;
    @FXML
    private TableColumn<Events, String> locationTypeCodes;
    @FXML
    private TableColumn<Events, String> eventTypeCodes;

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
    private TextField email;
    
    @FXML
    private TextField societyName4;
    
    @FXML
    private Text newEvent;
    
    @FXML
    private Text newEvent1;
    
    @FXML
    private Text newEvent2;
        
    @FXML
    private Circle societyPage;
    
    public String currentQuery;
    
    public String currentQuery1;
    
    public String currentQuery2;
    
    public String currentQuery5;
    
    ObservableList<Events> eventsData;
    ObservableList<Events> eventsDataUpcoming;
    ObservableList<Events> eventsDataPast;
    
    public static Connection conn;

    public static ResultSet rs;

    public static Statement statement;
    
    @FXML
    private Button editAccountButton;
    
    @FXML
    public TextField societyDescription;
    
    private String currentQuery3;
    private String currentQuery4;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateSocietyPageTitle();
            populateEmail();
            populateTableViewAll();
            populateTableViewUpcoming();
            populateTableViewPast();
            displayUserData();
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createNewEvent(MouseEvent event) throws SQLException {
            addNewEvent("SocietyScreens_NewEvent.fxml");
    }
    
    private void updateSocietyAccountNavigation(MouseEvent event) throws SQLException {
            updateSocietyPage("SocietyScreens_SingleSociety.fxml");
    }
    
    @FXML
    public void populateSocietyPageTitle() throws SQLException {
            statement = openConnection();
            currentQuery = "SELECT UPPER(society_name) from society JOIN app_user USING (society_id) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "')";
            ResultSet rs = statement.executeQuery(currentQuery);
            while (rs.next()) {
                societyName.setText(rs.getString(1));
                societyName1.setText(rs.getString(1));
                societyName2.setText(rs.getString(1));
                societyName4.setText(rs.getString(1));
            }
    }
    
    @FXML
    public void populateEmail() throws SQLException {
                email.setText(ARCSocietyHomeController.loggedInUser);
    }
    
    public void populateTableViewAll() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "')";
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
                eventsData.add(new Events(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2), rs.getString(i+3)));
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
        currentQuery1 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start >= '10/MAY/2018'";
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
                eventsDataUpcoming.add(new Events(rs1.getString(i), rs1.getString(i + 1), rs1.getString(i + 2), rs1.getString(i+3)));
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
        currentQuery3 = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE, EVENT_TYPE FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) JOIN APP_USER USING(SOCIETY_ID) WHERE email = lower('" + ARCSocietyHomeController.loggedInUser + "') AND event_start <= '10/MAY/2018'";
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
                eventsDataPast.add(new Events(rs3.getString(i), rs3.getString(i + 1), rs3.getString(i + 2), rs3.getString(i+3)));
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
        displayUserData();
//            populateSocietyPageTitle();
//            populateEmail();
//            populateTableViewAll();
    }

        private void displayUserData() throws SQLException {
        statement = openConnection();
        currentQuery5 = "SELECT society_name, society_description from society WHERE society_name = '" + societyName4.getText() + "'";
        ResultSet rs5 = statement.executeQuery(currentQuery5);
        while(rs5.next()) {
            societyName4.setText(rs5.getString(1));
            societyDescription.setText(rs5.getString(2));
        }
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
        currentQuery4 = "UPDATE society SET society_name = '" +  societyName4.getText() + "', " + "society_description = '" + societyDescription.getText()+ " WHERE society_name = '" + societyName4.getText() + "'";
        System.out.println(currentQuery4);
        int rs4 = statement.executeUpdate(currentQuery4);
    }
}