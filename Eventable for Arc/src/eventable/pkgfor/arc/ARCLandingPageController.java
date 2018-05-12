/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.DBController.openConnection;
import static eventable.pkgfor.arc.SocietyScreensEventsController.statement;
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
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ARCLandingPageController implements Initializable {
    @FXML
    Stage stage;
    @FXML
    Parent root;
    @FXML
    private TableView<ArcEvent> tableOfArcEventsAll;
    @FXML
    private TableColumn<ArcEvent, String> eventName;
    @FXML
    private TableColumn<ArcEvent, String> societyName;
    @FXML
    private TableColumn<ArcEvent, String> startDate;
    @FXML
    private TableColumn<ArcEvent, String> id;
    @FXML
    private Circle societyPage;
    @FXML
    private TextField email;
    
    public static Connection conn;

    public static ResultSet rs;

    public static Statement statement;
    
    public static String currentQuery;
    private ObservableList<ArcEvent> eventsArcData;
    
    public static String eventNameSelected;
    public static String societyNameSelected;
    public static String startDateSelected;
    public static String eventIdSelected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateEmail();
        try {
            populateTableViewArc();
        } catch (SQLException ex) {
            Logger.getLogger(ARCLandingPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void populateEmail() {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }
    
     public void populateTableViewArc() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM app_user JOIN society USING(SOCIETY_ID) JOIN event using(society_id) WHERE event_start <= '14/MAY/2018'";
        ResultSet rs = statement.executeQuery(currentQuery);
        
        eventName.setCellValueFactory(new PropertyValueFactory<>("event"));
        societyName.setCellValueFactory(new PropertyValueFactory<>("society"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        //Data added to observable List
        eventsArcData = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int i = 1;
                eventsArcData.add(new ArcEvent(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2), rs.getInt(i + 3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        eventName.setCellFactory(tc -> {
            TableCell<ArcEvent, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(eventName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        societyName.setCellFactory(tc -> {
            TableCell<ArcEvent, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(societyName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        startDate.setCellFactory(tc -> {
            TableCell<ArcEvent, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(startDate.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        //Data added to TableView
        try {
            tableOfArcEventsAll.setItems(eventsArcData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }
     
    @FXML
    private void tableviewItemClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 2) {
            ArcEvent eventSelected = tableOfArcEventsAll.getSelectionModel().getSelectedItem();
            eventNameSelected = eventSelected.getEvent();
            societyNameSelected = eventSelected.getSociety();
            startDateSelected = eventSelected.getStartDate();
            eventIdSelected = eventSelected.getId();
            loadNext("ARCSingleEventPage.fxml");
        }
    }

    public void loadNext(String destination) {
        stage = (Stage) societyPage.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}