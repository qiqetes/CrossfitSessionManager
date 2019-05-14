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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    
    AccesoBD singleton;
    public static ObservableList<Grupo> groupObsList;
    ArrayList<Grupo> groupData = new ArrayList<Grupo>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton  = AccesoBD.getInstance();
               
        /*TableView initialization*/
        groupData = singleton.getGym().getGrupos();    
        groupObsList = FXCollections.observableArrayList(groupData);
        tableView.setItems(groupObsList);
        
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
    }    
    
    static void initStage() {
        
    }

    @FXML
    private void onClickAddGroup(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAddGroup.fxml"));
            Parent root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLAddGroupController ManageGroupsController = loader.<FXMLManageGroupsController>getController();
            FXMLManageGroupsController.initStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add Group");
            stage.show();
        }catch(Exception e){}
    }

    @FXML
    private void onClickModify(ActionEvent event) {
    }

    @FXML
    private void onClickShowStats(ActionEvent event) {
    }
}
