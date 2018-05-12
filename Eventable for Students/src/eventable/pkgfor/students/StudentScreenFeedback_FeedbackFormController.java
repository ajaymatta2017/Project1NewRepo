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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenFeedback_FeedbackFormController implements Initializable {

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
    private Text societyName;
    @FXML
    private Text eventDateTime;
    @FXML
    private Text eventName;
    @FXML
    private Button backButton;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private RadioButton radioButton3;
    @FXML
    private RadioButton radioButton4;
    @FXML
    private RadioButton radioButton5;
    @FXML
    private TextArea thoughts;
    @FXML
    private TextArea enjoy;
    @FXML
    private TextArea improve;
    @FXML
    private Button recommendHy;
    @FXML
    private Button recommendNa;
    @FXML
    private Button futureHy;
    @FXML
    private Button futureNa;
    @FXML
    private Text errorText;
    
    public Integer radioButtonChoice = 0;
    public String recommendChoice;
    public String futureChoice;
    
    public static Connection conn;

    public String currentQuery;
    public String currentQuery1;
    public String currentQuery2;
    public String currentQuery3;
    public String currentQuery4;

    public static ResultSet rs;
    public static ResultSet rs1;
    public static ResultSet rs2;
    public static ResultSet rs3;
    public static ResultSet rs4;
    

    public static Statement statement;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateEventData();
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
    private void topNavBackButton(ActionEvent event) {
        loadNext("StudentScreenFeedback_Feedback.fxml");
    }

    @FXML
    private void select1(ActionEvent event) {
        radioButton1.setSelected(true);
        radioButton2.setSelected(false);
        radioButton3.setSelected(false);
        radioButton4.setSelected(false);
        radioButton5.setSelected(false);
        radioButtonChoice = 1;
    }

    @FXML
    private void select2(ActionEvent event) {
        radioButton2.setSelected(true);
        radioButton1.setSelected(false);
        radioButton3.setSelected(false);
        radioButton4.setSelected(false);
        radioButton5.setSelected(false);
        radioButtonChoice = 2;
    }

    @FXML
    private void select3(ActionEvent event) {
        radioButton3.setSelected(true);
        radioButton1.setSelected(false);
        radioButton2.setSelected(false);
        radioButton4.setSelected(false);
        radioButton5.setSelected(false);
        radioButtonChoice = 3;
    }

    @FXML
    private void select4(ActionEvent event) {
        radioButton4.setSelected(true);
        radioButton1.setSelected(false);
        radioButton2.setSelected(false);
        radioButton3.setSelected(false);
        radioButton5.setSelected(false);
        radioButtonChoice = 4;
    }

    @FXML
    private void select5(ActionEvent event) {
        radioButton5.setSelected(true);
        radioButton1.setSelected(false);
        radioButton2.setSelected(false);
        radioButton3.setSelected(false);
        radioButton4.setSelected(false);
        radioButtonChoice = 5;
    }
    
    @FXML
    private void recommendHyClicked (ActionEvent event) {
        recommendChoice = "Hell Yeah";
    }
    
    @FXML
    private void recommendNaClicked (ActionEvent event) {
        recommendChoice = "Na";
    }
    
    @FXML
    private void futureHyClicked (ActionEvent event) {
        futureChoice = "Hell Yeah";
    }
    
    @FXML
    private void futureNaClicked (ActionEvent event) {
        futureChoice = "Na";
    }

    @FXML
    private void submitFeedback (ActionEvent event) throws SQLException {
        //If all question answered write to the DB
        if(radioButtonChoice != null && recommendChoice != null && futureChoice != null && !enjoy.getText().trim().isEmpty() && !improve.getText().trim().isEmpty()) {
            statement = openConnection();
            currentQuery = "INSERT INTO FEEDBACK (email, question_id, event_id, feedback_answer) VALUES ('" + LoginController.loggedInUser +
                    "', '1', '" + StudentScreenFeedback_FeedbackController.eventId + "', '" + radioButtonChoice + "')";
            rs = statement.executeQuery(currentQuery);
            
            currentQuery1 = "INSERT INTO FEEDBACK (email, question_id, event_id, feedback_answer) VALUES ('" + LoginController.loggedInUser +
                    "', '2', '" + StudentScreenFeedback_FeedbackController.eventId + "', '" + thoughts.getText().trim() + "')";
            rs1 = statement.executeQuery(currentQuery1);
            
            currentQuery2 = "INSERT INTO FEEDBACK (email, question_id, event_id, feedback_answer) VALUES ('" + LoginController.loggedInUser +
                    "', '3', '" + StudentScreenFeedback_FeedbackController.eventId + "', '" + improve.getText().trim() + "')";
            rs2 = statement.executeQuery(currentQuery2);
            
            currentQuery3 = "INSERT INTO FEEDBACK (email, question_id, event_id, feedback_answer) VALUES ('" + LoginController.loggedInUser +
                    "', '4', '" + StudentScreenFeedback_FeedbackController.eventId + "', '" + recommendChoice + "')";
            rs3 = statement.executeQuery(currentQuery3);
            
            currentQuery4 = "INSERT INTO FEEDBACK (email, question_id, event_id, feedback_answer) VALUES ('" + LoginController.loggedInUser +
                    "', '5', '" + StudentScreenFeedback_FeedbackController.eventId + "', '" + futureChoice + "')";
            rs4 = statement.executeQuery(currentQuery4);
            loadNext("StudentScreenFeedback_FeedbackSubmitted.fxml");
        }
        else {
            eventName.requestFocus();
            errorText.setText("Please answer all questions");
        }
    }
    
    private void populateEventData() {
        //Populate Event Data
        eventName.setText(StudentScreenFeedback_FeedbackController.eventName);
        eventDateTime.setText(StudentScreenFeedback_FeedbackController.eventTime);
        societyName.setText(StudentScreenFeedback_FeedbackController.eventSocietyName);
    }
    
    public void loadNext(String destination){
        stage=(Stage) society.getScene().getWindow();
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
