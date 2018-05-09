/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;
import javafx.beans.property.SimpleStringProperty;

public class FavouriteSocieties {
    
    public final SimpleStringProperty societyName, societyDescription;
    public final int id;

    public String getSocietyName() {
        return this.societyName.get();
    }
    
    public void setSocietyName(String societyName) {
        this.societyName.set(societyName);
    }
      
    public String getSocietyDescription() {
        return this.societyDescription.get();
    }
    
     public void setSocietyDescription(String societyDescription) {
        this.societyDescription.set(societyDescription);
    }
     
    public FavouriteSocieties(String societyName, String societyDescription, int id) {
        this.societyName = new SimpleStringProperty(societyName);
        this.societyDescription = new SimpleStringProperty(societyDescription);
        this.id = id;
    }
    
    public String getId() {
        return Integer.toString(id);
    }
}