/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentScreenProfileController extends Application implements Initializable {
    @FXML
    Stage stage;
    Parent root;

    @FXML
    public Text society;
    @FXML
    private Text code;
    @FXML
    private Text events;
    @FXML
    private Text feedback;
    @FXML
    private Text profile;
    @FXML
    public Button editAccountButton;
    @FXML
    public Button updateAccountPassword;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField email;
    @FXML
    public TextField zID;
    @FXML
    public TextField mobile;
    @FXML
    public TextField degree;
    @FXML
    public TextField gradYear;
    
    public static Connection conn;
    
    public String currentQuery;
    
    public String currentQuery1;
    
    public static ResultSet rs;
    
    public static int rs1;
    
    public static Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayUserData();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenProfileController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void updateAccountPasswordButton(MouseEvent event) {
        loadNext("UpdatePassword1.fxml");
    }

    private void displayUserData () throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT email, zid, mobile_number, first_name, last_name, degree, graduation_year FROM app_user WHERE email = '" + LoginController.loggedInUser + "'";
        ResultSet rs = statement.executeQuery(currentQuery);
        while(rs.next()) {
            email.setText(rs.getString("email"));
           // password.setText(rs.getString("password"));
            zID.setText(rs.getString("zid"));
            mobile.setText(rs.getString("mobile_number"));
            firstName.setText(rs.getString("first_name"));
            lastName.setText(rs.getString("last_name"));
            degree.setText(rs.getString("degree"));
            gradYear.setText(rs.getString("graduation_year"));
        }
    }
      
    @FXML
    private void updateAccount (MouseEvent event) throws SQLException {
        currentQuery1 = "UPDATE app_user SET email = '" +  email.getText() + "', " + "zid = '" + zID.getText() + "', " + "mobile_number = '" + mobile.getText() + "', " + "first_name = '" + firstName.getText() + "', " + "last_name = '" + lastName.getText() + "', " + "degree = '" + degree.getText() + "', " + " graduation_year = '" + gradYear.getText() + "'" + " WHERE email = '" + LoginController.loggedInUser + "'";
        System.out.println(currentQuery1);
        int rs1 = statement.executeUpdate(currentQuery1);
    }
    
    @FXML
    private void deleteAccount (MouseEvent event) throws SQLException {
        //TODO: fill this in
    }
       
    @FXML
    private void enableEditAccount(MouseEvent event) {
        if (!firstName.isEditable()) {
            firstName.setEditable(true);
            lastName.setEditable(true);
            email.setEditable(true);
            //password.setEditable(true).;
            zID.setEditable(true);
            mobile.setEditable(true);
            degree.setEditable(true);
            gradYear.setEditable(true);
            editAccountButton.setText("Save");
        } 
        else {
            firstName.setEditable(false);
            lastName.setEditable(false);
            email.setEditable(false);
            //password.setEditable(false);
            zID.setEditable(false);
            mobile.setEditable(false);
            degree.setEditable(false);
            gradYear.setEditable(false);
            editAccountButton.setText("Edit Account");
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
        stage = (Stage) society.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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