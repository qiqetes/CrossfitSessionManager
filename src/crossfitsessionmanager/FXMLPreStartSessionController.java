/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import modelo.Grupo;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Borja
 */
public class FXMLPreStartSessionController implements Initializable {

    @FXML
    private ComboBox<Grupo> cbGroup;
    @FXML
    private ComboBox<SesionTipo> cbSessionTemplate;
    
    private AccesoBD singleton;
    private Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*ComboBox initialization*/
        cbGroup.setItems(FXMLMainWindowController.groupObsList);
        cbGroup.setConverter(new StringConverter<Grupo>() {

            @Override
            public String toString(Grupo group) {
                if(group == null)return "";
                return group.getCodigo();
            }

            @Override
            public Grupo fromString(String string) {
                throw new UnsupportedOperationException("DON'T USE ME!"); 
            }
        });   
        cbGroup.setVisibleRowCount(5); //Sets a scroll bar for the combobox if there are more than 5 items
        cbGroup.focusedProperty().addListener((obs, oldVal, newVal)->{
            cbGroup.show();
        });
        
        cbSessionTemplate.setItems(FXMLMainWindowController.obsListSessions);
        cbSessionTemplate.setConverter(new StringConverter<SesionTipo>() {

            @Override
            public String toString(SesionTipo template) {
                if(template == null)return "";
                return template.getCodigo();
            }

            @Override
            public SesionTipo fromString(String string) {
                throw new UnsupportedOperationException("DON'T USE ME!"); 
            }
        });  
        cbSessionTemplate.setVisibleRowCount(5); //Sets a scroll bar for the combobox if there are more than 5 items
                
        /*Listener that synchronizes the correspondinf default template session when selecting a group*/
        cbGroup.valueProperty().addListener((observable, oldVal,newVal)->{
                cbSessionTemplate.setValue(cbGroup.getValue().getDefaultTipoSesion());
        });
        cbGroup.getEditor().textProperty().addListener((observable, oldVal,newVal)->{
                System.out.println(newVal);
        });
    }    

    @FXML
    private void onClickCancel(ActionEvent event) {
         primaryStage.close();
    }

    @FXML
    private void onClickOk(ActionEvent event) {
        /*Open window*/
        try{
            
            // TODO: check if comboboxes have existing group and template.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLIntervalTimer.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLIntervalTimerController intervalTimer = loader.<FXMLIntervalTimerController>getController();
            SesionTipo sT = cbSessionTemplate.getValue();
            Grupo g = cbGroup.getValue();
            intervalTimer.initStage(sT, g, stage);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Interval Timer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            cbGroup.getScene().getWindow().hide();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }

    public void initStage(AccesoBD singleton, Stage stage) {
        this.singleton = singleton;
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
    }
    
    
    
}
