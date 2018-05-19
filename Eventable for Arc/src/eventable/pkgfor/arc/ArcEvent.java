/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import javafx.beans.property.SimpleStringProperty;

public class ArcEvent {
    public final SimpleStringProperty society, event, startDate;
    private int id;

    public String getSociety() {
        return society.get();
    }

    public String getEvent() {
        return event.get();
    }

    public String getStartDate() {
        return startDate.get();
    }
    
    public int getId() {
        return id;
    }
    
    public ArcEvent(String society, String event, String startDate) {
        this.society = new SimpleStringProperty(society);
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
    }
    
    public ArcEvent(String society, String event, String startDate, int id) {
        this.society = new SimpleStringProperty(society);
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
        this.id = id;
    }
}