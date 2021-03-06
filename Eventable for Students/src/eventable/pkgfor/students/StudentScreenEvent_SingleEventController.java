/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.conn;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.statement;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenEvent_SingleEventController extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Text society;
    @FXML
    private Text code;
    @FXML
    private Text comments;
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
    private Button imInButton;
    @FXML
    private Button imOutButton;
    @FXML
    private Button postCommentButton;
    @FXML
    public Text eventName;
    @FXML
    public Text societyName;
    @FXML
    public Text eventLocation;
    @FXML
    public Text eventDate;
    @FXML
    public TextArea eventDetails;
    @FXML
    private TextArea commentBox;
    @FXML
    public Text myStatusText;

    @FXML
    public TableView<Comment> tableofComments;
    @FXML
    public TableColumn<Comment, String> comment;
    @FXML
    public TableColumn<Comment, String> user;
    @FXML
    public TableColumn<Comment, String> startDate;

    ObservableList<Comment> commentsSingleData;
    public static Connection conn;

    public String currentQuery;
    public String currentQuery1;
    public String currentQuery2;
    public String currentQuery3;
    public String currentQuery4;
    public String currentQuery5;

    public static ResultSet rs;
    public static ResultSet rs1;
    public static ResultSet rs2;
    public static ResultSet rs3;
    public static ResultSet rs4;
    public static ResultSet rs5;

    public boolean hasSelectedAttendance = false;
    public int maxCommentId;

    public static Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateEventData();
        try {
            checkAttendanceStatus();
            populateTableView();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvent_SingleEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populateTableView() throws SQLException {
        //Display comments
        statement = openConnection();

        currentQuery = "SELECT first_name, last_name, comment_content FROM APP_USER JOIN Comments USING(email) WHERE event_id = " + StudentScreenEvents_AllController.eventId;
        rs = statement.executeQuery(currentQuery);

        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));

        //Data added to observable List
        commentsSingleData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                commentsSingleData.add(new Comment(rs.getString(i) + " " + rs.getString(i + 1), rs.getString(i + 2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }

        comment.setCellFactory(tc -> {
            TableCell<Comment, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(comment.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        user.setCellFactory(tc -> {
            TableCell<Comment, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(user.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableofComments.setItems(commentsSingleData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            closeConnection(conn, rs, statement);
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

    @FXML
    private void imInButtonClicked(ActionEvent event) throws SQLException {
        statement = openConnection();
        if (!hasSelectedAttendance) {
            currentQuery2 = "INSERT INTO ATTENDANCE (event_id, email, event_theoretical_attendance) VALUES ('"
                    + StudentScreenEvents_AllController.eventId + "', '" + LoginController.loggedInUser + "', 'Y')";
            rs2 = statement.executeQuery(currentQuery2);
            myStatusText.setText("Already Going");
            hasSelectedAttendance = true;
        } else {
            currentQuery2 = "UPDATE ATTENDANCE SET EVENT_THEORETICAL_ATTENDANCE = 'Y' WHERE event_id = '" + StudentScreenEvents_AllController.eventId
                    + "' AND email = '" + LoginController.loggedInUser + "'";
            rs2 = statement.executeQuery(currentQuery2);
            myStatusText.setText("Already Going");
        }
    }

    @FXML
    private void imOutButtonClicked(ActionEvent event) throws SQLException {
        statement = openConnection();
        if (!hasSelectedAttendance) {
            currentQuery3 = "INSERT INTO ATTENDANCE (event_id, email, event_theoretical_attendance) VALUES ('"
                    + StudentScreenEvents_AllController.eventId + "', '" + LoginController.loggedInUser + "', 'N')";
            rs3 = statement.executeQuery(currentQuery3);
            myStatusText.setText("Dogs the boizzz");
            hasSelectedAttendance = true;
        } else {
            currentQuery3 = "UPDATE ATTENDANCE SET EVENT_THEORETICAL_ATTENDANCE = 'N' WHERE event_id = '" + StudentScreenEvents_AllController.eventId
                    + "' AND email = '" + LoginController.loggedInUser + "'";
            rs3 = statement.executeQuery(currentQuery3);
            myStatusText.setText("Dogs the boizzz");
        }
    }

    @FXML
    private void postCommentButtonClicked(ActionEvent event) throws SQLException {
        statement = openConnection();
        currentQuery5 = "SELECT comment_id FROM comments ORDER BY comment_id DESC";
        rs5 = statement.executeQuery(currentQuery5);
        while (rs5.next()) {
            maxCommentId = rs5.getInt(1);
            break;
        }
        maxCommentId += 1;
        
        if (!commentBox.toString().trim().isEmpty()) {
            currentQuery4 = "INSERT INTO COMMENTS (comment_content, email, event_ID, comment_id) VALUES (' " + commentBox.getText().trim() + "', '"
                    + LoginController.loggedInUser + "', '" + StudentScreenEvents_AllController.eventId + "', '" + maxCommentId + "')";
            rs4 = statement.executeQuery(currentQuery4);
        }
        try {
            populateTableView();
            commentBox.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadNext(String destination) {
        stage = (Stage) society.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination)); //putting it to 'Seek a Ride' for now, before we know what type of user each person is
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void populateEventData() {
        //Populate Event Data
        eventName.setText(StudentScreenEvents_AllController.eventName);
        eventLocation.setText(StudentScreenEvents_AllController.eventLocation);
        eventDate.setText(StudentScreenEvents_AllController.eventStartDate + " - " + StudentScreenEvents_AllController.eventEndDate);
        societyName.setText(StudentScreenEvents_AllController.eventSocietyName);
        eventDetails.setText(StudentScreenEvents_AllController.eventText);
    }

    private void checkAttendanceStatus() throws SQLException {
        //Checks DB to see whether the user is already attending or not
        statement = openConnection();

        currentQuery1 = "SELECT * FROM ATTENDANCE WHERE email = '" + LoginController.loggedInUser + "' AND event_id = '"
                + StudentScreenEvents_AllController.eventId + "'";

        rs1 = statement.executeQuery(currentQuery1);

        try {
            if (rs1.isBeforeFirst()) {
                rs1.next();
                if (rs1.getString(4).equalsIgnoreCase("y")) {
                    hasSelectedAttendance = true;
                    myStatusText.setText("Already Going");
                } else {
                    hasSelectedAttendance = true;
                    myStatusText.setText("Dogs the boizzz");
                }
            } else {
                hasSelectedAttendance = false;
                myStatusText.setText("Undecided");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        populateEventData();
        checkAttendanceStatus();
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
