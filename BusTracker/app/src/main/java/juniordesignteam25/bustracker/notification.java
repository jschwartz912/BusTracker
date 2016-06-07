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
 * Class that represents the notifications created by the user.
 * The user can select the locationsAndEvents they want to go
 * from and to.
 */
public class notification {
    locationsAndEvents from;
    locationsAndEvents to;
    String days;
    Time time;

    @JsonIgnore
    public notification(locationsAndEvents from, locationsAndEvents to, String days, Time time) {
        this.from = from;
        this.to = to;
        this.days = days;
        this.time = time;
    }

    public notification() {}

    public locationsAndEvents getFrom() {
        return from;
    }

    public locationsAndEvents getTo() {
        return to;
    }

    public String getDays() {
        return days;
    }

    public Time getTime() {
        return time;
    }
}
