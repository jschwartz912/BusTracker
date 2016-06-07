/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Time;

/**
 * Class that represents an abstract class that the user has.
 * Used in ExportScheduleActivity and ViewNotificationsActivityNotUsedAnymore
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class singleClassNotUsedAnymore implements Serializable{
    protected String name;
    protected int classNum;
    protected String days;
    protected Time startTime;
    protected Time endTime;
    protected String location;

    public singleClassNotUsedAnymore() {}

    @JsonIgnore
    public singleClassNotUsedAnymore(String name, int number, String days, Time startTime, Time endTime, String location) {
        this.name = name;
        this.classNum = number;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public int getClassNum() {
        return classNum;
    }

    public String getDays() {
        return days;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }


}
