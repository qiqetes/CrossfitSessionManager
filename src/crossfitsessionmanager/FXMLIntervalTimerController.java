/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modelo.Grupo;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLIntervalTimerController implements Initializable {

    @FXML
    private Text lDisplayedMsg;
    @FXML
    private Text lTime;
    @FXML
    private Text lTrainingMode;
    @FXML
    private Text lGroup;
    @FXML
    private Text lSesionTipo;
    
    private SesionTipo template;
    private Grupo group;
    private MyCronoTask task;
    private boolean isPaused = false;
    @FXML
    private ProgressIndicator progressIndicator;
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        
        task = new MyCronoTask();      
        task.initTimeVariables(120,3,2,3,2,5);
        //template.getT_calentamiento(), template.getT_ejercicio(), template.getD_ejercicio(),template.getNum_ejercicios(),template.getNum_circuitos(),template.getD_circuito());
        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
        
        lTime.textProperty().bind(task.messageProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
    }    
    
    public void initStage(SesionTipo sT, Grupo g) {
        template = sT;
        group = g;
        lGroup.setText(group.getCodigo());
        lSesionTipo.setText(template.getCodigo());
        
    }

    @FXML
    private void onClickPlay(ActionEvent event) {
        if(isPaused){
            isPaused = false;
            task.startTimer();
        }else{
            isPaused = true;
            task.stopTimer();
        }
        
    }


    @FXML
    private void onClickSkip(ActionEvent event) {
    }

    @FXML
    private void onClickReset(ActionEvent event) {
    }

    @FXML
    private void onClickQuit(ActionEvent event) {
    }
    class MyData{
        StringProperty trainingMode = new SimpleStringProperty();
        StringProperty timerString = new SimpleStringProperty();
        
        public MyData(String timerString,String trainingMode) {
            this.trainingMode.setValue(trainingMode);
            this.timerString.setValue(timerString);
        }
    }
    
    class MyCronoTask extends Task<MyData>{
        boolean started = false;
        long secTotal;
        long secActual;
        long startTime;
        
        boolean finished = false;        
        int warmT, exerT, exerRest, exerN, circN, circRest;
        
        void startTimer(){
            started = true;
            startTime = System.currentTimeMillis();
        }
        void stopTimer(){
            started = false;
        }
        
        void setTimeCrono(int sec){
            secTotal = sec;
            secActual = secTotal;
            startTime = System.currentTimeMillis();
        }
        
        void initTimeVariables(int WarmT, int ExerT, int ExerRest, int ExerN, int CircN,int CircRest){
            this.warmT = WarmT; this.exerT = ExerT; this.exerRest = ExerRest; this.exerN = ExerN; this.circN = CircN; this.circRest = CircRest;
        }
        
        long calcula(){
            secActual = secTotal - (System.currentTimeMillis() - startTime)/1000;
            updateMessage(Utils.toMinSecFormat((int)secActual));
            updateProgress(secActual, secTotal);
            return secActual;
        }
        
        @Override
        protected MyData call() {            
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    if (isCancelled()) {
                        break;
                    }
                }
                if (isCancelled()) {
                    break;
                }
                long next;
                
                while(started){
                    /*If there is warming time*/
                    if(warmT > -1){
                        setTimeCrono(warmT);
                        next = secTotal;
                        while(started && next != 0){
                            next = calcula();
                        }
                    }

                    for(int i = circN; i > 0; i--){ //Repeats the circuit
                        for(int j = exerN; j > 0; j--){ //Number of exercises
                            setTimeCrono(exerT);
                            next = secTotal;
                            while(started && next != -1){
                                next = calcula();
                            }
                            if(j>1){    //Last time there is no resting time for exercises, but for circuit
                                setTimeCrono(exerRest);
                                next = secTotal;
                                while(started && next != -1){
                                    next = calcula();
                                } 
                            }                  
                        }
                        if(i>1){    //Last time there is nor resting time for circuit neither exercise
                            setTimeCrono(circRest);
                            next = secTotal;
                            while(started && next != -1){
                                next = calcula();
                            }
                        }else{
                            System.err.println("Cancelling");
                            updateMessage("0");
                            started = false;
                            cancel();
                        }
                    }   
                }
            }
            return null;
        }
    }
}

