///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package eventable.pkgfor.arc;
//
//import static eventable.pkgfor.arc.ARCSocietyHomeController.loggedInUser;
//import static eventable.pkgfor.arc.DBController.openConnection;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.shape.Circle;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.util.StringConverter;
//
//public class SocietyScreens_SingleEventController extends Application implements Initializable {
//    
//    @FXML
//    Stage stage;
//    Parent root;
//    
//    @FXML
//    private Circle societyPage;
//    @FXML
//    private TextField email;
//    @FXML
//    private TextField eventName;
//    @FXML
//    private DatePicker startDate;
//    @FXML
//    private TextField startTime;
//    @FXML
//    private DatePicker endDate;
//    @FXML
//    private TextField endTime;
//    @FXML
//    private RadioButton onCampus;
//    @FXML
//    private RadioButton offCampus;
//    @FXML
//    private TextField streetNo;
//    @FXML
//    private TextField streetName;
//    @FXML
//    private TextField buildingName;
//    @FXML
//    private TextField suburb;
//    @FXML
//    private TextField postcode;
//    @FXML
//    private TextField roomNo;
//    @FXML
//    private TextField eventDescription;
//    @FXML
//    private Button createButton;
//    @FXML        
//    private Button cancelButton;
//    @FXML
//    private Text errorText;
//    
//    public static Connection conn;
//
//    public String currentQuery;
//    
//    public String currentQuery1;
//
//    public static ResultSet rs;
//
//    public static Statement statement;
//    
//    public Integer radioButtonChoice = 0;
//    private String currentQuery2;
//    private ObservableList<EventType> eventTypeData;
//    
//    @FXML
//    private void select1(ActionEvent event) {
//        radioButtonChoice = 1;
//    }
//    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//    }    
//
//    @FXML
//    private void populateEmail(ActionEvent event) {
//        email.setText(ARCSocietyHomeController.loggedInUser);
//    }
//    
//    @FXML
//    private void createEventButton (MouseEvent event) throws SQLException {
//        if (validateFields()) {
//            loadNext("SocietyScreensEvent.fxml");
//        }
//    }
//    
//    @FXML
//    private void cancelButton (MouseEvent event) {
//            loadNext("SocietyScreensEvent.fxml");
//    }
//    
//    public void loadNext(String destination) {
//        stage = (Stage) societyPage.getScene().getWindow();
//        try {
//            root = FXMLLoader.load(getClass().getResource(destination));
//        } catch (IOException ex) {
//            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    public void setError(String errorMessage) {
//        errorText.setText(errorMessage);
//        errorText.setVisible(true);
//    }
//        
//   public Boolean validateFields() throws SQLException {
//        errorText.setVisible(false);
//
//        if (Utils.extractStringIsEmpty(eventName)) {
//            setError("Event name cannot be empty");
//            return false;
//        }
//        if (Utils.extractStringIsEmpty(startTime)) {
//            setError("Start time cannot be empty");
//            return false;
//        }
//        if (Utils.extractStringIsEmpty(endTime)) {
//            setError("End time cannot be empty");
//            return false;
//        }
//        if (Utils.extractStringIsEmpty(eventDescription)) {
//            setError("Event Description cannot be empty");
//            return false;
//        }
//        statement = openConnection();
//        if (radioButtonChoice == 0) {
//        currentQuery = "UPDATE event SET event_title = '" + eventName.getText() + "', " + "location_type = '" + onCampus.getText() + "', " + "event_text = '" + eventDescription.getText() + "', " + "WHERE email = '" + loggedInUser + "'";
//        System.out.print(currentQuery);
//        int update = statement.executeUpdate(currentQuery);
//        return true;
//}
//        else if (radioButtonChoice == 1) {
//        currentQuery1 = "UPDATE event SET event_title = '" + eventName.getText() + "', " + "location_type = '" + offCampus.getText() + "', " + "event_text = '" + eventDescription.getText() + "', " + "street_no = " + streetNo.getText() + ", " + "street_name = '" + streetName.getText() + "', " + "suburb = '" + suburb.getText() + "', " + "postcode = '" + postcode.getText() + "', " + "society_id = '" +   + " WHERE email = '" + loggedInUser + "'";
//        System.out.print(currentQuery1);
//        int update = statement.executeUpdate(currentQuery1);
//        return true;
//        }
//        return false;
//   }
//    
//        public void populatingComboBox() throws SQLException {
//        statement = openConnection();
//        currentQuery2 = "SELECT securityquestion_wording from security_question";
//        ResultSet rs = statement.executeQuery(currentQuery);
//
//        eventTypeData = FXCollections.observableArrayList();
//        try {
//            while (rs.next()) {
//                int i = 1;
//                securityQuestionData.add(new SecurityQuestion(rs.getString(i)));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        //Format the strings in the ComboBox
//        securityQuestion1.setConverter(new StringConverter<SecurityQuestion>(){
//            @Override
//            public String toString(SecurityQuestion object) {
//                securityQuestion1Wording = object.getSecurityQuestionWording();
//                return object.getSecurityQuestionWording();
//            }
//
//            @Override
//            public SecurityQuestion fromString(String string) {
//                return null;
//            }
//        });
//        securityQuestion2.setConverter(new StringConverter<SecurityQuestion>(){
//            @Override
//            public String toString(SecurityQuestion object) {
//                securityQuestion2Wording = object.getSecurityQuestionWording();
//                return object.getSecurityQuestionWording();
//            }
//
//            @Override
//            public SecurityQuestion fromString(String string) {
//                return null;
//            }
//        });
//        
//        //Data added to comboBox
//        try {
//            securityQuestion1.setItems(eventTypeData);
//            securityQuestion2.setItems(eventTypeData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } //finally {
//        //closeConnection(conn, rs, statement);
//        //}
//    }
//        
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//       
//    }
//}