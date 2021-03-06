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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    private Text email;
    @FXML
    private Button AToZButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button goButton;
    @FXML
    private Button orderByDate;
    @FXML
    private Button orderByCount;

    public static Connection conn;

    public static ResultSet rs;

    public static Statement statement;

    public static String currentQuery;
    public static String currentQuery1;
    
    private ObservableList<ArcEvent> eventsArcData;
    public static String eventNameSelected;
    public static String societyNameSelected;
    public static String startDateSelected;
    public static int eventIdSelected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateEmail();
        orderByDate.setText("Most Recent");
        try {
            populateTableViewArc("SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM society JOIN event using(society_id) WHERE event_start <= '14/MAY/2018'");
        } catch (SQLException ex) {
            Logger.getLogger(ARCLandingPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void populateEmail() {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }
   
    @FXML
    private void orderByDateButton(MouseEvent event) throws SQLException {
        if (orderByDate.getText().toLowerCase().matches("most recent")){
//            System.out.println("Method 1 Entered");
//            orderByDate.setText("Least Recent");
            populateTableViewArc("SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM society JOIN event using(society_id) WHERE event_start <= '14/MAY/2018' ORDER BY event_start DESC");
        }
//        if (orderByDate.getText().toLowerCase().matches("least recent")) {
//            System.out.println("Method 2 Entered");
////            Label mostRecent = new Label("Most Recent");
//            orderByDate.setText("Most Recent");
//            orderByDate.setVisible(true);
//            populateTableViewArc("SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start FROM society JOIN event using(society_id) WHERE event_start <= '14/MAY/2018' ORDER BY event_start DESC");
//        }
    }
    
    //NEED TO COMPLETE
//    @FXML
//    private void orderByCountButton(MouseEvent event) {
//        currentQuery1 = "SELECT COUNT(
//    }   

    @FXML
    private void alphabeticalSort(MouseEvent event) throws SQLException {
        if (AToZButton.getText().toLowerCase().equals("a-z")) {
            populateTableViewArc("SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM society JOIN event using(society_id) WHERE event_start <= '14/MAY/2018' ORDER BY event_title ASC");
            AToZButton.setText("Z-A");
        } else {
            populateTableViewArc("SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM society JOIN event using(society_id) WHERE event_start <= '14/MAY/2018' ORDER BY event_title DESC");
            AToZButton.setText("A-Z");
        }
    }

    @FXML
    private void search(KeyEvent event) throws SQLException {
        populateTableViewArc("SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM society JOIN event using(society_id) WHERE event_start <= '14/MAY/2018' AND"
                + "(lower(society_name) LIKE '%" + searchField.getText().trim().toLowerCase() + "%' OR lower(event_title) LIKE '%"
                + searchField.getText().trim().toLowerCase() + "')");
    }

    public void populateTableViewArc(String currentQuery) throws SQLException {
        statement = openConnection();
        //currentQuery = "SELECT DISTINCT society_name, event_title, CAST(TO_CHAR(event_start, 'dd/MON/yy') AS VARCHAR2(50)) event_start, event_id FROM app_user JOIN society USING(SOCIETY_ID) JOIN event using(society_id) WHERE event_start <= '14/MAY/2018'";
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
                eventsArcData.add(new ArcEvent(rs.getString(i), rs.getString(i + 1), rs.getString(i + 2), rs.getInt(i+3)));
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