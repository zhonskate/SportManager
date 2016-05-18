/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author GE60
 */
public class ChartsController implements Initializable {

    @FXML
    private LineChart<?, ?> HeightChart;
    @FXML
    private LineChart<?, ?> SpeedChart;
    @FXML
    private LineChart<?, ?> HeartRateChart;
    @FXML
    private LineChart<?, ?> PedalingChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private TextField HRField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSubmit(ActionEvent event) {
    }
    
}
