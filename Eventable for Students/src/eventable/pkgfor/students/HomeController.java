/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author edhopkins
 */
public class HomeController implements Initializable {
    
    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Button login, signUp, proceed;
    
//    @FXML 
//    private ImageView home;
//    
//    @FXML
//    private TextField username;
//    
//    @FXML
//    private PasswordField password;
//    
//    @FXML
//    private Text SignInError, InjectionError;
//    
//    public static String loggedInUser;
//
//    DBController d = new DBController(); //Establish a connection to the db

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {   
    }
    
    @FXML
    private void SignUpButton(ActionEvent event) throws Exception{
        loadNext("SignUp1.fxml");
    }
    
    @FXML
    private void LoginButton(ActionEvent event) throws Exception{
        loadNext("Login.fxml"); 
    }
    
    @FXML
    private void proceedWithoutLoggingIn (ActionEvent event) throws Exception{
        loadNext("StudentScreenEvents_All.fxml");
    }
    
    public void loadNext(String destination){
        stage=(Stage) signUp.getScene().getWindow();
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
