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
import javafx.fxml.Initializable;
import modelo.Grupo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLManageGroupsController implements Initializable {

    
    
    
    AccesoBD singleton;
    public static ObservableList<Grupo> groupObsList;
    ArrayList<Grupo> groupData = new ArrayList<Grupo>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singleton  = AccesoBD.getInstance();
        groupData = singleton.getGym().getGrupos();
        groupObsList = FXCollections.observableArrayList(groupData);
        listViewGroups.setItems(groupObsList);
        
        /*Cell Factory for templates listView*/
        listViewSessions.setCellFactory(c -> new SessionTemplateListCell());
    }    
    
    static void initStage() {
        
    }
}
