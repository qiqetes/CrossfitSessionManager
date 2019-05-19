/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.Grupo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLGroupStatsController implements Initializable {
    @FXML
    private Text lGroupCode;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Text lGroupDescription;
    @FXML
    private Text lDefSessionCode;
    @FXML
    private Text lLastSessionDur;
    
    private Grupo grupo;
    private Stage primaryStage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    void initStage(Grupo g, Stage s) {
        grupo = g;
        primaryStage = s;
        initChart();        
    }
    
    private void initChart(){
        /*LineChart initialization*/
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();        
        xAxis.setLabel("Date");
        yAxis.setLabel("Minutes");
        lineChart = new LineChart(xAxis,yAxis);
        
        /*Initialize series and show just the last 10 sessions (in case the group has done more than 10)*/
        int i = (grupo.getSesiones().size() > 10) ? (grupo.getSesiones().size()-10) : 0;
        for(; i < grupo.getSesiones().size(); i++){
            Duration duration = grupo.getSesiones().get(i).getDuracion();
            LocalDateTime date = grupo.getSesiones().get(i).getFecha();
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(duration, date));
            
            lineChart.getData().add(series);
        }
    }
}
