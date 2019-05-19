/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Borja
 */
public class FXMLSessionDetailsController implements Initializable {

    @FXML
    private Text lCode;
    @FXML
    private Text lWarmTime;
    @FXML
    private Text lNofExer;
    @FXML
    private Text lWorkTimeExer;
    @FXML
    private Text lRestTimeExer;
    @FXML
    private Text lRepsCircuit;
    @FXML
    private Text lRestTimeCircuits;
    
    private SesionTipo template;
    private Stage primaryStage;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {            
    }    

    @FXML
    private void onClickOk(ActionEvent event) {
        lCode.getScene().getWindow().hide();
    }
    
    void initStage(SesionTipo sT, Stage stage) {
        template = sT;  
        primaryStage = stage;
        primaryStage.setResizable(false);
        
        lCode.setText(template.getCodigo());
        lWarmTime.setText(template.getT_calentamiento() + " seconds");
        lNofExer.setText(template.getNum_ejercicios() + " exercises" );
        lWorkTimeExer.setText(template.getT_ejercicio() + " seconds");
        lRestTimeExer.setText(template.getD_ejercicio() + " seconds");
        lRepsCircuit.setText(template.getNum_circuitos()  + " reps");
        lRestTimeCircuits.setText(template.getD_circuito() + " seconds");
    }

    
}
