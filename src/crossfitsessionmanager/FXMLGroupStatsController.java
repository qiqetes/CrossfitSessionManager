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
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLGroupStatsController implements Initializable {
    @FXML
    private Text lGroupCode;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private Text lGroupDescription;
    @FXML
    private Text lDefSessionCode;
    @FXML
    private Text lLastSessionDur;
    
    private Grupo grupo;
    private Stage primaryStage;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;


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
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();        

        xAxis.setLabel("Date");
        yAxis.setLabel("Minutes");
        
        /*Initialize series and show just the last 10 sessions (in case the group has done more than 10)*/
        int i = (grupo.getSesiones().size() > 10) ? (grupo.getSesiones().size()-10) : 0;
        XYChart.Series seriesTime = new XYChart.Series();
        seriesTime.setName("RealTime");
        XYChart.Series seriesRest = new XYChart.Series();
        seriesRest.setName("SessionRestTime");
        XYChart.Series seriesFanTime = new XYChart.Series();
        seriesFanTime.setName("SessionTime");
        for(; i < grupo.getSesiones().size(); i++){
            Duration duration = grupo.getSesiones().get(i).getDuracion();
            LocalDateTime date = grupo.getSesiones().get(i).getFecha();
            
//            series.setName(grupo.getSesiones().get(i).getTipo().getCodigo());
            seriesTime.getData().add(new XYChart.Data( date.toString(),duration.getSeconds()/60)); 
            SesionTipo t = grupo.getSesiones().get(i).getTipo();
            int workinTime = t.getNum_ejercicios()*t.getT_ejercicio() + t.getT_calentamiento();
            int restinTime = (t.getNum_ejercicios() - 1)* t.getD_ejercicio() + (t.getNum_circuitos()-1)*t.getD_circuito();
            seriesRest.getData().add(new XYChart.Data( date.toString(), restinTime/60)); 
            seriesFanTime.getData().add(new XYChart.Data( date.toString(),workinTime/60)); 
        }
         lineChart.getData().addAll(seriesTime,seriesFanTime,seriesRest);
    }
}
