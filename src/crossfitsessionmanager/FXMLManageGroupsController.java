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
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import modelo.Grupo;

/**
 * FXML Controller class
 *
 * @author Qiqete
 */
public class FXMLManageGroupsController implements Initializable {

    
    
    
    AccesoBD accesoBD;
    public static ObservableList<Grupo> groupObservableList;
    ArrayList<Grupo> groupData = new ArrayList<Grupo>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accesoBD  = AccesoBD.getInstance();
        
    }    
    
    static void initStage() {
        
    }
}
