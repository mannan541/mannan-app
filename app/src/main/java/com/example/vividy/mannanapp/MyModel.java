package com.example.vividy.mannanapp;

/**
 * Created by VIVIDY on 7/25/2016.
 */
public class MyModel {

    public String eventName;
    public String startDate;
    public String endDate;
    public String description;

    public MyModel() {
    }

    public MyModel(String eventName, String startDate, String endDate, String description) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
