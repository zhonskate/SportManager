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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import jgpx.model.analysis.Chunk;
import jgpx.model.analysis.TrackData;
import jgpx.model.gpx.Track;
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

    public XYChart.Series<Number, Number> seriesA;
    public XYChart.Series<Number, Number> seriesB;
    public XYChart.Series<Number, Number> seriesC;
    public XYChart.Series<Number, Number> seriesD;
    public XYChart.Series<Number, Number> seriesAt;
    public XYChart.Series<Number, Number> seriesBt;
    public XYChart.Series<Number, Number> seriesCt;
    public XYChart.Series<Number, Number> seriesDt;

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
        chartButt.setDisable(true);
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(loadButt.getScene().getWindow());
        if (file == null) {
            chartButt.setDisable(false);
            return;
        }

        Task<Long> task = new Task<Long>() {
            final Scene _scene = loadButt.getScene();

            @Override
            protected Long call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        _scene.setCursor(Cursor.WAIT);
                    }
                });
                long f = 1;

                JAXBContext jaxbContext = JAXBContext.newInstance(GpxType.class, TrackPointExtensionT.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                JAXBElement<Object> root;

                root = (JAXBElement<Object>) unmarshaller.unmarshal(file);

                GpxType gpx = (GpxType) root.getValue();

                trackData = new TrackData(new Track(gpx.getTrk().get(0)));

                //series
                ObservableList<Chunk> chunks = trackData.getChunks();
                seriesA = new XYChart.Series();
                seriesB = new XYChart.Series();
                seriesC = new XYChart.Series();
                seriesD = new XYChart.Series();
                seriesAt = new XYChart.Series();
                seriesBt = new XYChart.Series();
                seriesCt = new XYChart.Series();
                seriesDt = new XYChart.Series();
                double acumulated = 0;
                double time = 0;
                System.out.println(chunks.size());

                for (int i = 0; i < chunks.size(); i++) {

                    Number xd = ((chunks.get(i).getDistance() + acumulated));
                    time = chunks.get(i).getDuration().toMillis() + time;

                    if (i % 3 == 0) {

                        seriesA.getData().add(new XYChart.Data<>(xd, chunks.get(i).getAvgHeight()));
                        seriesB.getData().add(new XYChart.Data<>(xd, chunks.get(i).getSpeed()));
                        seriesC.getData().add(new XYChart.Data<>(xd, chunks.get(i).getAvgHeartRate()));
                        //if(i%15==0)
                        seriesD.getData().add(new XYChart.Data<>(xd, chunks.get(i).getAvgCadence()));
                    }
                    if (true) {

                        String timexd = String.format("%.0fm", time / 60000);
                        //   System.out.println(timexd);
                        seriesAt.getData().add(new XYChart.Data<>(time, chunks.get(i).getAvgHeight()));
                        seriesBt.getData().add(new XYChart.Data<>(time, chunks.get(i).getSpeed()));
                        seriesCt.getData().add(new XYChart.Data<>(time, chunks.get(i).getAvgHeartRate()));
                        seriesDt.getData().add(new XYChart.Data<>(time, chunks.get(i).getAvgCadence()));
                    }

                    acumulated += chunks.get(i).getDistance();

                }
                Platform.runLater(new Runnable() {
                @Override public void run() {
                _scene.setCursor(Cursor.DEFAULT);
                }});
                return f;
            }

        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    chartButt.setDisable(false);
                    showTrackInfo(trackData);

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Problem with the file");
                    alert.setContentText("There was a problem with the file, choose a correct one.");
                    alert.showAndWait();
                }
            }
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    @FXML

    private void onChart(ActionEvent event) {
        try {
            FXMLLoader charger = new FXMLLoader(getClass().getResource("/view/charts.fxml"));
            Parent root = charger.load();
            Scene scene = new Scene(root);
            ((ChartsController) charger.getController()).setSeries(trackData, seriesA, seriesB, seriesC, seriesD);
            Stage chz = new Stage();
            chz.setTitle("Charts");
            chz.setScene(scene);
            chz.setResizable(true);
            chz.setMinHeight(600.0);
            chz.setMinWidth(750.0);
            chz.show();
        } catch (IOException e) {
        }
    }

    @FXML
    private void onExit(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void showTrackInfo(TrackData trackData) {
        startDateLbl.setText(DateTimeUtils.format(trackData.getStartTime()).substring(0, 10));
        StartTimeLbl.setText(DateTimeUtils.format(trackData.getStartTime()).substring(11));
        TotalDistanceLbl.setText(String.format("%.0f m", trackData.getTotalDistance()));
        DurationLbl.setText(DateTimeUtils.format(trackData.getTotalDuration()).substring(0, 2) + "h "
                + DateTimeUtils.format(trackData.getTotalDuration()).substring(3, 5) + "m "
                + DateTimeUtils.format(trackData.getTotalDuration()).substring(6, 8) + "s");
        ExerciseTimeLnl.setText(DateTimeUtils.format(trackData.getMovingTime()).substring(0, 2) + "h "
                + DateTimeUtils.format(trackData.getMovingTime()).substring(3, 5) + "m "
                + DateTimeUtils.format(trackData.getMovingTime()).substring(6, 8) + "s");
        MaxHrLbl.setText(trackData.getMaxHeartrate() + " bpm");
        AvgHrLbl.setText(trackData.getAverageHeartrate() + " bpm");
        MinHrLbl.setText(trackData.getMinHeartRate() + " bpm");
        MaxSpeedLbl.setText(String.format("%.2f km/h", (trackData.getMaxSpeed() * 3.6)));
        AvgSpeedLbl.setText(String.format("%.2f km/h", (trackData.getAverageSpeed() * 3.6)));
        AccSlopeUpLbl.setText(String.format("%.2f m", trackData.getTotalAscent()));
        AccSlopeDownLbl.setText(String.format("%.2f m", trackData.getTotalDescend()));
        MaxPedalRateLbl.setText(String.format("%d ppm", trackData.getMaxCadence()));
        AvgPedalRateLbl.setText(String.format("%d ppm", trackData.getAverageCadence()));

    }
    
    

}
