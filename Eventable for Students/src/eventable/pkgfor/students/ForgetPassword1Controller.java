/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.closeConnection;
import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.SignUp1Controller.conn;
import static eventable.pkgfor.students.SignUp1Controller.rs;
import static eventable.pkgfor.students.SignUp1Controller.statement;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ForgetPassword1Controller extends Application implements Initializable {

    @FXML
    Stage stage;
    Parent root;

    @FXML
    private Button next;
    public TextField email;
    public Text errorText, errorText2;
    @FXML
    public ImageView backButton;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;

    public static String emailAddress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public Boolean validateData() throws SQLException, NullPointerException {
        emailAddress = email.getText().trim().toLowerCase();
        errorText.setVisible(false);
        errorText2.setVisible(false);
        //Check if field is empty
        if (Utils.extractStringIsEmpty(email)) {
            errorText.setVisible(true);
            return false;
        } else {
            //Check if email address exists in database
            statement = openConnection();
            currentQuery = "SELECT email from app_user WHERE email = lower('" + emailAddress + "')";
            ResultSet rs = statement.executeQuery(currentQuery);
            if (rs.next() == false) {
                errorText2.setVisible(true);
                return false;
            }
            while (rs.next()) {
                System.out.println("email address is " + emailAddress);
                System.out.println("result set is " + rs.getString(1));
                if (emailAddress.matches(rs.getString(1).toLowerCase())) {
                } else {
                    errorText2.setVisible(true);
                    return false;
                }
            }
            return true;
        }
    }

    @FXML
    private void nextButton(ActionEvent event) throws SQLException {
        if (validateData()) {
            loadNext("ForgetPassword2.fxml");
        }
    }

    @FXML
    private void backButtonPressed(MouseEvent event) {
        loadNext("Home.fxml");
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
