/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import static eventable.pkgfor.arc.DBController.openConnection;
import eventable.pkgfor.arc.SocietyScreensEventsController.*;
import static eventable.pkgfor.arc.SocietyScreensEventsController.eventId;
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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class SocietyScreens_SingleFeedbackController implements Initializable {

    @FXML
    Stage stage;
    @FXML
    Parent root;
    @FXML
    private Circle societyPage;
    @FXML
    private Button backButton;
    @FXML
    private Text errorText;
    @FXML
    private Text email;
    @FXML
    private Text eventName;
    @FXML
    private Text averageRating;
    @FXML
    private Text percentageTotalYes;
    @FXML
    private Text totalYes;
    @FXML
    private Text percentageTotalNo;
    @FXML
    private Text totalNo;
    @FXML
    private Text percentageTotalYes1;
    @FXML
    private Text totalYes1;
    @FXML
    private Text percentageTotalNo1;
    @FXML
    private Text totalNo1;
    @FXML
    private TableView<Feedback> tableOfEventsFeedbackQ;
    @FXML
    private TableColumn<Feedback, String> feedbackQuestion;
    @FXML
    private TableColumn<Feedback, String> feedbackAnswer;

    public Statement statement;
    public static Connection conn;
    public static ResultSet rs;
    private String currentQuery;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateEventName();
            populateEmail();
            calculateAverageRating();
            populateTotalYes();
            populateTotalNo();
            populatePercentageTotalYes();
            populatePercentageTotalNo();
            populateTotalYes1();
            populateTotalNo1();
            populatePercentageTotalYes1();
            populatePercentageTotalNo1();
            //Populate TableView for Feedback Question and Answers
            ResultSet outputFeedback = queryPopulateTableView("select question_wording, feedback_answer from feedback join feedback_question using (question_id) where event_id = " + eventId + " and (question_id = 2 or question_id = 3) order by question_wording");
            ObservableList<Feedback> eventsDataFeedback = observableListFeedbackQuestionPopulation(outputFeedback);
            TableViewFeedbackSetup(eventsDataFeedback);
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreens_SingleFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void populateEventName() {
        eventName.setText(SocietyScreensEventsController.eventName);
    }

    @FXML
    private void populateEmail() {
        email.setText(ARCSocietyHomeController.loggedInUser);
    }

    private void calculateAverageRating() throws SQLException {
        statement = openConnection();
        currentQuery = "select round(avg(feedback_answer),1) from feedback where question_id = 1 and event_id = " + eventId;
        rs = statement.executeQuery(currentQuery);
        while (rs.next()) {
            averageRating.setText(rs.getString(1));
        }
    }

    private void populateTotalYes() throws SQLException {
        statement = openConnection();
        String currentQuery1 = "select count(feedback_answer) from feedback where question_id = 4 and lower(feedback_answer) = 'yes' and event_id = " + eventId;
        ResultSet rs1 = statement.executeQuery(currentQuery1);
        while (rs1.next()) {
            totalYes.setText(rs1.getString(1));
        }
    }

    private void populateTotalNo() throws SQLException {
        statement = openConnection();
        String currentQuery2 = "select count(feedback_answer) from feedback where question_id = 4 and lower(feedback_answer) = 'no' and event_id = " + eventId;
        ResultSet rs2 = statement.executeQuery(currentQuery2);
        while (rs2.next()) {
            totalNo.setText(rs2.getString(1));
        }
    }

    private void populateTotalYes1() throws SQLException {
        statement = openConnection();
        String currentQuery4 = "select count(feedback_answer) from feedback where question_id = 5 and lower(feedback_answer) = 'yes' and event_id = " + eventId;
        ResultSet rs4 = statement.executeQuery(currentQuery4);
        while (rs4.next()) {
            totalYes1.setText(rs4.getString(1));
        }
    }

    private void populateTotalNo1() throws SQLException {
        statement = openConnection();
        String currentQuery5 = "select count(feedback_answer) from feedback where question_id = 5 and lower(feedback_answer) = 'no' and event_id = " + eventId;
        ResultSet rs5 = statement.executeQuery(currentQuery5);
        while (rs5.next()) {
            totalNo1.setText(rs5.getString(1));
        }
    }

    private void populatePercentageTotalYes() throws SQLException {
        statement = openConnection();
        String currentQuery3 = "select (count(feedback_answer)* 100/(select count(*) from feedback where question_id = 4 and lower(feedback_answer) = 'yes' and event_id = " + eventId + " )) from feedback where question_id = 4 and lower(feedback_answer) = 'yes' and event_id = " + eventId + " group by feedback_answer";
        ResultSet rs3 = statement.executeQuery(currentQuery3);
        if (!rs3.isBeforeFirst()) {
            percentageTotalYes.setText("0");
            percentageTotalYes.setVisible(true);
        }
        while (rs3.next()) {
            percentageTotalYes.setText(rs3.getString(1));
        }
    }

    private void populatePercentageTotalNo() throws SQLException {
        statement = openConnection();
        String currentQuery3 = "select (count(feedback_answer)* 100/(select count(*) from feedback where question_id = 4 and lower(feedback_answer) = 'no' and event_id = " + eventId + " )) from feedback where question_id = 4 and lower(feedback_answer) = 'no' and event_id = " + eventId + " group by feedback_answer";
        ResultSet rs3 = statement.executeQuery(currentQuery3);
        if (!rs3.isBeforeFirst()) {
            percentageTotalNo.setText("0");
            percentageTotalNo.setVisible(true);
        }
        while (rs3.next()) {
            percentageTotalNo.setText(rs3.getString(1));
        }
    }

    private void populatePercentageTotalYes1() throws SQLException {
        statement = openConnection();
        String currentQuery6 = "select (count(feedback_answer)* 100/(select count(*) from feedback where question_id = 5 and lower(feedback_answer) = 'yes' and event_id = " + eventId + " )) from feedback where question_id = 5 and lower(feedback_answer) = 'yes' and event_id = " + eventId + " group by feedback_answer";
        ResultSet rs6 = statement.executeQuery(currentQuery6);
        if (!rs6.isBeforeFirst()) {
            percentageTotalYes1.setText("0");
            percentageTotalYes1.setVisible(true);
        }
        while (rs6.next()) {
            percentageTotalYes1.setText(rs6.getString(1));
        }
    }

    private void populatePercentageTotalNo1() throws SQLException {
        statement = openConnection();
        String currentQuery7 = "select (count(feedback_answer)* 100/(select count(*) from feedback where question_id = 5 and lower(feedback_answer) = 'no' and event_id = " + eventId + " )) from feedback where question_id = 5 and lower(feedback_answer) = 'no' and event_id = " + eventId + " group by feedback_answer";
        ResultSet rs7 = statement.executeQuery(currentQuery7);
        if (!rs7.isBeforeFirst()) {
            percentageTotalNo1.setText("0");
            percentageTotalNo1.setVisible(true);
        }
        while (rs7.next()) {
            percentageTotalNo1.setText(rs7.getString(1));
        }
    }

    public ResultSet queryPopulateTableView(String currentQuery) throws SQLException {
        statement = openConnection();
        ResultSet rs8 = statement.executeQuery(currentQuery);
        return rs8;
    }

    private ObservableList<Feedback> observableListFeedbackQuestionPopulation(ResultSet rs1) throws SQLException {
        ObservableList<Feedback> eventsFeedbackData = FXCollections.observableArrayList();
        try {
            while (rs1.next()) {
                int i = 1;
                eventsFeedbackData.add(new Feedback(rs1.getString(i), rs1.getString(i + 1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SocietyScreens_SingleFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eventsFeedbackData;
    }

    public void TableViewFeedbackSetup(ObservableList<Feedback> data) {
        feedbackQuestion.setCellValueFactory(new PropertyValueFactory<>("feedbackQuestion"));
        feedbackAnswer.setCellValueFactory(new PropertyValueFactory<>("feedbackAnswer"));

        feedbackQuestion.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(feedbackQuestion.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        feedbackAnswer.setCellFactory(tc -> {
            TableCell<Feedback, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(feedbackAnswer.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        
        try {
            tableOfEventsFeedbackQ.setItems(data);
            //tableofEvents.getColumns().setAll(event, startDate, location);
        } catch (Exception e) {
            e.printStackTrace();
        } //finally {
        //closeConnection(conn, rs, statement);
        //}
    }

    @FXML
    private void backButtonMethod(MouseEvent event) {
        loadNext("SocietyScreensEvents.fxml");
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
