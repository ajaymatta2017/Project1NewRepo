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
    public final SimpleStringProperty event, startDate, locationType, streetNo, streetName, postcode, suburb, buildingCode, buildingName, roomNo, societyName, eventEnd, eventEndTime, eventStartTime, eventText;
    public final int id;

    public Events(String event, String startDate, String locationType, String streetNo, String streetName, String postcode, String suburb, String buildingCode, String buildingName, String roomNo, String societyName, String eventEnd, String eventEndTime, String eventStartTime, String eventText, int id) {
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
        this.locationType = new SimpleStringProperty(locationType);
        this.streetNo = new SimpleStringProperty(streetNo);
        this.streetName = new SimpleStringProperty(streetName);
        this.postcode = new SimpleStringProperty(postcode);
        this.suburb = new SimpleStringProperty(suburb);
        this.buildingCode = new SimpleStringProperty(buildingCode);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.roomNo = new SimpleStringProperty(roomNo);
        this.societyName = new SimpleStringProperty(societyName);
        this.eventEnd = new SimpleStringProperty(eventEnd);
        this.eventEndTime = new SimpleStringProperty(eventEndTime);
        this.eventStartTime = new SimpleStringProperty(eventStartTime);
        this.eventText = new SimpleStringProperty(eventText);
        this.id = id;
    }

    public String getEvent() {
        return event.get();
    }

    public String getStartDate() {
        return startDate.get();
    }

    public String getLocationType() {
        return locationType.get();
    }

    public String getStreetNo() {
        return streetNo.get();
    }

    public String getStreetName() {
        return streetName.get();
    }

    public String getPostcode() {
        return postcode.get();
    }

    public String getSuburb() {
        return suburb.get();
    }

    public String getBuildingCode() {
        return buildingCode.get();
    }

    public String getRoomNo() {
        return roomNo.get();
    }

    public String getSocietyName() {
        return societyName.get();
    }

    public String getEventEnd() {
        return eventEnd.get();
    }

    public String getBuildingName() {
        return buildingName.get();
    }

    public String getEventEndTime() {
        return eventEndTime.get();
    }

    public String getEventStartTime() {
        return eventStartTime.get();
    }

    public String getEventText() {
        return eventText.get();
    }

    public String getId() {
        return Integer.toString(id);
    }
    
      
}