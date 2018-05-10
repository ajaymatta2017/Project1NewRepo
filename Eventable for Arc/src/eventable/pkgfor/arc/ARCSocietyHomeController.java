/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ARCSocietyHomeController extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    public TextField societyName, newSocietyEmail, arcEmail, newArcPosition, societyEmail, newArcEmail, newArcPassword, newSocietyPassword;
    public PasswordField arcPassword, societyPassword;
    public Text errorText, errorText1, errorText2, errorText3;
    public ImageView backButton;
    public Button societyLogin, societyCreateAccount, arcLogin, arcCreateAccount;

    public static Connection conn;

    public String currentQuery;
    
    public String currentQuery1;
    
    public String currentQuery2;

    public static ResultSet rs;

    public static Statement statement;

    public static String emailAddress;

    public static String loggedInUser;

    public static Boolean userInSystem;

    public String userPassword;

    public char ch;
    
    int maxSocietyID;
    int newMaxSocietyID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //Authenticate
    public boolean authenticateArcAccount() throws ClassNotFoundException, SQLException {
        //Get email and password from fields
        statement = openConnection();
        loggedInUser = arcEmail.getText().trim().toLowerCase();
        int loggedInPasswordHashed = arcPassword.getText().hashCode();
        String loggedInPasswordHashedString = loggedInPasswordHashed + "";

        //Checks if either field is empty
        if ((arcEmail.getText().isEmpty()) || (arcPassword.getText().isEmpty())) {
            setError2("Please enter username and/or password");
        } else {
            currentQuery = "SELECT PASSWORD FROM APP_USER WHERE EMAIL = lower('" + loggedInUser + "')";
            String passwordStoredInDB = null;
            int passwordStoredInDBHashed;
            String passwordStoredInDBHashedString = null;

            try {
                try {
                    ResultSet rs = statement.executeQuery(currentQuery);
                    while (rs.next()) {
                        passwordStoredInDB = rs.getString("PASSWORD");
                        passwordStoredInDBHashed = passwordStoredInDB.hashCode();
                        passwordStoredInDBHashedString = passwordStoredInDBHashed + "";
                    }
                } catch (NullPointerException e) {
                    setError2("Incorrect username/password entered");
                    return false;
                }

                //Comparing passwords
                if (loggedInPasswordHashedString.matches(passwordStoredInDB)) {
                    System.out.print("Password correct");
                    return true;
                } else {
                    setError2("Incorrect username/password entered");
                    return false;
                }
            } catch (Exception ex) {
                Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }

    //Authenticate
    public boolean authenticateSocietyAccount() throws ClassNotFoundException, SQLException {
        //Get email and password from fields
        statement = openConnection();
        loggedInUser = societyEmail.getText().trim().toLowerCase();
        int loggedInPasswordHashed = societyPassword.getText().hashCode();
        String loggedInPasswordHashedString = loggedInPasswordHashed + "";

        //Checks if either field is empty
        if ((societyEmail.getText().isEmpty()) || (societyPassword.getText().isEmpty())) {
            setError("Please enter username and/or password");
        } else {
            currentQuery = "SELECT PASSWORD FROM APP_USER WHERE EMAIL = lower('" + loggedInUser + "')";
            String passwordStoredInDB = null;
            int passwordStoredInDBHashed;
            String passwordStoredInDBHashedString = null;

            try {

                ResultSet rs = statement.executeQuery(currentQuery);
                while (rs.next()) {
                    passwordStoredInDB = rs.getString("PASSWORD");
                    passwordStoredInDBHashed = passwordStoredInDB.hashCode();
                    passwordStoredInDBHashedString = passwordStoredInDBHashed + "";
                }
                //Comparing passwords
                if (loggedInPasswordHashedString.matches(passwordStoredInDB)) {
                    System.out.print("Password correct");
                    return true;
                } else {
                    setError("Incorrect username/password entered");
                    return false;
                }
            } catch (NullPointerException e) {
                setError("Incorrect username/password entered");
                return false;
            } catch (Exception ex) {
                Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        }
        return false;
    }

    public void setError(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisible(true);
    }
    
    public void setError1(String errorMessage) {
        errorText1.setText(errorMessage);
        errorText1.setVisible(true);
    }
    
    public void setError2(String errorMessage) {
        errorText2.setText(errorMessage);
        errorText2.setVisible(true);
    }
    
   public void setError3(String errorMessage) {
        errorText3.setText(errorMessage);
        errorText3.setVisible(true);
    }


    public boolean checkSocietyPasswordRequirements() {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;
        userPassword = newSocietyPassword.getText();
        //Check password length
        if (userPassword.length() < 8) {
            setError1("Minimum length should be 8 characters");
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
            setError1("At least 1 uppercase letter is required");
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
            setError1("At least 1 lowercase letter is required");
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
            setError1("At least 1 number is required");
            System.out.println("Failed number requirement");
            return false;
        }
        //Check if password contains symbol
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(userPassword);
        hasSymbol = m.find();
        if (!hasSymbol) {
            setError1("At least 1 symbol is required");
            System.out.println("Failed symbol requirement");
            return false;
        }
        return true;
    }

    public boolean checkARCPasswordRequirements() {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;
        userPassword = newArcPassword.getText();
        //Check password length
        if (userPassword.length() < 8) {
            setError3("Minimum length should be 8 characters");
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
            setError3("At least 1 uppercase letter is required");
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
            setError3("At least 1 lowercase letter is required");
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
            setError3("At least 1 number is required");
            System.out.println("Failed number requirement");
            return false;
        }
        //Check if password contains symbol
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(userPassword);
        hasSymbol = m.find();
        if (!hasSymbol) {
            setError3("At least 1 symbol is required");
            System.out.println("Failed symbol requirement");
            return false;
        }
        return true;
    }

    public Boolean validatenewSocietyPassword() throws SQLException {
        if (!checkSocietyPasswordRequirements()) {
            return false;
        }
        //Inputting details into database
        String newEmail = newSocietyEmail.getText();
        String newSociety = societyName.getText();
        int userPasswordHashed = newSocietyPassword.getText().hashCode();
        String userPasswordHashedString = userPasswordHashed + "";
        statement = openConnection();
        currentQuery2 = "SELECT society_id FROM society ORDER BY society_id DESC";
        ResultSet rs = statement.executeQuery(currentQuery2);
        while (rs.next()) {
            maxSocietyID = rs.getInt(1);
            break;
        }
        newMaxSocietyID = maxSocietyID + 1;
        currentQuery = "INSERT INTO SOCIETY(society_id, society_name) VALUES(" + newMaxSocietyID + ", '" + newSociety + "')";
        currentQuery1 = "INSERT INTO APP_USER(email, password, society_id) VALUES('" + newEmail + "', '" + userPasswordHashed + "', " + newMaxSocietyID + ")";
        int update = statement.executeUpdate(currentQuery);
        int update1 = statement.executeUpdate(currentQuery1);
        return true;
    }

    public Boolean validatenewArcPassword() throws SQLException {
        if (!checkARCPasswordRequirements()) {
            return false;
        }
        //Inputting details into database
        String newEmail = newArcEmail.getText();
        String newArc = newArcPosition.getText();
        int userPasswordHashed = newArcPassword.getText().hashCode();
        String userPasswordHashedString = userPasswordHashed + "";
        statement = openConnection();
        currentQuery = "INSERT INTO APP_USER(email, position, password, account_type) VALUES('" + newEmail + "', '" + newArc + "', '" + userPasswordHashed + "', 'ARC')";
        System.out.println(currentQuery);
        int update = statement.executeUpdate(currentQuery);
        return true;
    }
    
    @FXML
    private void createSocietyAccount (MouseEvent event) throws SQLException {
        if (validatenewSocietyPassword()) {
            loadNext("SocietyScreens.fxml");
        }
    }

    @FXML
    private void createArcAccount(MouseEvent event) throws SQLException {
        if (validatenewArcPassword()) {
            loadNext1("ARCLandingPage.fxml");
        }
    }
    
    @FXML
    private void societyLogin(ActionEvent event) throws Exception {
        errorText.setVisible(false);
        userInSystem = true;
        //Checks if password is correct
        if (authenticateSocietyAccount()) {
            loadNext("SocietyScreens.fxml");
        }
    }

    @FXML
    private void arcLogin(ActionEvent event) throws Exception {
        errorText.setVisible(false);
        userInSystem = true;
        //Checks if password is correct
        if (authenticateArcAccount()) {
            loadNext1("ARCLandingPage.fxml");
        }
    }

    public void loadNext(String destination) {
        stage = (Stage) societyLogin.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(ARCSocietyHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loadNext1(String destination) {
        stage = (Stage) arcLogin.getScene().getWindow();
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
}