/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        cbSessionTemplate.setItems(FXMLMainWindowController.obsListSessions);
        cbSessionTemplate.setConverter(new StringConverter<SesionTipo>() {

            @Override
            public String toString(SesionTipo template) {
                return "Template: " + template.getCodigo();
            }

            @Override
            public SesionTipo fromString(String string) {
                throw new UnsupportedOperationException("DON'T USE ME!"); 
            }
        });     
        
        cbGroup.setItems(FXMLMainWindowController.groupObsList);
        cbGroup.setConverter(new StringConverter<Grupo>() {

            @Override
            public String toString(Grupo group) {
                return "Group: " + group.getCodigo();
            }

            @Override
            public Grupo fromString(String string) {
                throw new UnsupportedOperationException("DON'T USE ME!"); 
            }
        });     
    }    

    @FXML
    private void onClickCancel(ActionEvent event) {
         cbGroup.getScene().getWindow().hide();
    }

    @FXML
    private void onClickOk(ActionEvent event) {
        /*Open window*/
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLIntervalTimer.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLIntervalTimerController intervalTimer = loader.<FXMLIntervalTimerController>getController();
            intervalTimer.initStage();
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Interval Timer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }

    void initStage(AccesoBD singleton) {
        this.singleton = singleton;
    }
    
}
