/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLMainWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
            FXMLSessionTemplatesController.initStage();
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Session Templates");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }
    
    
}
