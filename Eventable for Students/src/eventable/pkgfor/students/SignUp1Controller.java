/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.StudentScreenEvents_FavouritesController.conn;
import static eventable.pkgfor.students.StudentScreenEvents_FavouritesController.statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
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

public class SignUp1Controller extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Button next;
    @FXML
    public PasswordField confirmPassword;
    public TextField email;
    public Text errorText;
    @FXML
    public PasswordField password;
    @FXML
    private ImageView backButton;

    char ch;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;

    public String userPassword;
    
    public static String userEmailAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Boolean validateRegistration() throws SQLException {
        if (Utils.extractStringIsEmpty(email)) {
            setError("Email cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(password)) {
            setError("Password cannot be empty");
            return false;
        }

        if (Utils.extractStringIsEmpty(confirmPassword)) {
            setError("Confirm Password cannot be empty");
            return false;
        }

        if (!checkPasswordRequirements()) {
            return false;
        }
        if (!confirmPassword.getText().equals(password.getText())) {
            setError("Passwords entered do not match");
            return false;
        }
        //Inputting details into database
        int userPasswordHashed = userPassword.hashCode();
        String userPasswordHashedString = userPasswordHashed + "";
        statement = openConnection();
        currentQuery = "INSERT INTO APP_USER(email, password) VALUES('" + email.getText() + "', '" + userPasswordHashedString + "')";
        int update = statement.executeUpdate(currentQuery);
        userEmailAddress = email.getText();
        return true;
    }

    @FXML
    private void nextButton(ActionEvent event) throws SQLException {
        if (validateRegistration()) {
//            closeConnection(conn, rs, statement);
            loadNext("SignUp2.fxml");
        }
    }
    
    @FXML
    private void backButtonPressed (MouseEvent event) throws SQLException {
        loadNext("Home.fxml");
    }

    public void loadNext(String destination) {
        stage = (Stage) next.getScene().getWindow();
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
        stage = (Stage) next.getScene().getWindow(); //NEED TO FIX UP LINE
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setError(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisible(true);
    }

    public boolean checkPasswordRequirements() {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;
        userPassword = password.getText();
        //Check password length
        if (userPassword.length() < 8) {
            setError("Minimum length should be 8 characters");
            System.out.println("Failed length requirement");
            return false;
        }
        //Check upperCase:
        for (int i = 0; i < userPassword.length(); i++) {
            ch = userPassword.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
                break;
            }
        }
        if (!hasUpper) {
            setError("At least 1 uppercase letter is required");
            System.out.println("Failed Uppercase requirement");
            return false;
        }
        //Check lowerCase:
        for (int i = 0; i < userPassword.length(); i++) {
            ch = userPassword.charAt(i);
            if (Character.isLowerCase(ch)) {
                hasLower = true;
                break;
            }
        }
        if (!hasLower) {
            setError("At least 1 lowercase letter is required");
            System.out.println("Failed lowercase requirement");
            return false;
        }
        //Check if password contains number
        for (int i = 0; i < userPassword.length(); i++) {
            for (int j = 0; j < 10; j++) {
                if (userPassword.charAt(i) == Integer.toString(j).charAt(0)) {
                    hasNumber = true;
                    break;
                }
            }
        }
        if (!hasNumber) {
            setError("At least 1 number is required");
            System.out.println("Failed number requirement");
            return false;
        }
        //Check if password contains symbol
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(userPassword);
        hasSymbol = m.find();
        if (!hasSymbol) {
            setError("At least 1 symbol is required");
            System.out.println("Failed symbol requirement");
            return false;
        }
        return true;
    }
}

//    @FXML
//    private void SignInButton(ActionEvent event) throws Exception{
//        DBController auth = new DBController();
//        SignInError.setVisible(false);
//        InjectionError.setVisible(false);
//        
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
