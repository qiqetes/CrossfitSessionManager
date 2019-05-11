/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLSessionTemplatesController implements Initializable {

    @FXML
    private ListView<SesionTipo> listViewSessions;
    
    private static Stage primaryStage;
    
    public static ObservableList<SesionTipo> obsListSessions;
    private ArrayList<SesionTipo> arrayListSessions;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*listView initialization*/
        arrayListSessions = new ArrayList();
        obsListSessions = FXCollections.observableArrayList(arrayListSessions);
        listViewSessions.setItems(obsListSessions);
        
        /*Cell Factory for templates listView*/
        listViewSessions.setCellFactory(c -> new SessionTemplateListCell());
    }    
    
    public static void initStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private void bAddTemplate(ActionEvent event) {
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

    @FXML
    private void bSeeDetails(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSessionDetails.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage stage = new Stage();
            FXMLSessionDetailsController SessionDetailsController = loader.<FXMLSessionDetailsController>getController();
            FXMLSessionDetailsController.initStage(stage);
            Scene scene = new Scene(root);  
            stage.setScene(scene);
            stage.setTitle("Session Details");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch(Exception e){}
    }
    
    /* Format the text displayed on the listView cells */
    class SessionTemplateListCell extends ListCell<SesionTipo> {
        @Override
        protected void updateItem(SesionTipo item, boolean empty)
        {   super.updateItem(item, empty);
            if( item == null || empty ) setText(null);
            else setText("Session Template: " + item.getCodigo());
        }
    }
}