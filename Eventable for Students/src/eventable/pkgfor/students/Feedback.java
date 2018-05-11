/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

import javafx.beans.property.SimpleStringProperty;

public class Feedback {
    
    public final SimpleStringProperty event, endDate, societyName, startDate, eventEndTime, eventStartTime;
    public final int eventId;

    public Feedback(String societyNames, String eventName, String endDates, String startDate, String eventEndTime, String eventStartTime, int eventId) {
        event = new SimpleStringProperty(eventName);
        endDate = new SimpleStringProperty(endDates);
        societyName = new SimpleStringProperty(societyNames);
        this.startDate = new SimpleStringProperty(startDate);
        this.eventEndTime = new SimpleStringProperty(eventEndTime);
        this.eventStartTime = new SimpleStringProperty(eventStartTime);
        this.eventId = eventId;
    }

    public String getEvent() {
        return event.get();
    }

    public String getEndDate() {
        return endDate.get();
    }

    public String getSocietyName() {
        return societyName.get();
    }

    public String getStartDate() {
        return startDate.get();
    }

    public String getEventEndTime() {
        return eventEndTime.get();
    }

    public String getEventStartTime() {
        return eventStartTime.get();
    }

    public String getEventId() {
        return Integer.toString(eventId);
    }
    
    
    
    
    
}
