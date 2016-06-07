/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */

package juniordesignteam25.bustracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to store the data of the user currently logged in so that their
 * data will persist even if the application is killed.
 */
public class userLocalStore {

    private String name;
    private String email;
    private ArrayList<locationsAndEvents> events;
    private ArrayList<notification> notifications;
    private SharedPreferences prefs;

    public userLocalStore(SharedPreferences prefs) {
        this.prefs = prefs;
        name = prefs.getString("name", "");
        email = prefs.getString("email", "");
        Gson gson = new Gson();
        String classesString = prefs.getString("events", "");
        Type type = new TypeToken<ArrayList<locationsAndEvents>>() {}.getType();
        events = gson.fromJson(classesString, type);
        if (events == null) {
            events = new ArrayList<>();
        }

        String notifStrings = prefs.getString("notifications", "");
        Type type1 = new TypeToken<ArrayList<notification>>() {}.getType();
        notifications = gson.fromJson(notifStrings, type1);
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
    }

    public userLocalStore(String name, String email, ArrayList<locationsAndEvents> events, ArrayList<notification> notifications, SharedPreferences prefs) {
        this.name = name;
        this.email = email;
        this.events = events;
        this.prefs = prefs;
        this.notifications = notifications;
    }

    public void login() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        Gson gson = new Gson();
        String classesGson = gson.toJson(events);
        editor.putString("events", classesGson);

        String notifGson = gson.toJson(notifications);
        editor.putString("notifications", notifGson);
        editor.commit();
    }

    public void logout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<locationsAndEvents> getEvents() {
        return events;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public ArrayList<notification> getNotifications() {return notifications;}

    public void setEvents(ArrayList<locationsAndEvents> events) {
        this.events = events;
        login();
    }

    public void setNotifications(ArrayList<notification> notifications) {
        this.notifications = notifications;
        login();
    }
}
