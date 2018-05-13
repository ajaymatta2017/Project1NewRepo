/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.StudentScreenEvents_GoingController.statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenCodeController implements Initializable {

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
    private Text ceSociety;
    @FXML
    private Text ceName;
    @FXML
    private Text ceTime;
    @FXML
    private Text ceLocation;
    @FXML
    private Text ceCode;
    @FXML
    private Button refreshButton;
    
    public static Connection conn;

    public String currentQuery;
    
    private int eventID;
    private String loggedInUser;

    public static ResultSet rs;

    public static Statement statement;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            detectEvent();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public void detectEvent() throws SQLException {
        refreshButton.setDisable(false);
        
        loggedInUser = LoginController.loggedInUser;
        statement = openConnection();
        currentQuery = "SELECT UNIQUE EVENT_TITLE FROM EVENT e, ATTENDANCE a WHERE e.EVENT_START < SYSTIMESTAMP AND e.EVENT_END > SYSTIMESTAMP AND e.EVENT_ID = a.EVENT_ID AND EMAIL = '" + loggedInUser + "' AND ROWNUM <= 1";
        ResultSet rs = statement.executeQuery(currentQuery);
        
        //check if there's a current event
        if (!rs.next() ){
            //No data
            ceSociety.setText("N/A");
            ceName.setText("NO EVENT FOUND");
            ceTime.setText("N/A");
            ceLocation.setText("N/A");
            ceCode.setText("N/A");
            refreshButton.setDisable(true);
        }
        else{
            currentQuery = "SELECT UNIQUE e.EVENT_ID, e.EVENT_TITLE, e.LOCATION_TYPE, e.EVENT_START, s.SOCIETY_NAME FROM EVENT e, ATTENDANCE a, SOCIETY s WHERE e.EVENT_START < SYSTIMESTAMP AND e.EVENT_END > SYSTIMESTAMP AND e.EVENT_ID = a.EVENT_ID AND e.SOCIETY_ID = s.SOCIETY_ID AND EMAIL = '" + loggedInUser + "' AND ROWNUM <= 1";
            rs = statement.executeQuery(currentQuery);
            rs.next();
            eventID = Integer.parseInt(rs.getString("EVENT_ID"));
            ceSociety.setText(rs.getString("SOCIETY_NAME"));
            ceName.setText(rs.getString("EVENT_TITLE"));
            ceTime.setText(rs.getString("EVENT_START"));
            ceLocation.setText(rs.getString("LOCATION_TYPE"));
            //ceCode.setText("N/A");
            detectCode();

        }
    }
    
    public void detectCode() throws SQLException {
        currentQuery = "SELECT GENERATED_CODE FROM ATTENDANCE WHERE EMAIL = '" + loggedInUser + "' AND EVENT_ID = " + eventID;
        System.out.println(currentQuery);
        rs = statement.executeQuery(currentQuery);
        rs.next();
        if (rs.getString("GENERATED_CODE") == null){
            ceCode.setText("(No Code Available)");
        }
        else {
            ceCode.setText(rs.getString("GENERATED_CODE"));
        }
    }
    
    @FXML
    private void refreshClicked(MouseEvent event) throws SQLException {
        detectCode();
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
