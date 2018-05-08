/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import javafx.beans.property.SimpleStringProperty;

public class Feedback {
    
    public final SimpleStringProperty event, endDate, societyName;

    public String getSocietyName() {
        return societyName.get();
    }
    
    public void setSocietyName(String societyName) {
        this.societyName.set(societyName);
    }

    public String getEvent() {
        return event.get();
    }
    
    public void setEvent(String event) {
        this.event.set(event);
    }
    
    public String getEndDate() {
        return endDate.get();
    }
    
     public void setEndDate(String startDate) {
        this.endDate.set(startDate);
    }

    public Feedback(String eventName, String startDates, String societyNames) {
        event = new SimpleStringProperty(eventName);
        endDate = new SimpleStringProperty(startDates);
        societyName = new SimpleStringProperty(societyNames);
    }
}
