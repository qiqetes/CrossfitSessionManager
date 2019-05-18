/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

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
    
    private SesionTipo template;
    private Grupo group;
    
    private MyCronoTask task;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        task = new MyCronoTask();      
        task.initTimeVariables(0,3,2,3,2,5);
                //template.getT_calentamiento(), template.getT_ejercicio(), template.getD_ejercicio(),template.getNum_ejercicios(),template.getNum_circuitos(),template.getD_circuito());
        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
        
        lTime.textProperty().bind(task.messageProperty());
    }    
    
    public void initStage(SesionTipo sT, Grupo g) {
        template = sT;
        group = g;
    }

    @FXML
    private void onClickPlay(ActionEvent event) {
        task.startTimer();
    }

    class MyCronoTask extends Task<Void>{
        boolean started = false;
        long secTotal;
        long secActual;
        long startTime;
        
        int WarmT, ExerT, ExerRest, ExerN, CircN, CircRest;
        
        void startTimer(){
            started = true;
            startTime = System.currentTimeMillis();
        }
        
        void setTimeCrono(int sec){
            secTotal = sec;
            secActual = secTotal;
            startTime = System.currentTimeMillis();
        }
        
        void initTimeVariables(int WarmT, int ExerT, int ExerRest, int ExerN, int CircN,int CircRest){
            this.WarmT = WarmT; this.ExerT = ExerT; this.ExerRest = ExerRest; this.ExerN = ExerN; this.CircN = CircN; this.CircRest = CircRest;
        }
        
        long calcula() {
            secActual = secTotal - (System.currentTimeMillis() - startTime)/1000;
            updateMessage("" + secActual);
            return secActual;
        }
        
        @Override
        protected Void call() {            
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
                /*If there is warming time*/
                while(started){
                if(WarmT != 0){
                    setTimeCrono(WarmT);
                    next = secTotal;
                    while(started && next != 0){
                        next = calcula();
                    }
                }
                
                for(int i = CircN; i > 0; i--){ //Repeats the circuit
                    for(int j = ExerN; j > 0; j--){ //Number of exercises
                        setTimeCrono(ExerT);
                        next = secTotal;
                        while(started && next != 0){
                            next = calcula();
                        }
                        if(j>1){    //Last time there is no resting time for exercises, but for circuit
                            setTimeCrono(ExerRest);
                            next = secTotal;
                            while(started && next != 0){
                                next = calcula();
                            } 
                        }                  
                    }
                    if(i>1){    //Last time there is nor resting time for circuit neither exercise
                        setTimeCrono(CircRest);
                        next = secTotal;
                        while(started && next != 0){
                            next = calcula();
                        }
                    }else{
                        System.err.println("Cancelling");
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

