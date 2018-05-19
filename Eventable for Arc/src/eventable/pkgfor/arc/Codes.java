/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import javafx.beans.property.SimpleStringProperty;

public class Codes {
    public final SimpleStringProperty society, event, email;

    public String getSociety() {
        return society.get();
    }

    public String getEvent() {
        return event.get();
    }

    public String getEmail() {
        return email.get();
    }
    
    public Codes(String society, String event, String email) {
        this.society = new SimpleStringProperty(society);
        this.event = new SimpleStringProperty(event);
        this.email = new SimpleStringProperty(email);
    }
}
