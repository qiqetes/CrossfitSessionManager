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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelo.Grupo;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Borja
 */
public class FXMLAddModifyGroupController implements Initializable {

    @FXML
    private TextField tFCode;
    @FXML
    private TextArea tADescription;
    @FXML
    private ComboBox<SesionTipo> cBDefaultSession;
    @FXML
    private Button bAdd;

    private Stage primaryStage;
    private AccesoBD singleton;
    private Grupo g;
    private Boolean modifyGroup = false;
    
    private boolean codeError;
    
    TableView refTableView;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*bindings*/
        bindings();
        
        /*Checks if code is repeated*/
        tFCode.textProperty().addListener((observable, oldVal,newVal)->{
            if(Utils.isCodeRepeatedGroups(singleton, newVal)){
                codeError = true;
                // TODO: add some kind of visual indicator
            }else{
                codeError = false;
            }
            //codeError = (Utils.isCodeRepeated(singleton, newVal)) ? true:false;
        });
                
        /*Initialize choiceBox*/
        cBDefaultSession.setItems(FXMLMainWindowController.obsListSessions);
        cBDefaultSession.setConverter(new StringConverter<SesionTipo>() {

            @Override
            public String toString(SesionTipo template) {
                return "Template: " + template.getCodigo();
            }

            @Override
            public SesionTipo fromString(String string) {
                throw new UnsupportedOperationException("DON'T USE ME!"); 
            }
        });        
    }    
    
    @FXML
    private void onClickAdd(ActionEvent event) {
        if(codeError && !modifyGroup){
            Utils.dialog(Alert.AlertType.ERROR, "Error", "Invalid input", "This code is already in use");
        //}else if(FXMLSessionTemplatesController.obsListSessions.isEmpty()){
            //implement a signal to say that a group cannot be created if there is not at least one session template created
        }else{
            g.setCodigo(tFCode.getText());
            g.setDescripcion(tADescription.getText());
            g.setDefaultTipoSesion(cBDefaultSession.getValue());
            
            String s = "modified";
            /*Add the new group to the list*/
            if(!modifyGroup){                
                FXMLMainWindowController.groupObsList.add(g);
                singleton.getGym().getGrupos().add(g);
                s = "created";
            }        
            onClickCancel(event);
            FXMLMainWindowController.alreadySaved = false;
            Utils.dialog(Alert.AlertType.INFORMATION, "Successfully " + s, "Template was "+ s+ " successfully", null);
            
            /*Refresh TableView*/
            
        }
    }
    @FXML
    private void onClickCancel(ActionEvent event) {
        if(refTableView != null)
            refTableView.refresh();
        primaryStage.close();
    }

    void initStage(Stage stage, AccesoBD singleton, Grupo grupo, TableView tbleView) {
        primaryStage = stage;
        this.singleton = singleton;
        this.refTableView = tbleView;
        /*Set fields to the values of the already-existing group*/
        if(grupo != null){
            g = grupo;
            modifyGroup = true;
            bAdd.setText("Modify");
            tFCode.setText(g.getCodigo());
            tFCode.setDisable(true);    //Disable to not let modifications
            tADescription.setText(g.getDescripcion());
            cBDefaultSession.setValue(g.getDefaultTipoSesion());
        }
        /*Create new group otherwise*/
        else{
            g = new Grupo();
        }
    }

    private void bindings() {
        BooleanBinding boolB = tFCode.textProperty().isEmpty().or(cBDefaultSession.valueProperty().isNull());
        bAdd.disableProperty().bind(boolB);
    }    
}
