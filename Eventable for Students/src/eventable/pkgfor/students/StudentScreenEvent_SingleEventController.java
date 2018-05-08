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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenEvent_SingleEventController implements Initializable {

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
    public TableView<Events> tableofEvents;
    @FXML
    public TableColumn<Events, String> event;
    @FXML
    public TableColumn<Events, String> startDate;
    @FXML
    public TableColumn<Events, String> location;

    ObservableList<Events> eventsSingleData;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void populateTableView() throws SQLException {
        //Only display events that are in the future
        statement = openConnection();
       
        currentQuery = "SELECT EVENT_TITLE, CAST(TO_CHAR(EVENT_START, 'dd/MON/yy') AS VARCHAR2(50)) EVENT_START, LOCATION_TYPE FROM EVENT WHERE EVENT_START >= '05/MAY/2018'";
        rs = statement.executeQuery(currentQuery);

        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));

        //Data added to observable List
        eventsSingleData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                eventsSingleData.add(new Events(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Data added to TableView
        try {
            tableofEvents.setItems(eventsSingleData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, rs, statement);
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
