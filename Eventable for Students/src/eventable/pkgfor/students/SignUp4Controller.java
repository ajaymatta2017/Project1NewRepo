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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author edhopkins
 */
public class SignUp4Controller implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    public ComboBox securityQuestion1;
    public ComboBox securityQuestion2;
    public TextField securityAnswer1;
    public TextField securityAnswer2;
    public Text errorText;

    @FXML
    private Button next;

    private ObservableList<SecurityQuestion> securityQuestionData;

    public static Connection conn;

    public String currentQuery;
    public String currentQuery1;
    
    public String securityQuestion1Wording;
    public String securityQuestion2Wording;

    public static ResultSet rs;

    public static Statement statement;

    public String userPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populatingComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(SignUp4Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void populatingComboBox() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT securityquestion_wording, securityquestion_id from security_question";
        ResultSet rs = statement.executeQuery(currentQuery);

        securityQuestionData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                securityQuestionData.add(new SecurityQuestion(rs.getString(i), Integer.parseInt(rs.getString(i + 1))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_AllController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Format the strings in the ComboBox
        securityQuestion1.setConverter(new StringConverter<SecurityQuestion>(){
            @Override
            public String toString(SecurityQuestion object) {
                securityQuestion1Wording = object.getSecurityQuestionWording();
                return object.getSecurityQuestionWording();
            }

            @Override
            public SecurityQuestion fromString(String string) {
                return null;
            }
        });
        securityQuestion2.setConverter(new StringConverter<SecurityQuestion>(){
            @Override
            public String toString(SecurityQuestion object) {
                securityQuestion2Wording = object.getSecurityQuestionWording();
                return object.getSecurityQuestionWording();
            }

            @Override
            public SecurityQuestion fromString(String string) {
                return null;
            }
        });
        
        //Data added to comboBox
        try {
            securityQuestion1.setItems(securityQuestionData);
            securityQuestion2.setItems(securityQuestionData);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    public boolean validateFields() throws SQLException {
        if (Utils.extractStringIsEmpty(securityQuestion1)) {
            setError("Please select a security question");
            return false;
        }
        if (Utils.extractStringIsEmpty(securityAnswer1)) {
            setError("Please answer your security questions");
            return false;
        }
        if (Utils.extractStringIsEmpty(securityQuestion2)) {
            setError("Please select a security question");
            return false;
        }
        if (Utils.extractStringIsEmpty(securityAnswer2)) {
            setError("Please answer your security questions");
            return false;
        }
        if (securityQuestion1Wording.equals(securityQuestion2Wording)) {
            setError("Please answer 2 unique security questions");
            return false;
        }
        //Adding security questions for user
        statement = openConnection();
        SecurityQuestion securityQuestion1Selected = (SecurityQuestion) securityQuestion1.getSelectionModel().getSelectedItem();
        SecurityQuestion securityQuestion2Selected = (SecurityQuestion) securityQuestion2.getSelectionModel().getSelectedItem();
        currentQuery = "INSERT INTO SECURITY_FEATURE (securityquestion_id, email, security_answer) VALUES ('" + securityQuestion1Selected.getId()  + "', '" + 
                SignUp1Controller.userEmailAddress + "', '" + Utils.extractString(securityAnswer1) + "')";
        System.out.print(currentQuery);
        currentQuery1 = "INSERT INTO SECURITY_FEATURE (securityquestion_id, email, security_answer) VALUES ('" + securityQuestion2Selected.getId() + "', '" + 
                SignUp1Controller.userEmailAddress + "', '" + Utils.extractString(securityAnswer2) + "')";
        int update = statement.executeUpdate(currentQuery);
        int update1 = statement.executeUpdate(currentQuery1);
        return true;
    }

    @FXML
    private void nextButton(ActionEvent event) throws Exception {
        if (validateFields()) {
            System.out.print("Entered nextButton method");
            //LoginController.loggedInUser = SignUp1Controller.userEmailAddress;
//            closeConnection(conn, rs, statement);
            loadNext("Login.fxml");
        }

    }

    public void loadNext(String destination) {
        stage = (Stage) next.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination)); //putting it to 'Seek a Ride' for now, before we know what type of user each person is
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setError(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisible(true);
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
