/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Map Activity displays the map of the manual route
 * specified by the user, and create a route.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager mLocationManager;
    GMapV2Direction md;
    LatLng scLocation;
    LatLng userLocation;
    LatLng culcLocation;
    LatLng naLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        md = new GMapV2Direction();
        //Enable MyLocation Layer of Google Map
        Location myLocation = getLastKnownLocation();
        //USER LOCATION HARD CODED TO CULC FOR USABILITY TESTS
        //Student Center Location
        scLocation = new LatLng(33.773964, -84.398356);
        //CULC Location
        culcLocation = new LatLng(33.774920, -84.396415);
        //North Ave Apartments Location
        naLocation = new LatLng(33.770272, -84.391592);
        // Add a markers for locations

        if (myLocation != null) {
            userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(scLocation,15));
        }
        mMap.addMarker(new MarkerOptions().position(scLocation).title("Student Center"));
        mMap.addMarker(new MarkerOptions().position(naLocation).title("North Avenue Apartments"));
        mMap.addMarker(new MarkerOptions().position(culcLocation).title("Clough Undergraduate Learning Commons"));
        //Draws route on map, using google maps app instead
        //new showRoute().execute();

    }

    //Gets location of user
    public Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            try {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            } catch (SecurityException e) {
                System.err.println("No permission to get location.  This app cannot function.");
            }
        }
        return bestLocation;
    }

    //Draws route on map between two locations
    private class showRoute extends AsyncTask<Void, Void, Document> {

        Document doc;
        PolylineOptions rectLine;

        @Override
        protected Document doInBackground(Void... params) {

            doc = md.getDocument(userLocation, scLocation, GMapV2Direction.MODE_WALKING);

            ArrayList<LatLng> directionPoint = md.getDirection(doc);
            rectLine = new PolylineOptions().width(3).color(Color.RED);

            for (int i = 0; i < directionPoint.size(); i++) {
                rectLine.add(directionPoint.get(i));
            }

            return null;
        }
        @Override
        protected void onPostExecute(Document result) {

            mMap.addPolyline(rectLine);
        }


    }
}
