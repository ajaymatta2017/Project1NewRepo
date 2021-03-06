/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import javafx.beans.property.SimpleStringProperty;

public class Events {
    private SimpleStringProperty event;
    private SimpleStringProperty startDate;
    private SimpleStringProperty locationType;
    private SimpleStringProperty eventType;
    private SimpleStringProperty streetNo;
    private SimpleStringProperty streetName;
    private SimpleStringProperty postcode;
    private SimpleStringProperty suburb;
    private SimpleStringProperty buildingID;
    private SimpleStringProperty buildingName;
    private SimpleStringProperty roomNo;
    private SimpleStringProperty eventEndTime;
    private SimpleStringProperty eventEnd;
    private SimpleStringProperty eventStartTime;
    private int id;
    private SimpleStringProperty eventText;

    public Events(String event, String startDate, String locationType, String streetNo, String streetName, String postcode, String suburb, String buildingID, String buildingName, String roomNo, String eventEnd, String eventEndTime, String eventStartTime, String eventText, int id, String eventType) {
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
        this.locationType = new SimpleStringProperty(locationType);
        this.streetNo = new SimpleStringProperty(streetNo);
        this.streetName = new SimpleStringProperty(streetName);
        this.postcode = new SimpleStringProperty(postcode);
        this.suburb = new SimpleStringProperty(suburb);
        this.buildingID = new SimpleStringProperty(buildingID);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.roomNo = new SimpleStringProperty(roomNo);
        this.eventEnd = new SimpleStringProperty(eventEnd);
        this.eventEndTime = new SimpleStringProperty(eventEndTime);
        this.eventStartTime = new SimpleStringProperty(eventStartTime);
        this.eventText = new SimpleStringProperty(eventText);
        this.id = id;
        this.eventType = new SimpleStringProperty(eventType);
    }
    
    public Events(String event, String startDate, String locationType, String eventType, String streetNo, String streetName, String postcode, String suburb, String eventEnd, String eventEndTime, String eventStartTime, String eventText) {
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
        this.eventEnd = new SimpleStringProperty(eventEnd);
        this.eventEndTime = new SimpleStringProperty(eventEndTime);
        this.eventStartTime = new SimpleStringProperty(eventStartTime);
        this.locationType = new SimpleStringProperty(locationType);
        this.streetNo = new SimpleStringProperty(streetNo);
        this.streetName = new SimpleStringProperty(streetName);
        this.postcode = new SimpleStringProperty(postcode);
        this.suburb = new SimpleStringProperty(suburb);
        this.eventText = new SimpleStringProperty(eventText);
        this.eventType = new SimpleStringProperty(eventType);
    }
    
    public Events(String event, String startDate, String locationType, String eventType, String buildingName, String roomNo, String eventEnd, String eventEndTime, String eventStartTime, String eventText) {
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
        this.eventEnd = new SimpleStringProperty(eventEnd);
        this.eventEndTime = new SimpleStringProperty(eventEndTime);
        this.eventStartTime = new SimpleStringProperty(eventStartTime);
        this.locationType = new SimpleStringProperty(locationType);
        this.eventText = new SimpleStringProperty(eventText);
        this.eventType = new SimpleStringProperty(eventType);
    }
   
    public Events(String event, String startDate, String locationType, String eventType) {
        this.event = new SimpleStringProperty(event);
        this.startDate = new SimpleStringProperty(startDate);
        this.locationType = new SimpleStringProperty(locationType);
        this.eventType = new SimpleStringProperty(eventType);
    }
    
    public Events(String eventType) {
        this.eventType = new SimpleStringProperty(eventType);
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
    
    public String getEventType() {
        return eventType.get();
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

    public String getBuildingID() {
        return buildingID.get();
    }

    public String getRoomNo() {
        return roomNo.get();
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