package juniordesignteam25.bustracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import layout.NotificationsAdapter;

/**
 * Class that represents the fragment within each of the
 * tabs in the Home screen. This is mainly used to
 * show the notifications that the user has for any given
 * day.
 */
public class notificationListFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private userLocalStore user;
    private ArrayList<notification> notifications;

    private int position;

    public static notificationListFragment newInstance(int position) {
        notificationListFragment f = new notificationListFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
                .getDisplayMetrics());

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String day = days[position];

        ArrayList<notification> todayNotification = new ArrayList<>();

        //Setting up the user and the classes
        user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(getContext()));
        notifications = user.getNotifications();
        for (notification each : notifications) {
            String d = each.getDays();
            if (d.contains(day)) {
                todayNotification.add(each);
            }
        }

        notification[] stupid = todayNotification.toArray(new notification[todayNotification.size()]);

        //Setting up the notification inflater
        NotificationsAdapter adapter = new NotificationsAdapter(getContext(), R.layout.notification_row, stupid);
        ListView list = new ListView(getActivity());
        params.setMargins(margin, margin, margin, margin);
        list.setLayoutParams(params);
        list.setLayoutParams(params);
        list.setAdapter(adapter);

        fl.addView(list);
        return fl;
    }

    /**
     * Class that represents the Adapter that would inflate the generic notification row
     * for all the notifications inputted.
     */
    public class NotificationsAdapter extends ArrayAdapter<notification> {

        Context context;
        int layoutResourceId;
        notification[] notifications = null;

        public NotificationsAdapter(Context context, int layoutResourceId, notification[] notifications) {
            super(context, layoutResourceId, notifications);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.notifications = notifications;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            View rowView = inflater.inflate(R.layout.notification_row, parent, false);

            TextView fromDescription = (TextView) rowView.findViewById(R.id.fromTitle);
            TextView fromLocation = (TextView) rowView.findViewById(R.id.fromLocation);

            TextView toDescription = (TextView) rowView.findViewById(R.id.toTitle);
            TextView toLocation = (TextView) rowView.findViewById(R.id.toLocation);

            TextView time = (TextView) rowView.findViewById(R.id.time);

            notification c = notifications[position];

            fromDescription.setText(c.getFrom().getTitle());
            fromLocation.setText(c.getFrom().getLocation());
            toDescription.setText(c.getTo().getTitle());
            toLocation.setText(c.getTo().getLocation());

            time.setText(addNotificationFragment.timeToString(c.getTime()));

            return rowView;
        }
    }
}
