/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;




public class Utils {
    
    /*All types of dialogs*/
    public static void dialog(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){System.err.println("OK");}
    }
    
    public static boolean isNumeric(String s){
        try {  
         Integer.parseInt(s);  
         return true;  
      } catch (NumberFormatException e) {  
         return false;  
      } 
    }
    
}
