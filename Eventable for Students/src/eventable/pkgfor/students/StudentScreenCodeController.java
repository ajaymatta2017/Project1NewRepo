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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
