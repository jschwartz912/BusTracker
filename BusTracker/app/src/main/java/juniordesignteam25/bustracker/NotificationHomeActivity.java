/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

/**
 * Class used to display all the notifications the user currently has set.
 * Also, this is the main page of the application if the user is already logged
 * in and has already set their preferences. The user can also add events, locations, and use the
 * manual route function from here.
 */
public class NotificationHomeActivity extends AppCompatActivity{
    private userLocalStore user;
    private ArrayList<locationsAndEvents> classes;

    //Adapter stuff and tabs stuff
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private dayPagerAdapter adapter;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLocation[0] = location.getLatitude();
            currentLocation[1] = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private double[] currentLocation = new double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HOME");
        setSupportActionBar(toolbar);

        //Setting up location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } catch (SecurityException e) {
            System.err.println("No permission to get location.  This app cannot function.");
            return;
        }

        //Setting up the user
        user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(this));
        classes = user.getEvents();

        //setting up the adapter
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new dayPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);

        //Setting up the floating menu
        final FloatingActionsMenu menu = (FloatingActionsMenu) findViewById(R.id.menu);

        //Setting up the add notification button
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.addNotification);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotificationFragment dialog = new addNotificationFragment();
                dialog.show(getFragmentManager(), "addNotificationFragment");
                menu.collapse();
            }
        });

        //Setting up the logout button
        FloatingActionButton fabLogout = (FloatingActionButton) findViewById(R.id.logoutButton);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(NotificationHomeActivity.this));
                user.logout();
                Intent i = new Intent(getBaseContext(), loginPage.class);
                startActivity(i);
            }
        });

        //Setting up the add event button
        FloatingActionButton fabEvent = (FloatingActionButton) findViewById(R.id.addEvents);
        fabEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddEventsActivity.class);
                i.putExtra("from", "home");
                startActivity(i);
            }
        });

        //Setting up the add event button
        FloatingActionButton fabLocation = (FloatingActionButton) findViewById(R.id.addLocations);
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddLocationsActivity.class);
                i.putExtra("from", "home");
                startActivity(i);
            }
        });

        //Setting up the add event button
        FloatingActionButton fabMap = (FloatingActionButton) findViewById(R.id.map);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     * Adapter for the different tabs and to show the notifications of each day
     */
    public class dayPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        public dayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return notificationListFragment.newInstance(position);
        }

    }
}


