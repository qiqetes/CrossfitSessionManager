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
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLManageGroupsController implements Initializable {
   
    @FXML
    private TableView<Grupo> tableView;
    @FXML
    private TableColumn<Grupo, String> colCode;
    @FXML
    private TableColumn<Grupo, SesionTipo> colDefSession;
    @FXML
    private TableColumn<Grupo, String> colDescription;
    @FXML
    private Button bModify;
    @FXML
    private Button bShowStats;
    
    AccesoBD singleton;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton  = AccesoBD.getInstance();
        
        /*TableView initialization*/        
        tableView.setItems(FXMLMainWindowController.groupObsList);
        
        /*TableView columns' cell factories*/
        colCode.setCellValueFactory(new PropertyValueFactory<Grupo,String>("codigo"));
        colDefSession.setCellValueFactory(new PropertyValueFactory<Grupo,SesionTipo>("defaultTipoSesion"));
        colDefSession.setCellFactory(c -> new TableCell<Grupo,SesionTipo>(){
            @Override
            protected void updateItem(SesionTipo item, boolean empty){
                super.updateItem(item,empty);
                if(item == null || empty ) setText(null);
                else setText(item.getCodigo());
            }
        });
        colDescription.setCellValueFactory(new PropertyValueFactory<Grupo,String>("descripcion"));
         
        /*Bindings*/
            /*Disable certain buttons if no item is selected from the tableView*/
            bModify.disableProperty().bind((Bindings.equal(-1, tableView.getSelectionModel().selectedIndexProperty())));
            bShowStats.disableProperty().bind((Bindings.equal(-1, tableView.getSelectionModel().selectedIndexProperty())));
            

    }    
    
    static void initStage() {
        
    }

    @FXML
    private void onClickAddGroup(ActionEvent event) {
        /*Open window*/
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAddModifyGroup.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLAddModifyGroupController AddModifyGroupController = loader.<FXMLAddModifyGroupController>getController();
            AddModifyGroupController.initStage(stage, singleton, null);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Add Group");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){}
    }

    @FXML
    private void onClickModify(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAddModifyGroup.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLAddModifyGroupController AddModifyGroupController = loader.<FXMLAddModifyGroupController>getController();
            Grupo g = tableView.getSelectionModel().getSelectedItem();
            AddModifyGroupController.initStage(stage, singleton, g);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Modify Group");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){}
    }

    @FXML
    private void onClickShowStats(ActionEvent event) {
    }    
    
    
}
