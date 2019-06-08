/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitsessionmanager;

import accesoBD.AccesoBD;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import modelo.Grupo;
import modelo.SesionTipo;




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
    
    public static boolean isCodeRepeatedTemplates(AccesoBD singleton, String code){
        ArrayList<SesionTipo> sesiones = singleton.getGym().getTiposSesion();
        for(int i = 0; i< sesiones.size(); i++){
            if(code.equals(sesiones.get(i).getCodigo())){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isCodeRepeatedGroups(AccesoBD singleton, String code){
        ArrayList<Grupo> groups = singleton.getGym().getGrupos();
        for(int i = 0; i< groups.size(); i++){
            if(code.equals(groups.get(i).getCodigo())){
                return true;
            }
        }
        return false;
    }
    
    public static String toMinSecFormat(int seconds){
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%d:%02d", min, sec);
    }
    public static String toMinSecFormat2(int seconds){
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
    
    
}
