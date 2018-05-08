/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.SignUp2Controller.statement;
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
public class SignUp3Controller extends Application implements Initializable {
    
    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Button next;
    public TextField degree, graduationYear;
    public Text errorText;
    
    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
        public Boolean validateFields() throws SQLException {

        if (Utils.extractStringIsEmpty(degree)) {
            setError("Degree cannot be empty");
            return false;
        }
        if (Utils.extractStringIsEmpty(graduationYear)) {
            setError("Graduation Year cannot be empty");
            return false;
        }
        statement = openConnection();
        currentQuery = "UPDATE app_user SET degree = '" + degree.getText() + "', " + "graduation_year = '" + graduationYear.getText() + "'" + "WHERE email = '" + SignUp1Controller.userEmailAddress + "'";
        System.out.print(currentQuery);
        int update = statement.executeUpdate(currentQuery);
        return true;
    }
    
    @FXML
    private void nextButton(ActionEvent event) throws Exception{
        if (validateFields()) {
            System.out.print("Entered nextButton method");
          //closeConnection(conn, rs, statement);
            loadNext("SignUp4.fxml");
        }
    }
    
    public void loadNext(String destination){
        stage=(Stage) next.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination)); 
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void backButtonPressed(MouseEvent event) {
        loadNext("SignUp2.fxml");
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = (Stage) next.getScene().getWindow();
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
