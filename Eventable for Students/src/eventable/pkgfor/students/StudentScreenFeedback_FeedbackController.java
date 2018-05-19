/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
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
import javafx.util.StringConverter;

public class StudentScreenFeedback_FeedbackController extends Application implements Initializable {

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
    private TextField searchField;
    @FXML
    public TableView<Feedback> tableofFeedback;
    @FXML
    public TableColumn<Feedback, String> event;
    @FXML
    public TableColumn<Feedback, String> endDate;
    @FXML
    public TableColumn<Feedback, String> societyName;
    
    public static String eventName;
    public static String eventSocietyName;
    public static String eventTime;
    public static String eventId;
    
    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;
    
    ObservableList<Feedback> feedbackData;
    ObservableList<FavouriteSocieties> listOfSocieties;
    private boolean aToZ = true;
    private String bySocietyFilterChoice;
    private boolean filtersApplied = false;
    
    public void populateTableView() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT SOCIETY_NAME, EVENT_TITLE, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_id  FROM SOCIETY  JOIN EVENT USING (SOCIETY_ID) "
                + "JOIN ATTENDANCE USING (EVENT_ID) WHERE EVENT_ACTUAL_ATTENDANCE = 'Y' AND email = '" + LoginController.loggedInUser +"'";
        ResultSet rs = statement.executeQuery(currentQuery);

        societyName.setCellValueFactory(new PropertyValueFactory<>("societyName"));
        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        //Data added to observable List
        feedbackData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                feedbackData.add(new Feedback(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2),
                rs.getString(i + 3), rs.getString(i + 4), rs.getString(i + 5), Integer.parseInt(rs.getString(i + 6))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }

        societyName.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        event.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(event.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        endDate.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(endDate.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableofFeedback.setItems(feedbackData);
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
            Logger.getLogger(StudentScreenFeedback_FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void search (KeyEvent event) throws SQLException {
        populateTableView("SELECT SOCIETY_NAME, EVENT_TITLE, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_id  FROM SOCIETY  JOIN EVENT USING (SOCIETY_ID) "
                + "JOIN ATTENDANCE USING (EVENT_ID) WHERE EVENT_ACTUAL_ATTENDANCE = 'Y' AND email = '" + LoginController.loggedInUser + "' AND "
                + "(lower(event_title) LIKE '%" + searchField.getText().trim().toLowerCase() + "%' OR lower(event_text) LIKE '%" + 
                searchField.getText().trim().toLowerCase() + "')");
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
            currentQuery = "SELECT SOCIETY_NAME, EVENT_TITLE, CAST(TO_CHAR(EVENT_END, 'dd/MON/yy') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)), CAST(TO_CHAR(EVENT_END, 'hh:mm am') AS VARCHAR2(50)), "
                + "CAST(TO_CHAR(EVENT_START, 'hh:mm am') AS VARCHAR2(50)), event_id  FROM SOCIETY  JOIN EVENT USING (SOCIETY_ID) "
                + "JOIN ATTENDANCE USING (EVENT_ID) WHERE EVENT_ACTUAL_ATTENDANCE = 'Y' AND email = '" + LoginController.loggedInUser +"'";

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

    public void loadNext(String destination){
        stage=(Stage) society.getScene().getWindow();
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
    
    @FXML
    private void tableviewItemClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            Feedback feedbackSelected = tableofFeedback.getSelectionModel().getSelectedItem();
            eventName = feedbackSelected.getEvent();
            eventId = feedbackSelected.getEventId();
            eventSocietyName = feedbackSelected.getSocietyName();
            eventTime = feedbackSelected.getStartDate() + " " + feedbackSelected.getEventStartTime() + " - " + feedbackSelected.getEndDate() +
                    " " + feedbackSelected.getEventEndTime();
            loadNext("StudentScreenFeedback_FeedbackForm.fxml");
        }
    }
    
    public void populateTableView(String currentQuery) throws SQLException {
        statement = openConnection();
        ResultSet rs = statement.executeQuery(currentQuery);

        societyName.setCellValueFactory(new PropertyValueFactory<>("societyName"));
        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        //Data added to observable List
        feedbackData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                feedbackData.add(new Feedback(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2),
                rs.getString(i + 3), rs.getString(i + 4), rs.getString(i + 5), Integer.parseInt(rs.getString(i + 6))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }

        societyName.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        event.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(event.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        endDate.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(endDate.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableofFeedback.setItems(feedbackData);
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
