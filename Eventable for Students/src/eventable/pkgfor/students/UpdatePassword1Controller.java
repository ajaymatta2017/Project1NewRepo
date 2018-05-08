/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.StudentScreenEvents_AllController.statement;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpdatePassword1Controller extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;
    @FXML
    private Button next;
    @FXML
    public TextField securityQuestion, answer;
    @FXML
    public Text errorText, errorText2;
    @FXML
    public ImageView backButton;
    
    public static Connection conn;

    public String currentQuery;
    public String currentQuery1;

    public static ResultSet rs;

    public static Statement statement;
    
    public String securityQuestionString;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            inputSecurityQuestion();
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPassword2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inputSecurityQuestion() throws SQLException {
        //Security Question
        statement = openConnection();
        currentQuery = "SELECT securityquestion_wording FROM security_question JOIN security_feature USING(securityquestion_id) WHERE email = '" + LoginController.loggedInUser + "'";
        System.out.println(currentQuery);
        ResultSet rs = statement.executeQuery(currentQuery);
        while (rs.next()) {
            securityQuestionString = rs.getString(1); 
            securityQuestion.setText(securityQuestionString);
        }
    }

    public boolean verifyIdentity() throws SQLException {
        errorText.setVisible(false);
        errorText2.setVisible(false);
        
        if (Utils.extractStringIsEmpty(answer)) {
            errorText.setVisible(true);
            return false;
        }
        
        //Security Answer
        currentQuery1 = "SELECT security_answer FROM security_feature JOIN security_question USING(securityquestion_id) WHERE email = '" + LoginController.loggedInUser + "'" + " AND securityquestion_wording = '" + securityQuestionString + "'";
        ResultSet rs1 = statement.executeQuery(currentQuery1);
        String securityAnswer = answer.getText().trim().toLowerCase();
        while (rs1.next()) {
            System.out.print(rs1.getString(1));
            if (securityAnswer.matches(rs1.getString(1).toLowerCase())) {
                return true;
            } else {
                errorText2.setVisible(true);
                return false;
            }
    }
        return false;
    }
    
    @FXML
    private void nextButton(MouseEvent event) throws SQLException {
        if (verifyIdentity()) {
            loadNext("UpdatePassword.fxml");
        }
    }
    @FXML
    private void backButtonPressed(MouseEvent event) {
        loadNext("StudentScreenProfile.fxml");
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
        stage = (Stage) next.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    
}