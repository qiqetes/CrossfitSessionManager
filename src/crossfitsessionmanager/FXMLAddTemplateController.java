/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    static void initStage(Stage stage) {
    }
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
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton = AccesoBD.getInstance();
        sesionTipoAttr = new TextField[]{tFWarmTime,tFNExercises,tFExerWorkingTime,tFRestExercises,tFCircuitReps,tFRestCircuits};
        bindings();
    }    

    @FXML
    private void onClickbAdd(ActionEvent event) {
        createTemplate();
        FXMLSessionTemplatesController.obsListSessions.add(sesionTipo);
        singleton.getGym().getTiposSesion().add(sesionTipo);
        onClickbCancel(event);
    }

    @FXML
    private void onClickbCancel(ActionEvent event) {
        Node n = (Node) event.getSource();
        n.getScene().getWindow().hide();
    }
    
    private void createTemplate(){
        sesionTipo = new SesionTipo();
        /*Check if the code is already associated to another session*/
        //TO IMPLEMENT
        /*Check if each text field has the proper format input*/
        for(int i = 0; i < sesionTipoAttr.length; i++){
            if(!Utils.isNumeric(sesionTipoAttr[i].getText())){
                Utils.dialog(Alert.AlertType.ERROR, "Error", "Input not valid", "Please, insert a numeric value");
            }
        }
        /*Get proper values and assign them to the template object*/
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
    
}
