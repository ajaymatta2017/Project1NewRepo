/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author AriSurfacePro
 */
public class Utils {
    public static String extractString(TextField field){
        return field.getText().trim();
    }
    
    public static String extractString(ComboBox comboBox){
        return comboBox.toString().trim();
    }
    
    public static boolean extractStringIsEmpty(TextField field){
        return extractString(field).isEmpty();
    }
    
    public static boolean extractStringIsEmpty (ComboBox comboBox) {
        return extractString(comboBox).isEmpty();
    }
}
