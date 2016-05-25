/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;

/**
 * FXML Controller class
 *
 * @author GE60
 */
public class ChartsController implements Initializable {

    @FXML
    private AreaChart<Number, Number> HeightChart;
    @FXML
    private LineChart<Number, Number> SpeedChart;
    @FXML
    private LineChart<Number, Number> HeartRateChart;
    @FXML
    private LineChart<Number, Number> PedalingChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private TextField HRField;
    public XYChart.Series<Number, Number> seriesA;
    public XYChart.Series<Number, Number> seriesB;
    public XYChart.Series<Number, Number> seriesC;
    public XYChart.Series<Number, Number> seriesD;
    public XYChart.Series<Number, Number> seriesAt;
    public XYChart.Series<Number, Number> seriesBt;
    public XYChart.Series<Number, Number> seriesCt;
    public XYChart.Series<Number, Number> seriesDt;
    private TrackData trackData;
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); 
    ObservableList<Chunk> chunks;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onSubmit(ActionEvent event) {
        int maxhr = -1;
        try{maxhr = Integer.parseInt( HRField.getText());}
        catch(NumberFormatException e){HRField.clear();HRField.setPromptText("Imput a number!");}
        
        if(maxhr>=0) setPie(maxhr);
    }

    void setSeries(TrackData trackData,XYChart.Series<Number, Number> seriesA, XYChart.Series<Number, Number> seriesB, XYChart.Series<Number, Number> seriesC, XYChart.Series<Number, Number> seriesD) {
        this.trackData = trackData;
        this.seriesA = seriesA;
        this.seriesB = seriesB;
        this.seriesC = seriesC;
        this.seriesD = seriesD;
        chunks = trackData.getChunks();

        HeightChart.getData().add(seriesA);
        SpeedChart.getData().add(seriesB);
        HeartRateChart.getData().add(seriesC);
        PedalingChart.getData().add(seriesD);

        System.out.print("8--D");

    }
    
    private void setPie(int maxhr){
        pieChartData.remove(0,pieChartData.size());
        int z1 = 0;
        int z2 = 0;
        int z3 = 0;
        int z4 = 0;
        int z5 = 0;
        for (int i = 0; i < chunks.size(); i++) {
            int gethr = (int) chunks.get(i).getAvgHeartRate();

            if (gethr < maxhr * 0.6) {
                z1++;
            } else if (gethr >= maxhr * 0.6 && gethr < maxhr * 0.7) {
                z2++;
            } else if (gethr >= maxhr * 0.7 && gethr < maxhr * 0.8) {
                z3++;
            } else if (gethr >= maxhr * 0.8 && gethr < maxhr * 0.9) {
                z4++;
            } else {
                z5++;
            }
    }
        int totals = z1+z2+z3+z4+z5;
        int z1d = (int) Math.round((double) z1*100/totals);
        int z2d = (int) Math.round((double) z2*100/totals);
        int z3d = (int) Math.round((double) z3*100/totals);
        int z4d = (int) Math.round((double) z4*100/totals);
        int z5d = (int) Math.round((double) z5*100/totals);
        pieChartData.add(new PieChart.Data("Recovery zone " + z1d + "%", z1));
        pieChartData.add(new PieChart.Data("Endurance zone "+ z2d + "%", z2));
        pieChartData.add(new PieChart.Data("Tempo zone "+ z3d + "%", z3));
        pieChartData.add(new PieChart.Data("Threshold zhone "+ z4d + "%", z4));
        pieChartData.add(new PieChart.Data("Anaerobic zone "+ z5d + "%", z5));

        pieChart.setData(pieChartData);
    }

    @FXML
    private void onHR(Event event) {
        if(pieChartData.isEmpty())setPie(0);
    }
    
    
    
}
