/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.ForgetPassword3Controller.statement;
import static eventable.pkgfor.students.StudentScreenProfileController.statement;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpdatePasswordController extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    public Text society;
    public TextField newPassword, confirmNewPassword;
    @FXML
    public Text errorText;
    @FXML
    public ImageView backButton;

    public static Connection conn;
    
    public char ch;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;
    
    public String userPassword;
    
    public static String userEmailAddress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        userPassword = newPassword.getText();
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

    public Boolean validatenewPassword() throws SQLException {
        if (Utils.extractStringIsEmpty(confirmNewPassword)) {
            setError("Please confirm new password");
            return false;
        }
        if (Utils.extractStringIsEmpty(newPassword)) {
            setError("Please input new password");
            return false;
        }

        if (!checkPasswordRequirements()) {
            return false;
        }

        if (!confirmNewPassword.getText().equals(newPassword.getText())) {
            setError("Passwords entered do not match");
            return false;
        }
        //Inputting details into database
        int userPasswordHashed = newPassword.getText().hashCode();
        String userPasswordHashedString = userPasswordHashed + "";
        statement = openConnection();
        currentQuery = "UPDATE APP_USER SET password = '" + userPasswordHashedString + "'" + "WHERE email = '" + ForgetPassword1Controller.emailAddress + "'";
        int update = statement.executeUpdate(currentQuery);
        return true;
    }

    @FXML
    private void updateAccount(MouseEvent event) throws SQLException {
        currentQuery = "UPDATE app_user SET password = '" + newPassword.getText() + " WHERE email = '" + LoginController.loggedInUser + "'";
        System.out.println(currentQuery);
        int rs1 = statement.executeUpdate(currentQuery);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = (Stage) society.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    
    private void backToUserProfile (MouseEvent event) {
        loadNext("UpdatePassword1.fxml");
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
}