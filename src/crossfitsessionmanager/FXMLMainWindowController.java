/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import modelo.Gym;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLMainWindowController implements Initializable {
    
    @FXML
    private Button bStartSession;
    
    private AccesoBD singleton = AccesoBD.getInstance();
    private Gym gym = singleton.getGym();
    
    public static boolean alreadySaved = false;    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        if(!alreadySaved){saveDialog();
        }else{
            /*Borrar: Stage stage = (Stage) bStartSession.getScene().getWindow();
            stage.close();*/
            Platform.exit();
        }
    }

    @FXML
    private void onClickMenuBarAddGroup(ActionEvent event) {
    }

    @FXML
    private void onClickMenuBarModifyGroup(ActionEvent event) {
    }

    @FXML
    private void onClickMenuBarAddTemplate(ActionEvent event) {
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
