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
        task.setTimeCrono(30);
        
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
        
        void startTimer(){
            started = true;
            startTime = System.currentTimeMillis();
        }
        
        void setTimeCrono(int sec){
            secTotal = sec;
            secActual = secTotal;
        }
        
        void calcula() {
            secActual = secTotal - (System.currentTimeMillis() - startTime)/1000;
            updateMessage("" + secActual);
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
                while(started){
                    calcula();
                }
            }
            return null;
        }
    }
}

