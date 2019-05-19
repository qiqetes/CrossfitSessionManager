/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Borja
 */
public class FXMLAddTemplateController implements Initializable {

    @FXML
    private TextField tFCode;
    @FXML
    private TextField tFWarmTime;
    @FXML
    private TextField tFNExercises;
    @FXML
    private TextField tFExerWorkingTime;
    @FXML
    private TextField tFRestExercises;
    @FXML
    private TextField tFCircuitReps;
    @FXML
    private TextField tFRestCircuits;
    @FXML
    private Button bAdd;
    
    private AccesoBD singleton;
    private SesionTipo sesionTipo;
    private TextField[] sesionTipoAttr;
    private Stage primaryStage;
    
    private boolean codeError;
    private boolean warmtimeError;
    private boolean exercisesError;
    private boolean workingtimeError;
    private boolean restexercisesError;
    private boolean restcircuError;
    private boolean repsError;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton = AccesoBD.getInstance();
        sesionTipoAttr = new TextField[]{tFWarmTime,tFNExercises,tFExerWorkingTime,tFRestExercises,tFCircuitReps,tFRestCircuits};
        
        /*Bindings*/
        bindings();
        
        tFCode.textProperty().addListener((observable, oldVal,newVal)->{
            if(Utils.isCodeRepeatedTemplates(singleton, newVal)){
                codeError = true;
                // TODO: add some kind of visual indicator
                addError(tFCode);
            }else{
                codeError = false;
                removeError(tFCode);
            }
            //codeError = (Utils.isCodeRepeated(singleton, newVal)) ? true:false;
        });
        
        tFCircuitReps.textProperty().addListener((observable, oldVal,newVal)->{
            if(!Utils.isNumeric( newVal)){
                repsError = true;
                addError(tFCircuitReps);
                // TODO: add some kind of visual indicator
            }else{
                repsError = false;
                removeError(tFCircuitReps);
            }
        });
        tFExerWorkingTime.textProperty().addListener((observable, oldVal,newVal)->{
            if(!Utils.isNumeric( newVal)){
                workingtimeError = true;
                addError(tFExerWorkingTime);
                // TODO: add some kind of visual indicator
            }else{
                workingtimeError = false;
                removeError(tFExerWorkingTime);
            }
        });
        tFNExercises.textProperty().addListener((observable, oldVal,newVal)->{
            if(!Utils.isNumeric( newVal)){
                exercisesError = true;
                addError(tFNExercises);
                // TODO: add some kind of visual indicator
            }else{
                exercisesError = false;
                removeError(tFNExercises);
            }
        });
        tFRestCircuits.textProperty().addListener((observable, oldVal,newVal)->{
            if(!Utils.isNumeric( newVal)){
                restcircuError = true;
                addError(tFRestCircuits);
                // TODO: add some kind of visual indicator
            }else{
                restcircuError = false;
                removeError(tFRestCircuits);
            }
        });
        tFWarmTime.textProperty().addListener((observable, oldVal,newVal)->{
            if(!Utils.isNumeric( newVal)){
                warmtimeError = true;
                addError(tFWarmTime);
                // TODO: add some kind of visual indicator
            }else{
                warmtimeError = false;
                removeError(tFWarmTime);
            }
        });
        tFRestExercises.textProperty().addListener((observable, oldVal,newVal)->{
            if(!Utils.isNumeric( newVal)){
                restexercisesError = true;
                addError(tFRestExercises);
                // TODO: add some kind of visual indicator
            }else{
                restexercisesError = false;
                removeError(tFRestExercises);
            }
        });
    }    

    @FXML
    private void onClickbAdd(ActionEvent event) {
        if(restcircuError || repsError || workingtimeError || exercisesError || restcircuError || warmtimeError){
            Utils.dialog(Alert.AlertType.ERROR, "Error", "Invalid input", "Please insert numeric values");
        }else if(codeError){
            Utils.dialog(Alert.AlertType.ERROR, "Error", "Invalid input", "This code is already in use");
        }
        else{
            createTemplate();
            FXMLMainWindowController.obsListSessions.add(sesionTipo);
            singleton.getGym().getTiposSesion().add(sesionTipo);
            onClickbCancel(event);
            Utils.dialog(Alert.AlertType.INFORMATION, "Successfully added", "Template was added successfully", null);
            FXMLMainWindowController.alreadySaved = false;            
        }      
    }

    @FXML
    private void onClickbCancel(ActionEvent event) {
        primaryStage.close();
    }
    
    private void createTemplate(){
        sesionTipo = new SesionTipo();
        sesionTipo.setCodigo(tFCode.getText());
        sesionTipo.setT_calentamiento(Integer.parseInt(tFWarmTime.getText()));
        sesionTipo.setNum_ejercicios(Integer.parseInt(tFNExercises.getText()));
        sesionTipo.setT_ejercicio(Integer.parseInt(tFExerWorkingTime.getText()));
        sesionTipo.setD_ejercicio(Integer.parseInt(tFRestExercises.getText()));
        sesionTipo.setNum_circuitos(Integer.parseInt(tFCircuitReps.getText()));
        sesionTipo.setD_circuito(Integer.parseInt(tFRestCircuits.getText()));        
    }
    
    private void bindings(){
        /*Disable buttons: all fields must be filled in*/
        BooleanBinding boolB = tFCode.textProperty().isEmpty().or(
                                tFWarmTime.textProperty().isEmpty().or(
                                tFNExercises.textProperty().isEmpty().or(
                                tFExerWorkingTime.textProperty().isEmpty().or(
                                tFRestExercises.textProperty().isEmpty().or(
                                tFCircuitReps.textProperty().isEmpty().or(
                                tFRestCircuits.textProperty().isEmpty()))))));
        bAdd.disableProperty().bind(boolB);
    }
    
    public void initStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setResizable(false);
    }
    
    
    private void addError(TextField tf){
        if(!tf.getStyleClass().contains("error")){
            tf.getStyleClass().add("error");
        }
    }
    private void removeError(TextField tf){
        if(tf.getStyleClass().contains("error")){
                    tf.getStyleClass().remove("error");
        }
    }
}
