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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Grupo;
import modelo.Gym;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLMainWindowController implements Initializable {
    
    @FXML
    private AnchorPane root;
    
    private Stage primaryStage;
    private AccesoBD singleton = AccesoBD.getInstance();
    private Gym gym = singleton.getGym();
    
    public static boolean alreadySaved = true;    
    
    public static ObservableList<SesionTipo> obsListSessions;
    private ArrayList<SesionTipo> arrayListSessions;
    public static ObservableList<Grupo> groupObsList;
    private ArrayList<Grupo> groupData = new ArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Initialize Global observableLists to use in every class: SessionTemplates and Groups*/
        arrayListSessions = singleton.getGym().getTiposSesion();
        obsListSessions = FXCollections.observableArrayList(arrayListSessions);
        groupData = singleton.getGym().getGrupos();    
        groupObsList = FXCollections.observableArrayList(groupData);
    }   
    
    
    public void initStage(Stage stage){
        primaryStage = stage;
        primaryStage.setOnCloseRequest((e)->{
            onClickMenuBarClose(null);
        });
    }
    
    @FXML
    private void onClickManageGroups(ActionEvent event) {
        /*Open window*/
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLManageGroups.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLManageGroupsController ManageGroupsController = loader.<FXMLManageGroupsController>getController();
            FXMLManageGroupsController.initStage();
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Manage Groups");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }

    @FXML
    private void onClickStartSession(ActionEvent event) {
        /*Open window*/
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLIntervalTimer.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLIntervalTimerController IntervalTimerController = loader.<FXMLIntervalTimerController>getController();
            FXMLIntervalTimerController.initStage();
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Interval Timer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }

    @FXML
    private void onClickSessionTemplates(ActionEvent event) {
        /*Open window*/
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSessionTemplates.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLSessionTemplatesController SessionTemplatesController = loader.<FXMLSessionTemplatesController>getController();
            FXMLSessionTemplatesController.initStage(stage);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Session Templates");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }

    
    @FXML
    private void onClickMenuBarSave(ActionEvent event) {
        singleton.salvar();
        alreadySaved = true;
    }

    @FXML
    private void onClickMenuBarClose(ActionEvent event) {
        //If the changes have not already been saved -> Save Dialog
        if(!alreadySaved){saveDialog();}
        else{Platform.exit();}
    }

    @FXML
    private void onClickMenuBarManageGroups(ActionEvent event) {
        onClickManageGroups(event);
    }
    
    @FXML
    private void onClickMenuBarAddGroup(ActionEvent event) { //Or should we make FXMLManageGroupsController.onClickAddGroup public?
        /*Open window*/
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAddModifyGroup.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLAddModifyGroupController AddGroupController = loader.<FXMLAddModifyGroupController>getController();
            AddGroupController.initStage(stage, singleton, null, null);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Add Group");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){}
    }


    @FXML
    private void onClickMenuBarAddTemplate(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAddTemplate.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLAddTemplateController AddTemplateController = loader.<FXMLAddTemplateController>getController();
            FXMLAddTemplateController.initStage(stage);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("New Template");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(Exception e){}
    }
    
    
    /*Save changes and close project Dialog*/
    public void saveDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save and Close");
        alert.setHeaderText("Do you want to save the changes?");
        //Customized buttons
        ButtonType bSave = new ButtonType("Save");
        ButtonType bDontSave = new ButtonType("Don't Save");        
        alert.getButtonTypes().setAll(bSave, bDontSave, ButtonType.CANCEL);
        
        /*Borrar: Stage stage = (Stage) bStartSession.getScene().getWindow();*/
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            if(result.get() == bSave){
                onClickMenuBarSave(null);
                System.out.println("Save");  
                /*Borrar:stage.close();*/
                Platform.exit();
            }
            else if(result.get() == bDontSave){
                System.out.println("Don't Save");
                /*Borrar:stage.close();*/
                Platform.exit();
            }
            else{
                System.err.println("Cancel");
            }
            
            
        }
    }
}
