/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.SignUp1Controller.userEmailAddress;
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

/**
 *
 * @author edhopkins
 */
public class SignUp2Controller extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Button next;
    public TextField firstName, lastName, zid, mobileNumber;
    public Text errorText;
    @FXML
    private ImageView backButton;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Boolean validateFields() throws SQLException {
        errorText.setVisible(false);

        if (Utils.extractStringIsEmpty(firstName)) {
            setError("First Name cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(lastName)) {
            setError("Last Name cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(zid)) {
            setError("zID cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(mobileNumber)) {
            setError("Mobile Number cannot be empty");
            return false;
        }
        //Check length of zID is only 7 digits (no Z included)
        if (zid.getText().length() > 7) {
            setError("Incorrect zID format");
            return false;
        }
        //Check that zID doesn't contain any letters
        //TODO: fix this
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(Utils.extractString(zid));
        boolean invalidZID = m.find();
        if(!invalidZID) {
            setError("Incorrect zID format (NOTE: please do not enter the z)");
            return false;
        }
        //Check length of mobile number is only 10 digits
        if (!(mobileNumber.getText().length() == 10)) {
            setError("Invalid Mobile Number");
            return false;
        }
        //Check that mobile number doesn't contain any letters
        //TODO: fix this
        Pattern p1 = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher m1 = p1.matcher(Utils.extractString(mobileNumber));
        boolean invalidMobile = m1.find();
        if(!invalidMobile) {
            setError("Invalid Mobile Number");
            return false;
        }
        statement = openConnection();
        currentQuery = "UPDATE app_user SET zid = '" + zid.getText() + "', " + "mobile_number = '" + mobileNumber.getText() + "', " + "first_name = '" + firstName.getText() + "', " + "last_name = '" + lastName.getText() + "'" + " WHERE email = '" + SignUp1Controller.userEmailAddress + "'";
        System.out.print(currentQuery);
        int update = statement.executeUpdate(currentQuery);
        return true;
    }

    @FXML
    private void nextButton(ActionEvent event) throws SQLException {
        if (validateFields()) {
            System.out.print("Entered nextButton method");
//            closeConnection(conn, rs, statement);
            loadNext("SignUp3.fxml");
        }
    }
    @FXML
    private void backButtonPressed (MouseEvent event) {
        loadNext("SignUp1.fxml");
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
