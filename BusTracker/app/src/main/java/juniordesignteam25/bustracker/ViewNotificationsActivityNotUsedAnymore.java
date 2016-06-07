/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.astuetz.PagerSlidingTabStrip;

import java.sql.Time;

import layout.dayFragmentPagerAdapter;

/**
 * Class used to display all the notifications the user currently has set.
 * Also, this is the main page of the application if the user is already logged
 * in and has already set their preferences.
 */
public class ViewNotificationsActivityNotUsedAnymore extends FragmentActivity{
    //Setting up the user
    userLocalStore user;
    singleClassNotUsedAnymore[] class_data;

    private singleClassNotUsedAnymore classes[] = new singleClassNotUsedAnymore[]
    {
            new singleClassNotUsedAnymore("Systems and Networks", 2200, "MWF", new Time(12, 5, 0), new Time(1, 5, 0), "CULC 152"),
            new singleClassNotUsedAnymore("Mobiles Apps and Services", 4261, "TT", new Time(12, 5, 0), new Time(1, 25, 0), "CoC 16"),
            new singleClassNotUsedAnymore("Intro to Info Security", 4235, "TT", new Time(3, 5, 0), new Time(4, 25, 0), "CoC 16")
    };

    /**
     * Used to detect the location of the user
     */
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
        setContentView(R.layout.activity_view_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting up the user's classes
        user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(ViewNotificationsActivityNotUsedAnymore.this));
        //ArrayList<singleClassNotUsedAnymore> classList = user.getClass();
        //singleClassNotUsedAnymore[] class_data = classList.toArray(new singleClassNotUsedAnymore[classList.size()]);

        //Setting up location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } catch (SecurityException e) {
            System.err.println("No permission to get location.  This app cannot function.");
            return;
        }

        //Initializing the ViewPage and setting the adapter
        ViewPager pager = (ViewPager) findViewById(R.id.exportPager);
        pager.setAdapter(new dayFragmentPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);


        /*ListView listView;

        NotificationsAdapter adapter = new NotificationsAdapter(this, R.layout.class_row, class_data);
        listView = (ListView)findViewById(R.id.notificationsList);

        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddNotificationActivity.class);
                startActivity(i);
            }
        });

        Button mrButton = (Button) findViewById(R.id.button2);
        mrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(ViewNotificationsActivityNotUsedAnymore.this, MapsActivity.class);
                startActivity(i);
            }
        });*/
    }


}
