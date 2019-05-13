/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Qiqete
 */
public class CrossfitSessionManager extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        /*
            Parent root = FXMLLoader.load(getClass().getResource("FXMLMainWindow.fxml"));        
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        */
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainWindow.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            FXMLMainWindowController MainWindowController = loader.<FXMLMainWindowController>getController();
            FXMLIntervalTimerController.initStage();
            Scene scene = new Scene(root);  
            MainWindowController.initStage(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException ioe){ioe.printStackTrace();}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);        
    }
    
}
