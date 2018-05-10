/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import eventable.pkgfor.*;
import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author edhopkins
 */
public class LoginController implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Button loginButton;
    @FXML
    private ImageView backButton;

    @FXML
    private TextField email;
    @FXML
    public PasswordField password;

    @FXML
    private Text errorText, errorText2, forgotPassword;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;

    public static String loggedInUser;

    public static Boolean userInSystem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //Authenticate
    public boolean authenticate() throws ClassNotFoundException, SQLException {
        //Get email and password from fields
        statement = openConnection();
        loggedInUser = email.getText().trim().toLowerCase();
        int loggedInPasswordHashed = password.getText().hashCode();
        String loggedInPasswordHashedString = loggedInPasswordHashed + "";

        //Checks if either field is empty
        if ((email.getText().isEmpty()) || (password.getText().isEmpty())) {
            errorText.setVisible(true);
        } else {
            currentQuery = "SELECT PASSWORD FROM APP_USER WHERE EMAIL = lower('" + loggedInUser + "')";
            String passwordStoredInDB = null;
            int passwordStoredInDBHashed;
            String passwordStoredInDBHashedString = null; //as passwords stored in DB are currently not hashed 

            try {
                try {
                    ResultSet rs = statement.executeQuery(currentQuery);
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            passwordStoredInDB = rs.getString("PASSWORD");
//                    passwordStoredInDBHashed = passwordStoredInDB.hashCode();
//                    passwordStoredInDBHashedString = passwordStoredInDBHashed + "";
                        }
                    } else {
                        errorText2.setVisible(true);
                    }
                } catch (NullPointerException e) {
                    errorText2.setVisible(true);
                    return false;
                }

                //Comparing passwords
                if (loggedInPasswordHashedString.matches(passwordStoredInDB)) {
                    System.out.print("Password correct");
                    return true;
                } else {
                    errorText2.setVisible(true);
                    return false;
                }
            } catch (Exception ex) {
                Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    @FXML
    private void loginButton(ActionEvent event) throws Exception {
        errorText.setVisible(false);
        errorText2.setVisible(false);
        userInSystem = true;
        //Checks if password is correct
        if (authenticate()) {
//                closeConnection(conn, rs, statement);
            loadNext("StudentScreenEvents_All.fxml");
            //loadNext("StudentScreenEvents_Favourites.fxml");
            // loadNext("StudentScreenEvents_Going.fxml");
//                loadNext("StudentScreenEvents_Past.fxml");
            //loadNext("SignUp3.fxml");
            // loadNext("ForgetPassword1.fxml");
            //loadNext("SignUp1.fxml");
        }
    }

    @FXML
    private void backButtonPressed(MouseEvent event) throws SQLException {
        loadNext("Home.fxml");
    }

    @FXML
    private void forgotPassword(MouseEvent event) {
        loadNext("ForgetPassword1.fxml");
    }

    public void loadNext(String destination) {
        stage = (Stage) loginButton.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
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
