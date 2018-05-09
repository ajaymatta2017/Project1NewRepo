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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AriSurfacePro
 */
public class StudentScreenSociety_SingleSocietyController extends Application implements Initializable {

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
    private Button backButton;
    @FXML
    private Text societyNameLabel;
    @FXML
    private Text societyDescriptionLabel;
    @FXML
    private Button favouriteSocietyButton;

    public static Connection conn;

    public String currentQuery;
    public String currentQuery1;

    public static ResultSet rs;
    public static ResultSet rs1;

    public static Statement statement;
    public boolean alreadyFavourited;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSocietyData();
    }

    @FXML
    private void favouriteSociety(ActionEvent event) throws SQLException {
        if (!alreadyFavourited) {
            statement = openConnection();
            try {
                currentQuery = "INSERT INTO FAVOURITES (email, society_ID) VALUES ('" + LoginController.loggedInUser + "', '" + StudentScreenSociety_AllController.societySelectedId + "')";
                rs = statement.executeQuery(currentQuery);
                loadNext("StudentScreenSociety_Favourites.fxml");
            } catch (SQLException ex) {
                Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            statement = openConnection();
            try {
                currentQuery = "DELETE FROM FAVOURITES WHERE email = '" + LoginController.loggedInUser + "' AND society_id = '" + StudentScreenSociety_AllController.societySelectedId + "'";
                rs = statement.executeQuery(currentQuery);
                loadNext("StudentScreenSociety_Favourites.fxml");
            } catch (SQLException ex) {
                Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //TODO: Add message box/popup
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
    private void topNavBackButton(MouseEvent event) {
        loadNext("StudentScreenSociety_All.fxml");
    }

    public void loadNext(String destination) {
        stage = (Stage) society.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination)); //putting it to 'Seek a Ride' for now, before we know what type of user each person is
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void populateSocietyData() {
        statement = openConnection();
        try {
            currentQuery1 = "SELECT * FROM FAVOURITES WHERE email = '" + LoginController.loggedInUser + "' AND society_id = '" + StudentScreenSociety_AllController.societySelectedId + "'";
            rs1 = statement.executeQuery(currentQuery1);
            if (rs1.isBeforeFirst()) {
                alreadyFavourited = true;
            } else {
                alreadyFavourited = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        societyNameLabel.setText(StudentScreenSociety_AllController.societySelectedName);
        societyDescriptionLabel.setText(StudentScreenSociety_AllController.societySelectedDescription);

        if (alreadyFavourited) {
            favouriteSocietyButton.setText("Unfavourite");
        } else {
            favouriteSocietyButton.setText("Favourite");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        populateSocietyData();
        stage = (Stage) society.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
