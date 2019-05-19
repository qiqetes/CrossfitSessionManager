/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import com.gluonhq.charm.glisten.control.ProgressIndicator;
import javafx.scene.media.AudioClip;
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
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    @FXML
    private ProgressIndicator progressIndicator;

    private Stage primaryStage;
    private SesionTipo template;
    private Grupo group;
    private MyCronoTask task;
    private Thread hilo;
    private boolean isPaused = true;
    private int warmT, exerT, exerRest, exerN, circN, circRest;

    private int counter = 0;
    private Training[] trainings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initStage(SesionTipo sT, Grupo g, Stage stage) {
        template = sT;
        group = g;
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);

        /*Set Text to labels*/
        lGroup.setText(group.getCodigo());
        lSesionTipo.setText(template.getCodigo());

        /*Initialize global variables to pass to the task*/
        warmT = template.getT_calentamiento();
        exerT = template.getT_ejercicio();
        exerRest = template.getD_ejercicio();
        exerN = template.getNum_ejercicios();
        circN = template.getNum_circuitos();
        circRest = template.getD_circuito();

        fillTrainings();

        task = new MyCronoTask();
        task.setTimeCrono(trainings[counter].seg);
        lTrainingMode.setText(trainings[counter].modo);
        hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
        /*Bingdings*/

        bindings();

    }

    void bindings() {
        lTime.textProperty().bind(task.messageProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.progressProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal.floatValue() == 0) {
                System.out.println(counter);
                // Unbind
                lTime.textProperty().unbind();
                progressIndicator.progressProperty().unbind();

                counter++;
                if (counter < trainings.length) {
                    task = new MyCronoTask();
                    task.setTimeCrono(trainings[counter].seg);
                    hilo = new Thread(task);
                    hilo.setDaemon(true);
                    hilo.start();
                    lTrainingMode.setText(trainings[counter].modo);
                    lTime.textProperty().bind(task.messageProperty());
                    progressIndicator.progressProperty().bind(task.progressProperty());
                    task.play();
                } else {

                    lTime.textProperty().unbind();
                    lTime.setText("0:00");
                    lTrainingMode.setText("SESSION FINISHED!!!");
                }
            }

        });
        lTime.textProperty().addListener((obs,oldVal,newVal)->{
            if(newVal.equals("0:01")){
                System.out.println("Sound");
                trainings[counter].efecto.play();
            }
        });
    }

    void fillTrainings() {
        int isWarming = warmT > 0 ? 1 : 0;
        trainings = new Training[circN * (exerN * 2 - 1) + isWarming + circN - 1];
        int count = 0;
        AudioClip effect;
        
        if (isWarming == 1) {
            effect = new AudioClip(getClass().getResource("/resources/bell.mp3").toString());
            trainings[0] = new Training(warmT, "WARMING TIME", effect);
            count = 1;
        }
        for (int k = 0; k < circN; k++) {
            for (int l = 0; l < exerN; l++) {
                effect = new AudioClip(getClass().getResource("/resources/bell.mp3").toString()    );
                trainings[count] = new Training(exerT, "EXERCISE TIME!", effect);
                if (l != exerN - 1) {
                    effect = new AudioClip(getClass().getResource("/resources/321.mp3").toString()    );
                    trainings[count + 1] = new Training(exerRest, "REST TIME", effect);
                    count += 2;
                } else {
                    count++;
                }
            }
            if (k != circN - 1) {
                effect = new AudioClip(getClass().getResource("/resources/321.mp3").toString()    );
                trainings[count] = new Training(circRest, "CIRCUIT REST", effect);
                count++;
            }else{ //If it is the last exercise time
                effect = new AudioClip(getClass().getResource("/resources/Whistle.mp3").toString()    );
                trainings[count-1] = new Training(exerT, "EXERCISE TIME!", effect);
            }
        }
        for (int i = 0; i < trainings.length; i++) {
            System.out.println(trainings[i].modo);
        }
    }

    
    
    /*Training objects*/
    class Training {

        int seg;
        String modo;
        AudioClip efecto;

        public Training(int s, String m, AudioClip e) {
            seg = s;
            modo = m;
            efecto = e;
        }
    }

    
    
    
    @FXML
    private void onClickPlay(ActionEvent event) {

        if (isPaused) {
            task.play();
            isPaused = false;
        } else {
            isPaused = true;
            task.pause();
        }
    }

    @FXML
    private void onClickSkip(ActionEvent event) {
    }

    @FXML
    private void onClickReset(ActionEvent event) {
        System.out.println("Reseting");

        lTime.textProperty().unbind();
        progressIndicator.progressProperty().unbind();
        progressIndicator.setRotate(0);
        isPaused = true;

        task = new MyCronoTask();
        hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
        lTime.textProperty().bind(task.messageProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
    }

    @FXML
    private void onClickQuit(ActionEvent event) {
        primaryStage.close();
        // Todo: save the session on group
    }

    private void setTimes() {

    }

    class MyCronoTask extends Task<Void> {

        boolean started = false;
        long secTotal;
        long secActual;
        long startTime;
        long timePaused = 0;

        boolean paused = true;

        void setTimeCrono(int sec) {
            secTotal = sec;
            secActual = secTotal;
            startTime = System.currentTimeMillis();
        }

        void pause() {
            paused = true;
        }

        void play() {
            paused = false;
        }

        long calcula() {
            secActual = secTotal + timePaused - (System.currentTimeMillis() - startTime) / 1000;
            updateMessage(Utils.toMinSecFormat((int) secActual));
            updateProgress(secActual, secTotal);
            return secActual;
        }

        @Override
        protected Void call() throws Exception {
            while (true) {
                Thread.sleep(100);
                if (isCancelled()) {
                    break;
                }
                while (paused) {
                    Thread.sleep(1000);
                    timePaused += 1;
                }
                if (calcula() < 0) {
                    return null;
                }

            }
            return null;
        }
    }
}
