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
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventEndDate;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventId;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventLocation;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventName;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventSocietyName;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventStartDate;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.eventText;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StudentScreenEvents_FavouritesController extends Application implements Initializable {

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
    private Text all;
    @FXML
    private Text favourites;
    @FXML
    private Text going;
    @FXML
    private Text past;
    @FXML
    public TableView<Events> tableofEventsFavourites;
    @FXML
    public TableColumn<Events, String> event;
    @FXML
    public TableColumn<Events, String> startDate;
    @FXML
    public TableColumn<Events, String> location;

    ObservableList<Events> eventsData;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;   
//  public static String loggedInUser;
    
    public void populateTableView() throws SQLException {
        String loggedInUser = LoginController.loggedInUser;
        statement = openConnection();
        currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), LOCATION_TYPE, STREET_NO, STREET_NAME, POSTCODE, SUBURB, BUILDING_CODE, BUILDING_NAME, ROOM_NO, SOCIETY_NAME, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_START, 'hh:mm') AS VARCHAR2(50)), event_text, event_id FROM EVENT JOIN SOCIETY USING(SOCIETY_ID) LEFT OUTER JOIN CAMPUS USING(ROOM_NO, BUILDING_CODE) JOIN favourites f USING (society_id) WHERE f.email = '"+ loggedInUser + "'";
        ResultSet rs = statement.executeQuery(currentQuery);

        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));

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
            Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
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
            tableofEventsFavourites.setItems(eventsData);
        } catch (Exception e) {
            e.printStackTrace();
        }// finally {
          //  closeConnection(conn, rs, statement);
        //}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateTableView();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @FXML
    private void tableviewItemClicked(MouseEvent event) throws SQLException {
         if (event.getClickCount() == 2) {
            Events eventSelected = tableofEventsFavourites.getSelectionModel().getSelectedItem();
            eventName = eventSelected.getEvent();
            String eventLocationType = eventSelected.getLocationType();
            if (eventLocationType.equals("On Campus")) {
                eventLocation = eventSelected.getRoomNo() + " " + eventSelected.getBuildingName() + ", " + eventSelected.getBuildingCode();
            }
            else {
                eventLocation = eventSelected.getStreetNo() + " " + eventSelected.getStreetName() + ", " + eventSelected.getSuburb() + ", " + 
                        eventSelected.getPostcode();
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
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        populateTableView();
        stage = (Stage) society.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //    @FXML
//    private void SignInButton(ActionEvent event) throws Exception{
//        DBController auth = new DBController();
//        SignInError.setVisible(false);
//        InjectionError.setVisible(false);
//        
//        if (auth.sanitise(username.getText(), password.getText())){
//            //InjectionError.setVisible(false);
//                if (auth.authenticate(username.getText(), password.getText())){
//                    loggedInUser = username.getText();
//                    //userType = Integer.parseInt(d.returnSingleQuery("SELECT USERTYPE FROM USER WHERE USERNAME LIKE '" + loggedInUser + "'"));
//                    loadNext("Seek a Ride.fxml"); //Change this to the main report page
//
//                }
//                else {
//                    SignInError.setVisible(true);
//                }
//        }
//        else {
//            InjectionError.setVisible(true);
//        }
//
//    }
//    
//    //Saves duplicates
//    public void loadNext(String destination){
//        SignInError.setVisible(false);
//        stage=(Stage) SignIn.getScene().getWindow();
//        try {
//            root = FXMLLoader.load(getClass().getResource(destination)); //putting it to 'Seek a Ride' for now, before we know what type of user each person is
//        } catch (IOException ex) {
//            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    public static String getUser(){
//        return loggedInUser;
//    } 
}
