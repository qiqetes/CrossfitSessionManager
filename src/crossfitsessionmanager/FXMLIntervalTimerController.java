/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import modelo.Grupo;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLIntervalTimerController implements Initializable {

    private SesionTipo template;
    private Grupo group;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startTime = LocalDateTime.now();
    }    
    
    public void initStage(SesionTipo sT, Grupo g) {
        template = sT;
        System.out.println(template.getCodigo());
        group = g;
        System.out.println(group);
    }

    @FXML
    private void onClickPlay(MouseEvent event) {
    }
}
