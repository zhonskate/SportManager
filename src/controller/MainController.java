/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Track;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;
import jgpx.model.jaxb.GpxType;
import jgpx.model.jaxb.TrackPointExtensionT;
import jgpx.util.DateTimeUtils;

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
    private TrackData trackData;
    @FXML
    private Button loadButt;
    @FXML
    private Button chartButt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startDateLbl.setText("");
        StartTimeLbl.setText("");
        DurationLbl.setText("0h 0m 0s");
        ExerciseTimeLnl.setText("0h 0m 0s");
        MaxHrLbl.setText("0 bpm");
        AvgHrLbl.setText("0 bpm");
        MinHrLbl.setText("0 bpm");
        TotalDistanceLbl.setText("0 km");
        MaxSpeedLbl.setText("0 km/h");
        AvgSpeedLbl.setText("0 km/h");
        AccSlopeUpLbl.setText("0 m");
        AccSlopeDownLbl.setText("0 m");
        MaxPedalRateLbl.setText("0 ppm");
        AvgPedalRateLbl.setText("0 ppm");   
        chartButt.setDisable(true);
    }    

    @FXML
    private void onLoad(ActionEvent event) throws JAXBException {
        
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadButt.getScene().getWindow());
        if (file == null) {return;}
        // label.setText("Loading " + file.getName());
        JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Object> root = (JAXBElement<Object>) unmarshaller.unmarshal(file);
        GpxType gpx = (GpxType) root.getValue();

        if (gpx != null) {
            trackData = new TrackData(new jgpx.model.gpx.Track(gpx.getTrk().get(0)));
            showTrackInfo(trackData);
            chartButt.setDisable(false);
            //label.setText("GPX successfully loaded");
        } else {
            //label.setText("Error loading GPX from " + file.getName());
        
    }
    }

    @FXML
   
    private void onChart(ActionEvent event) {
        try{
            FXMLLoader charger = new FXMLLoader(getClass().getResource("/view/charts.fxml"));
                Parent root = charger.load();   
                Scene scene = new Scene(root);
            
                Stage chz = new Stage();
                chz.setTitle("Charts");
                chz.setScene(scene);
                chz.setResizable(true);
                chz.setMinHeight(600.0);
                chz.setMinWidth(750.0);
                chz.show();
        }
        catch (IOException e){}
    }

    @FXML
    private void onExit(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); 
    }
    
     private void showTrackInfo(TrackData trackData) {
        startDateLbl.setText(DateTimeUtils.format(trackData.getStartTime()).substring(0, 10));
        StartTimeLbl.setText(DateTimeUtils.format(trackData.getStartTime()).substring(11));
        TotalDistanceLbl.setText(String.format("%.0f m",trackData.getTotalDistance()));
        DurationLbl.setText(DateTimeUtils.format(trackData.getTotalDuration()).substring(0,2)+"h "+
                DateTimeUtils.format(trackData.getTotalDuration()).substring(3,5)+"m "+
                DateTimeUtils.format(trackData.getTotalDuration()).substring(6,8)+"s");
        ExerciseTimeLnl.setText(DateTimeUtils.format(trackData.getMovingTime()).substring(0,2)+"h "+
                DateTimeUtils.format(trackData.getMovingTime()).substring(3,5)+"m "+
                DateTimeUtils.format(trackData.getMovingTime()).substring(6,8)+"s");
        MaxHrLbl.setText(trackData.getMaxHeartrate() + " bpm");
        AvgHrLbl.setText(trackData.getAverageHeartrate() + " bpm");
        MinHrLbl.setText(trackData.getMinHeartRate() + " bpm");
        MaxSpeedLbl.setText(String.format("%.2f km/h", (trackData.getMaxSpeed()*3.6)));
        AvgSpeedLbl.setText(String.format("%.2f km/h", (trackData.getAverageSpeed()*3.6)));
        AccSlopeUpLbl.setText(String.format("%.2f m", trackData.getTotalAscent()));
        AccSlopeDownLbl.setText(String.format("%.2f m", trackData.getTotalDescend()));
        MaxPedalRateLbl.setText(String.format("%d ppm", trackData.getMaxCadence()));
        AvgPedalRateLbl.setText(String.format("%d ppm", trackData.getAverageCadence()));      
          
    }
     
   
    
    
}
