package com.example.goldenatoz.holiday;

public class ModelHolidayCalendar {

    String date;
    String event;

    public ModelHolidayCalendar(String date, String event) {
        this.date = date;
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


}
