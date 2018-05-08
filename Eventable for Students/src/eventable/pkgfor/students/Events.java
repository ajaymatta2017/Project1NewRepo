/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author Ajay Matta
 */
public class Events {
    public final SimpleStringProperty event, startDate, location;

    public String getEvent() {
        return event.get();
    }
    
    public void setEvent(String event) {
        this.event.set(event);
    }
    
    public String getStartDate() {
        return startDate.get();
    }
    
     public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getLocation() {
        return location.get();
    }
    
     public void setLocation(String location) {
        this.location.set(location);
    }

    public Events(String eventName, String startDates, String locations) {
        event = new SimpleStringProperty(eventName);
        startDate = new SimpleStringProperty(startDates);
        this.location = new SimpleStringProperty(locations);
    }
}