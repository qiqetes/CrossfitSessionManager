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
    private Label lRepsCircuit;
    @FXML
    private Label lRestTimeCircuits;
    
    private static Stage primaryStage;
    private static SesionTipo template;
    private static String s;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void onClickOk(ActionEvent event) {
        primaryStage.close();
    }
    
    static void initStage(Stage stage, SesionTipo sT) {
        primaryStage = stage;
        template = sT;
        
        
    }
}
