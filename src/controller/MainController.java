/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author GE60
 */
public class MainController implements Initializable {

    @FXML
    private Label startDateLbl;
    @FXML
    private Label StartTimeLbl;
    @FXML
    private Label DurationLbl;
    @FXML
    private Label ExerciseTimeLnl;
    @FXML
    private Label MaxHrLbl;
    @FXML
    private Label AvgHrLbl;
    @FXML
    private Label MinHrLbl;
    @FXML
    private Label TotalDistanceLbl;
    @FXML
    private Label MaxSpeedLbl;
    @FXML
    private Label AvgSpeedLbl;
    @FXML
    private Label AccSlopeUpLbl;
    @FXML
    private Label AccSlopeDownLbl;
    @FXML
    private Label MaxPedalRateLbl;
    @FXML
    private Label AvgPedalRateLbl;
    @FXML
    private Button LoadButt;
    @FXML
    private Button ChartButt;
    @FXML
    private Button ExitButt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
