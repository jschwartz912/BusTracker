/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Allows users to add more custom locations that they would like
 * to make notifications to and from. Also handles saving the information in
 * the local data storage as well as saving the information into the database.
 */
public class AddLocationsActivity extends AppCompatActivity {
    Firebase firebaseRef;
    Firebase eventsRef;
    ArrayList<location> ls;
    ArrayList<locationsAndEvents> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Locations");
        setSupportActionBar(toolbar);

        //Setting up the database connection
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://gtbus-tracker.firebaseio.com/");
        final Firebase eventsRef = firebaseRef.child("users").child(firebaseRef.getAuth().getUid()).child("events");
        Intent intent = getIntent();
        events = new ArrayList<>();

        //Setting up listview
        ls = new ArrayList<>();
        ls.add(new location("Home", ""));
        ls.add(new location("Food", ""));


        //Setting up the adapter for the list view containing the "add location" cards
        final addLocationsAdapter locationAdapter = new addLocationsAdapter(this, ls);
        ListView listView = (ListView) findViewById(R.id.carddemo);
        listView.setAdapter(locationAdapter);

        //Sets the done button as well as the onClickListener
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < ls.size(); i++) {
                    locationsAndEvents newEvent = new locationsAndEvents(locationAdapter.getDescription(i), locationAdapter.getLocation(i), new Time(0, 0, 0), new Time(0, 0, 0), false);
                    System.out.println(locationAdapter.getDescription(i));
                    System.out.println(locationAdapter.getLocation(i));
                    events.add(newEvent);
                }

                userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(AddLocationsActivity.this));
                ArrayList<locationsAndEvents> oldEvents = user.getEvents();
                oldEvents.addAll(events);
                eventsRef.setValue(oldEvents);
                user.setEvents(oldEvents);
                Intent intent = new Intent(getBaseContext(), NotificationHomeActivity.class);
                startActivity(intent);
            }
        });

        //Displays a helpful dialog only if the user is first signing up
        if (intent.getStringExtra("from") != "home") {
            AlertDialog.Builder alert = new AlertDialog.Builder(AddLocationsActivity.this);
            alert.setTitle("Add Locations");
            final String message = "Please add some locations that you frequent, and a simple description";
            alert.setMessage(message).setCancelable(true).setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }

    }

    /**
     * Class that represents a location and is used by the Adapter
     * to inflate the views.
     */
    public class location{
        public String description;
        public String location;

        public location(String description, String location) {
            this.description = description;
            this.location = location;
        }
    }


    /**
     * Location adapter used to inflate the individual "add location" cards
     * into a list view
     */
    public class addLocationsAdapter extends ArrayAdapter<location> {
        private HashMap<String, String> textValues = new HashMap<String, String>();

        public addLocationsAdapter(Context context, ArrayList<location> locations) {
            super(context, 0, locations);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            location l = getItem(position);
            boolean convertViewWasNull = false;

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_location_row, parent, false);
                convertViewWasNull=true;
            }

            EditText description = (EditText) convertView.findViewById(R.id.add_location_title);

            AutoCompleteTextView location = (AutoCompleteTextView) convertView.findViewById(R.id.locationList);
            String s = "430 10th Street NW Building,890 Curran St NW,A French Building,Academy of Medicine,Advanced Wood Products,Aerospace Engineering Combustion Lab,Ajax Building,Alexander Memorial Coliseum,Alpha Chi Omega,Alpha Delta Chi,Alpha Delta Pi,Alpha Epsilon Pi,Alpha Gamma Delta,Alpha Tau Omega,Alpha Xi Delta,Alumni House,Alumni Park,Aquatic Center,Architecture Building East,Architecture Building West,Armstrong Residence,Army Armory,Athletic Association Conference Room,Baker Building,Baptist Student Union,Beringause Building,Beta Theta Pi,Bill Moore Student Success Center,Bill Moore Tennis Center,Bobby Dodd Stadium,Boggs Building,Brittain Dining Hall,Broadband Institute Residential Laboratory,Brown Residence,Building Construction and Center for GIS,Bunger Henry Building,Burger Bowl Field,Business Services Building,CEISMC,Caldwell Residence,Callaway Building,Campus Recreation Center,Carbon Neutral Energy Solutions Laboratory,Carnegie Building,Catholic Center,Centennial Research Building,Center Street Apartments,Center Street North,Center Street South,Center for Assistive Technology and Environmental Access,Centergy One,Central Receiving Property Control,Chandler Stadium,Chapin Building,Cherry Emerson,Cherry Street Library,Chi Phi,Chi Psi,Christian Campus Fellowship,Cloudman Residence,Clough Undergraduate Learning Commons,College of Architecture,College of Computing,College of Management,Commander Building,Coon Building,Couch Building,Crecine Apartments,Crosland Tower Northwest Library,Custodial Services Building,DM Smith Building,Daniel Laboratory,Delta Chi,Delta Sigma Phi,Delta Tau Delta,Delta Upsilon,Digital Fabrication Lab,Economic Development Building,Edge Athletic Center,Eighth Street Apartments,Engineered Biosystems Building,Engineering Center,Engineering Science and Mechanics Building,Engineers Bookstore,Facilities Building,Family Housing,Ferst Center for the Arts,Fiber Optic Network Building,Field Residence Hall,Fitten Residence Hall,Folk Residence Hall,Food Processing Technology Building,Ford Environmental Science and Technology Building,Fourth Street Apartments,Freeman Residence Hall,Fulmer Residence Hall,GTRI Conference Center,Georgia Tech Competition Center,Georgia Tech Global Learning Center,Georgia Tech Hotel and Conference Center,Georgia Tech Water Sports,Georgia Tech Yellow Jacket Ticketing Office,Glenn Residence Hall,Graduate Living Center,Griffin Track,Grinnell Building,Groseclose Building and ISyE Annex,Guggenheim Building,Habersham Building,Hall Building,Hanson Residence Hall,Harris Residence Hall,Harrison Residence Hall,Health Systems Institute,Hefner Residence Hall,Hemphill Avenue Apartments,Hinman Research Building,Holland Building,Hopkins Residence Hall,Howell Residence Hall,Howey Physics Building And Observatory,Institute of Paper Science and Technology,Instructional Center,Ivan Allen College,JC Shaw Sports Complex Athletic Association,Juniors Grill,Kappa Alpha,Kappa Sigma,Ken Byers Tennis Complex,Kessler Campanile,King Facilities Building,Klaus Advanced Computing Bldg,Klaus Advanced Computing Building,Lamar Allen Sustainable Education Building,Lambda Chi Alpha,Landscape Services,Love Building,Luck Building,Lutheran Center,Lyman Hall,Manufacturing Related Disiplines Complex,Manufacturing Research Center,Marcus Nanotechnology Building,Mason Civil Engineering Building,Matheson Residence Hall,Maulding Residence Hall,McCamish Pavilion,Mechanical Engineering Research Building,Mewborn Field,Molecular Science And Engineering Building,Montag Residence Hall,Montgomery Knight Building,Neely Nuclear Research Center,North Avenue Apartments,North Avenue Dining Hall,North Avenue East,North Avenue North,North Avenue South,North Avenue South Apartments,North Avenue West,North View Apartments,OIT Engineering,OKeefe Building,OKeefe Gym,Office of Human Resources,Office of Information Technology,Old CE Building,Parker H Petit Biotechnology Building,Paul Hefferna House,Perry Residence Hall,Pettit Microelectronics Research Center,Phi Delta Theta,Phi Gamma Delta,Phi Kappa Sigma,Phi Kappa Tau,Phi Kappa Theta,Phi Mu,Phi Sigma Kappa,Pi Kappa Alpha,Pi Kappa Alpha House,Pi Kappa Phi,Prince Gilbert Northwest Library,Psi Upsilon,R Kirk Landon Learning Center,Research Administration,Rice Center for Sports Performance,Rich Computer Center,Savant Building,Scheller College of Business,School of Applied Physiology,School of Physics,Sigma Alpha Epsilon,Sigma Chi,Sigma Nu,Sigma Phi Epsilon,Sixth Street Apartments,Skiles Classroom Building,Smith Residence Hall,Smithgall Student Services Building,Stamps Student Center Commons,Stein Hayes Goldin And Fourth Street E Houses,Structural Engineering and Materials Research Lab,Student Health Center,Student Health Services,Swann Building,Tau Kappa Epsilon,Tech Tower,Technology Square Research Building,Tenth and Home,Theta Chi,Theta Xi,Towers Residence Hall,UA Whitaker Building,Undergraduate Living Center,Van Leer School Of Electrical And Computer Engineering,Veron D and Helen D Crawford Pool,WH Emerson Building,Wardlaw Center,Weber Space Science And Technology Building Ii,Wenn Student Center,Wesley Foundation/Methodist Center,Westminster Christian Fellowship,Whitehead Building Health Center,Woodruff Residence Hall,Zelnak Basketball Practice Facility,Zeta Beta Tau,Zeta Tau Alpha";
            String[] LOCATIONS = s.split(",");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, LOCATIONS);
            location.setAdapter(adapter);

            if(convertViewWasNull) {
                description.addTextChangedListener(new GenericTextWatcher(description));
                location.addTextChangedListener(new GenericTextWatcher(location));
            }

            description.setTag("descriptionAt"+position);
            location.setTag("locationAt"+position);

            return convertView;
        }

        /**
         * Detects user input changes, so that the user input can be
         * retrieved later on.
         */
        private class GenericTextWatcher implements TextWatcher {
            private View view;
            private GenericTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            public void afterTextChanged(Editable editable) {

                String text = editable.toString();
                //save the value for the given tag :
                addLocationsAdapter.this.textValues.put((String)view.getTag(), editable.toString());
            }
        }

        public String getDescription(int position) {
            String result = textValues.get("descriptionAt"+position);
            if(result == null)
                result = "default";
            return result;
        }

        public String getLocation(int position) {
            String result = textValues.get("locationAt"+position);
            if(result == null)
                result = "default";
            return result;
        }
    }

}
