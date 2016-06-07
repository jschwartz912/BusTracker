/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Notification fragment class that represents the popup dialog
 * when the user selects "Add Notification" in the home screen.
 * This class handles the display as well as the storage of the notification that was
 * created by the user.
 */
public class addNotificationFragment extends DialogFragment{
    private int pendingNum;
    public locationsAndEvents[] locations;
    public String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");


    public static addNotificationFragment newInstance() {
        addNotificationFragment f = new addNotificationFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
        }

        View root = inflater.inflate(R.layout.fragment_add_notification, container, false);

        //Setting up the database
        final Firebase firebaseRef = new Firebase("https://gtbus-tracker.firebaseio.com/");

        //Setting up the local storage
        userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(getActivity()));
        locations = user.getEvents().toArray(new locationsAndEvents[user.getEvents().size()]);

        //Populating the Spinners with the locations/events of the user
        final Spinner fromSpin = (Spinner) root.findViewById(R.id.fromSpinner);
        fromSpin.setAdapter(new eventsSpinnerAdapter(getActivity(), R.layout.one_location_event, locations));
        final Spinner toSpin = (Spinner) root.findViewById(R.id.toSpinner);
        toSpin.setAdapter(new eventsSpinnerAdapter(getActivity(), R.layout.one_location_event, locations));

        final EditText time = (EditText) root.findViewById(R.id.time_input);

        //Retrieving the checkboxes
        final CheckBox[] checkBoxes = new CheckBox[7];
        checkBoxes[0] = (CheckBox)root.findViewById(R.id.mondayBox);
        checkBoxes[1] = (CheckBox)root.findViewById(R.id.tuesdayBox);
        checkBoxes[2] = (CheckBox)root.findViewById(R.id.wednesdayBox);
        checkBoxes[3] = (CheckBox)root.findViewById(R.id.thursdayBox);
        checkBoxes[4] = (CheckBox)root.findViewById(R.id.fridayBox);
        checkBoxes[5] = (CheckBox)root.findViewById(R.id.saturdayBox);
        checkBoxes[6] = (CheckBox)root.findViewById(R.id.sundayBox);

        //Setting up the button and the click listener of the button
        Button addButton = (Button) root.findViewById(R.id.addNotificationButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationsAndEvents from = (locationsAndEvents) fromSpin.getSelectedItem();
                locationsAndEvents to = (locationsAndEvents) toSpin.getSelectedItem();
                Time setTime = stringToTime(time.getText().toString());

                boolean[] checkedDays = new boolean[checkBoxes.length];
                String classDays = new String();
                for (int i = 0; i < checkBoxes.length; i++){
                    checkedDays[i] = checkBoxes[i].isChecked();
                    if(checkBoxes[i].isChecked()) {
                        classDays = classDays + days[i];
                    }
                }

                ArrayList<Calendar> days = createDays(setTime, checkedDays);
                for(Calendar day : days){
                    addNotificationTime(day.getTime(), from.getLocation(), to.getLocation());
                }

                notification newNotif = new notification(from, to, classDays, setTime);

                userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(getActivity()));
                ArrayList<notification> no = user.getNotifications();
                no.add(newNotif);
                user.setNotifications(no);

                firebaseRef.child("users").child(firebaseRef.getAuth().getUid()).child("notifications").setValue(no);

                Toast.makeText(getActivity(), "Notification Made!", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        return root;
    }

    /**
     * creates dates in the future for the selected weekdays
     * @param time  The time of the notification
     * @param checkedDays   The days the user wants the notification on
     * @return
     */
    private ArrayList<Calendar> createDays(Date time,  boolean[] checkedDays){
        ArrayList<Calendar> days = new ArrayList<>();
        for(int i = 0; i < checkedDays.length; i++){
            if (checkedDays[i]==true) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR, time.getHours());
                cal.set(Calendar.MINUTE, time.getMinutes());
                cal.set(Calendar.DAY_OF_WEEK, i + 2);
                if (cal.before(Calendar.getInstance())){
                    cal.add(Calendar.DATE,7);
                }
                days.add(cal);
            }
        }
        return days;
    }

    /**
     * Creates an alarm to create the notification
     * @param time  The time the user wants the notification to appear
     * @param stop  The stop the user waits at for the bus
     * @param destination   The place the user wants to get to
     */
    private void addNotificationTime(Date time, String stop, String destination){
        Intent intent = new Intent(getActivity() , notificationCreatorService.class);
        intent.putExtra("stop",stop);
        intent.putExtra("destination", destination);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(getActivity(), pendingNum++, intent, 0);
        Calendar cal = new GregorianCalendar();
        cal.setTime(time);
        System.out.println("Notification set for: "+ sdf.format(cal.getTime()));
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart() {
        super.onStart();

        // change dialog width
        if (getDialog() != null) {

            int fullWidth = getDialog().getWindow().getAttributes().width;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                fullWidth = size.x;
            } else {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                fullWidth = display.getWidth();
            }

            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                    .getDisplayMetrics());

            int w = fullWidth - padding;
            int h = getDialog().getWindow().getAttributes().height;

            getDialog().getWindow().setLayout(w, h);
        }
    }

    /**
     * Class that inflates the events/locations into the Spinner
     */
    public class eventsSpinnerAdapter extends ArrayAdapter<locationsAndEvents> {

        public eventsSpinnerAdapter(Context context, int textViewResourceId, locationsAndEvents[] stuff) {
            super(context, textViewResourceId, stuff);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.one_location_event, parent, false);

            TextView title = (TextView) layout.findViewById(R.id.title);
            TextView location = (TextView) layout.findViewById(R.id.location);
            TextView time = (TextView) layout.findViewById(R.id.time);

            title.setText(locations[position].getTitle());
            location.setText(locations[position].getLocation());

            if(locations[position].getIsEvent()) {
                String start = timeToString(locations[position].getStartTime());
                String end = timeToString(locations[position].getEndTime());
                time.setText(start + " - " + end);
            }

            return layout;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }

    /**
     * Method that helps convert from Time format to a string for
     * easier display.
     */

    public static String timeToString(Time time) {
        String temp = time.toString();
        String[] split = temp.split(":");
        String str = (Integer.parseInt(split[0]) >= 12) ? "pm" : "am";
        str = ":" + split[1] + " " + str;

        if(Integer.parseInt(split[0]) == 0) {
            str = "12" + str;
        } else if (Integer.parseInt(split[0]) > 12) {
            str = (Integer.parseInt(split[0]) - 12) + str;
        } else {
            str = split[0] + str;
        }
        return str;
    }

    /**
     * Method that converts a string to Time format for easier
     * user input.
     */
    public static Time stringToTime(String s) {
        System.out.println(s);
        String[] split = s.split(":|\\s+");
        System.out.println(split[0]);
        System.out.println(split[1]);
        System.out.println(split[2]);
        int hour;
        int minute = Integer.parseInt(split[1]);
        if(Integer.parseInt(split[0]) != 12 && split[2].equals("pm")) {
            hour = Integer.parseInt(split[0]) + 12;
        } else {
            hour = Integer.parseInt(split[0]);
        }
        Time t = new Time(hour, minute, 0);
        System.out.println(t.toString());
        return t;
    }

}


