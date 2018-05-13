/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Ajay Matta
 */
public class SocietyScreens_SingleEvent1Controller implements Initializable {

    @FXML
    private Circle societyPage;
    @FXML
    private TextField email;
    @FXML
    private TextField eventName;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField startTime;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField endTIme;
    @FXML
    private RadioButton onCampus;
    @FXML
    private RadioButton offCampus;
    @FXML
    private TextField streetNo;
    @FXML
    private TextField streetName;
    @FXML
    private TextField buildingName;
    @FXML
    private TextField suburb;
    @FXML
    private TextField postcode;
    @FXML
    private TextField roomNo;
    @FXML
    private TextField eventDescription;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private Text errorText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void populateEmail(ActionEvent event) {
    }

    @FXML
    private void cancelButton(MouseEvent event) {
    }

    @FXML
    private void createEventButton(MouseEvent event) {
    }
    
}
