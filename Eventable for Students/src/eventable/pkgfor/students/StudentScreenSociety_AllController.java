/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import static eventable.pkgfor.students.DBController.openConnection;
import static eventable.pkgfor.students.LoginController.userInSystem;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentScreenSociety_AllController extends Application implements Initializable {

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
    private TextField searchField;
    @FXML
    public TableView<FavouriteSocieties> tableOfSocieties;
    @FXML
    public TableColumn<FavouriteSocieties, String> societyName;
    @FXML
    public TableColumn<FavouriteSocieties, String> societyDescription;

    ObservableList<FavouriteSocieties> societyData;

    public static Connection conn;

    public String currentQuery;

    public static ResultSet rs;

    public static Statement statement;

    public static String societySelectedName;
    public static String societySelectedDescription;
    public static String societySelectedId;
    
    public void populateTableView() throws SQLException {
        String loggedInUser = LoginController.loggedInUser;
        statement = openConnection();
        currentQuery = "SELECT society_name, society_description, society_id FROM society";
        ResultSet rs = statement.executeQuery(currentQuery);

        societyName.setCellValueFactory(new PropertyValueFactory<>("societyName"));
        societyDescription.setCellValueFactory(new PropertyValueFactory<>("societyDescription"));

        //Data added to observable List
        societyData = FXCollections.observableArrayList();

        try {
            while (rs.next()) {
                int i = 1;
                societyData.add(new FavouriteSocieties(rs.getString(i), rs.getString(i + 1), Integer.parseInt(rs.getString(i + 2))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        societyDescription.setCellFactory(tc -> {
            TableCell<FavouriteSocieties, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyDescription.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        societyName.setCellFactory(tc -> {
            TableCell<FavouriteSocieties, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableOfSocieties.setItems(societyData);
//            tableOfSocieties.setFixedCellSize(60.0);
        } catch (Exception e) {
            e.printStackTrace();
        }// finally {
        //  closeConnection(conn, rs, statement);
        //}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateTableView();
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void bottomNavSocietyButton(MouseEvent event) {
        loadNext("StudentScreenSociety_All.fxml");
    }

    @FXML
    private void bottomNavCodeButton(MouseEvent event) {
        if (userInSystem) {
            loadNext("StudentScreenCode.fxml");
        }
    }

    @FXML
    private void bottomNavEventsButton(MouseEvent event) {
        loadNext("StudentScreenEvents_All.fxml");
    }

    @FXML
    private void bottomNavFeedbackButton(MouseEvent event) {
        if (userInSystem) {
            loadNext("StudentScreenFeedback_Feedback.fxml");
        }
    }

    @FXML
    private void bottomNavProfileButton(MouseEvent event) {
        if (userInSystem) {
            loadNext("StudentScreenProfile.fxml");
        }
    }

    @FXML
    private void topNavAll(MouseEvent event) {
        loadNext("StudentScreenSociety_All.fxml");
    }

    @FXML
    private void topNavFavourites(MouseEvent event) {
        if (userInSystem) {
            loadNext("StudentScreenSociety_Favourites.fxml");
        }
    }
    
    @FXML
    private void search (KeyEvent event) throws SQLException{
        populateTableView("SELECT society_name, society_description, society_id FROM society WHERE (society_description LIKE '%" + searchField.getText().trim() + "%' OR society_name LIKE '%" + searchField.getText().trim() + "')");
    }

    public void loadNext(String destination) {
        stage = (Stage) society.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void tableviewItemClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            FavouriteSocieties societySelected = tableOfSocieties.getSelectionModel().getSelectedItem();
            societySelectedName = societySelected.getSocietyName();
            societySelectedDescription = societySelected.getSocietyDescription();
            societySelectedId = societySelected.getId();
            loadNext("StudentScreenSociety_SingleSociety.fxml");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        populateTableView();
        stage = (Stage) society.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void populateTableView(String currentQuery) throws SQLException {
        String loggedInUser = LoginController.loggedInUser;
        statement = openConnection();
        ResultSet rs = statement.executeQuery(currentQuery);

        societyName.setCellValueFactory(new PropertyValueFactory<>("societyName"));
        societyDescription.setCellValueFactory(new PropertyValueFactory<>("societyDescription"));

        //Data added to observable List
        societyData = FXCollections.observableArrayList();

        try {
            while (rs.next()) {
                int i = 1;
                societyData.add(new FavouriteSocieties(rs.getString(i), rs.getString(i + 1), Integer.parseInt(rs.getString(i + 2))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentScreenEvents_FavouritesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        societyDescription.setCellFactory(tc -> {
            TableCell<FavouriteSocieties, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyDescription.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        societyName.setCellFactory(tc -> {
            TableCell<FavouriteSocieties, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //Data added to TableView
        try {
            tableOfSocieties.setItems(societyData);
//            tableOfSocieties.setFixedCellSize(60.0);
        } catch (Exception e) {
            e.printStackTrace();
        }// finally {
        //  closeConnection(conn, rs, statement);
        //}
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
