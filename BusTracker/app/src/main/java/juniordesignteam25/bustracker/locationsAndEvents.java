/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;

/**
 * Class that represents the Locations and Events of a user
 * These could be custom, but they also include the classes
 * retrieved from OSCAR.
 */

public class locationsAndEvents {
    String title;
    String location;
    Time startTime;
    Time endTime;
    Boolean isEvent;

    public locationsAndEvents() {}

    @JsonIgnore
    public locationsAndEvents(String title, String location, Time startTime, Time endTime, Boolean isEvent) {
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isEvent = isEvent;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Boolean getIsEvent() {
        return isEvent;
    }
}
