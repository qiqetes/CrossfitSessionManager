/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Borja
 */
public class FXMLSessionDetailsController implements Initializable {

    @FXML
    private Label lCode;
    @FXML
    private Label lWarmTime;
    @FXML
    private Label lNofExer;
    @FXML
    private Label lWorkTimeExer;
    @FXML
    private Label lRestTimeExer;
    @FXML
    private Label lRepsCircuit;
    @FXML
    private Label lRestTimeCircuits;
    @FXML
    private Button bOk;
    
    private SesionTipo template;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    

    @FXML
    private void onClickOk(ActionEvent event) {
        bOk.getScene().getWindow().hide();
    }
    
    void initStage(SesionTipo sT) {
        template = sT;  
        lCode.setText(template.getCodigo());
        lWarmTime.setText(" " + template.getT_calentamiento());
        lNofExer.setText(" " + template.getNum_ejercicios());
        lWorkTimeExer.setText(" " + template.getT_ejercicio());
        lRestTimeExer.setText(" " + template.getD_ejercicio());
        lRepsCircuit.setText(" " + template.getNum_circuitos());
        lRestTimeCircuits.setText(" " + template.getD_circuito());
    }
    
}
