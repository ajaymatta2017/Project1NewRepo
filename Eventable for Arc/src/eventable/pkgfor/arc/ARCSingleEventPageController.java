/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.ARCLandingPageController.eventIdSelected;
import static eventable.pkgfor.arc.ARCLandingPageController.eventNameSelected;
import static eventable.pkgfor.arc.ARCLandingPageController.societyNameSelected;
import static eventable.pkgfor.arc.ARCLandingPageController.startDateSelected;
import static eventable.pkgfor.arc.DBController.openConnection;
import static eventable.pkgfor.arc.SocietyScreensEventsController.statement;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

public class ARCSingleEventPageController implements Initializable {

    @FXML
    Stage stage;
    @FXML
    Parent root;
    @FXML
    private Text eventName;
    @FXML
    private Text societyName;
    @FXML
    private Text startDate;
    @FXML
    private Text attendeeCount;
    @FXML
    private Text email;
    @FXML
    private TableView<ArcEvent_1> tableOfEventsArcExport;
    @FXML
    private TableColumn<ArcEvent_1, String> emailTableColumn;
    @FXML
    private TableColumn<ArcEvent_1, String> studentName;
    @FXML
    private TableColumn<ArcEvent_1, String> zid;

    public static Connection conn;
    public static ResultSet rs;
    public static Statement statement;
    public String currentQuery;
    private String currentQuery1;
    private ObservableList<ArcEvent_1> eventsArcData;
    private String currentQuery2;
    private Statement statement1;
    private String emailAddress;
    private Component modalToComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateAttendeeCount();
            populateEmail();
            settingValues();
            populateTableView();
        } catch (SQLException ex) {
            Logger.getLogger(ARCSingleEventPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void settingValues() {
        eventName.setText(eventNameSelected);
        societyName.setText(societyNameSelected);
        startDate.setText(startDateSelected);
    }

    @FXML
    public void populateEmail() throws SQLException {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }

    @FXML
    public void exportCSV(MouseEvent event) throws SQLException, FileNotFoundException, ParseException {
        exportTableView();
        loadNext("ARCLandingPage.fxml");
    }

    public void loadNext(String destination) {
        stage = (Stage) eventName.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(destination));
        } catch (IOException ex) {
            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void populateAttendeeCount() throws SQLException {
        statement = openConnection();
        currentQuery = "SELECT COUNT(event_actual_attendance) from attendance JOIN event USING (event_id) WHERE event_actual_attendance = 'Y' AND event_id = " + eventIdSelected;
        ResultSet rs = statement.executeQuery(currentQuery);
        while (rs.next()) {
            attendeeCount.setText(rs.getString(1));
        }
    }

    public void populateTableView() throws SQLException {
        statement = openConnection();
        currentQuery1 = "SELECT a.email FROM app_user JOIN society USING (society_id) JOIN event USING (society_id) JOIN attendance a USING(event_id) WHERE event_actual_attendance = 'Y' AND event_id = " + eventIdSelected;

        emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        zid.setCellValueFactory(new PropertyValueFactory<>("zid"));

        //Data added to observable List
        eventsArcData = FXCollections.observableArrayList();

        ResultSet rs1 = statement.executeQuery(currentQuery1);
        while (rs1.next()) {
            emailAddress = rs1.getString(1);
        }

        currentQuery2 = "SELECT email, first_name || ' ' || last_name as student_name, zid FROM app_user where lower(email) = '" + emailAddress + "'";
        ResultSet rs2 = statement.executeQuery(currentQuery2);
        while (rs2.next()) {
            int i = 1;
            eventsArcData.add(new ArcEvent_1(rs2.getString(i), rs2.getString(i + 1), rs2.getString(i + 2)));
            break;
        }

//        try {
//            while (rs1.next()) {
//                int i = 1;
//                String emailAddress = rs1.getString(i);
//                currentQuery2 = "SELECT first_name || ' ' || last_name as student_name, zid FROM app_user where lower(email) = '" + emailAddress + "'";
////                System.out.println(currentQuery2);
//                ResultSet rs2 = statement.executeQuery(currentQuery2);
//                while (rs2.next()) {
//                    eventsArcData.add(new ArcEvent_1(rs1.getString(i), rs2.getString(i), rs2.getString(i + 1)));
//                    break;
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SocietyScreensEventsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        emailTableColumn.setCellFactory(tc -> {
            TableCell<ArcEvent_1, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(emailTableColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        studentName.setCellFactory(tc -> {
            TableCell<ArcEvent_1, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(studentName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        zid.setCellFactory(tc -> {
            TableCell<ArcEvent_1, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(zid.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        //Data added to TableView
        try {
            tableOfEventsArcExport.setItems(eventsArcData);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    public ResultSet exportCSVResultSet() throws SQLException {
        statement = openConnection();
        currentQuery1 = "SELECT email, first_name || ' ' || last_name as student_name, zid FROM app_user where lower(email) = '" + emailAddress + "'";
        rs = statement.executeQuery(currentQuery1);
        return rs;
    }

    public void exportTableView() throws SQLException, FileNotFoundException {
        ResultSet rs1 = exportCSVResultSet();
        String row = null;
        String dataHeaders = null;

        ResultSetMetaData meta = rs1.getMetaData();
        int numberOfColumns = meta.getColumnCount();
        dataHeaders = "\"" + meta.getColumnName(1) + "\"";
        for (int i = 2; i < numberOfColumns + 1; i++) {
            dataHeaders += ",\"" + meta.getColumnName(i).replaceAll("\"", "\\\"") + "\"";
        }
        while (rs1.next()) {
            row = "\"" + rs1.getString(1).replaceAll("\"", "\\\"") + "\"";
            for (int i = 2; i < numberOfColumns + 1; i++) {
                row += ",\"" + rs1.getString(i).replaceAll("\"", "\\\"") + "\"";
            }
            
            String content = dataHeaders+ "\n" + row;
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(modalToComponent) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(file.getPath());
                    fw.write(content);
                    fw.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ARCSingleEventPageController.class.getName()).log(Level.SEVERE, null, ex);
                }

//        PrintWriter csvWriter = new PrintWriter(new File(filename));
//
//        ResultSetMetaData meta = rs.getMetaData();
//        int numberOfColumns = meta.getColumnCount();
//        String dataHeaders = "\"" + meta.getColumnName(1) + "\"";
//        for (int i = 2; i < numberOfColumns + 1; i++) {
//            dataHeaders += ",\"" + meta.getColumnName(i).replaceAll("\"", "\\\"") + "\"";
//        }
//        csvWriter.println(dataHeaders);
//        while (rs.next()) {
//            String row = "\"" + rs.getString(1).replaceAll("\"", "\\\"") + "\"";
//            for (int i = 2; i < numberOfColumns + 1; i++) {
//                row += ",\"" + rs.getString(i).replaceAll("\"", "\\\"") + "\"";
//            }
////            csvWriter.println(row);
//        }
//        csvWriter.close();
    }
}
}
}