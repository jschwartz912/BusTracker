/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.Firebase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Allows users to add more custom events that they would like
 * to make notifications to and from. Also handles saving the information in
 * the local data storage as well as saving the information into the database.
 */
public class AddEventsActivity extends AppCompatActivity {
    Firebase firebaseRef;
    ArrayList<event> ls;
    ArrayList<locationsAndEvents> events;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Additional Events");
        setSupportActionBar(toolbar);

        //Setting up the database connection
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://gtbus-tracker.firebaseio.com/");
        final Firebase eventsRef = firebaseRef.child("users").child(firebaseRef.getAuth().getUid()).child("events");

        //Setting up the user local storage
        userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(this));
        events = user.getEvents();
        intent = getIntent();

        //Setting up listview
        ls = new ArrayList<>();
        ls.add(new event("", "", "", ""));
        ls.add(new event("", "", "", ""));


        //Setting up the adapter for the listview of the "add event" cards
        final addEventsAdapter eventsAdapter = new addEventsAdapter(this, ls);
        ListView listView = (ListView) findViewById(R.id.addEventList);
        listView.setAdapter(eventsAdapter);

        //Sets up the done button, and sets up the listener that would save the data when the
        //button is clicked.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < ls.size(); i++) {
                    Time start = addNotificationFragment.stringToTime(eventsAdapter.getStart(i));
                    Time end = addNotificationFragment.stringToTime(eventsAdapter.getEnd(i));
                    locationsAndEvents newEvent = new locationsAndEvents(eventsAdapter.getDescription(i), eventsAdapter.getLocation(i), start, end, true);
                    System.out.println(eventsAdapter.getDescription(i));
                    System.out.println(eventsAdapter.getLocation(i));
                    events.add(newEvent);
                }

                userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(AddEventsActivity.this));
                eventsRef.setValue(events);
                user.setEvents(events);

                if (intent.getStringExtra("from") == "home") {
                    Intent intent = new Intent(getBaseContext(), NotificationHomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), AddLocationsActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    /**
     * Class that represents an event
     */
    public class event{
        public String description;
        public String location;
        public String start;
        public String end;

        public event(String description, String location, String start, String end) {
            this.description = description;
            this.location = location;
            this.start = start;
            this.end = end;
        }
    }


    /**
     * Class used as an adapter for the listview,
     * this allows us to control what goes into the listview,
     * as well as ensure that we can get the user input after the user
     * has inputted everything.
     */
    public class addEventsAdapter extends ArrayAdapter<event> {
        private HashMap<String, String> textValues = new HashMap<String, String>();

        public addEventsAdapter(Context context, ArrayList<event> locations) {
            super(context, 0, locations);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            event l = getItem(position);
            boolean convertViewWasNull = false;

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_event_row, parent, false);
                convertViewWasNull=true;
            }

            EditText description = (EditText) convertView.findViewById(R.id.add_event_title);
            EditText start = (EditText) convertView.findViewById(R.id.input_start_time);
            EditText end = (EditText) convertView.findViewById(R.id.input_end_time);

            AutoCompleteTextView location = (AutoCompleteTextView) convertView.findViewById(R.id.locationList);
            String s = "430 10th Street NW Building,890 Curran St NW,A French Building,Academy of Medicine,Advanced Wood Products,Aerospace Engineering Combustion Lab,Ajax Building,Alexander Memorial Coliseum,Alpha Chi Omega,Alpha Delta Chi,Alpha Delta Pi,Alpha Epsilon Pi,Alpha Gamma Delta,Alpha Tau Omega,Alpha Xi Delta,Alumni House,Alumni Park,Aquatic Center,Architecture Building East,Architecture Building West,Armstrong Residence,Army Armory,Athletic Association Conference Room,Baker Building,Baptist Student Union,Beringause Building,Beta Theta Pi,Bill Moore Student Success Center,Bill Moore Tennis Center,Bobby Dodd Stadium,Boggs Building,Brittain Dining Hall,Broadband Institute Residential Laboratory,Brown Residence,Building Construction and Center for GIS,Bunger Henry Building,Burger Bowl Field,Business Services Building,CEISMC,Caldwell Residence,Callaway Building,Campus Recreation Center,Carbon Neutral Energy Solutions Laboratory,Carnegie Building,Catholic Center,Centennial Research Building,Center Street Apartments,Center Street North,Center Street South,Center for Assistive Technology and Environmental Access,Centergy One,Central Receiving Property Control,Chandler Stadium,Chapin Building,Cherry Emerson,Cherry Street Library,Chi Phi,Chi Psi,Christian Campus Fellowship,Cloudman Residence,Clough Undergraduate Learning Commons,College of Architecture,College of Computing,College of Management,Commander Building,Coon Building,Couch Building,Crecine Apartments,Crosland Tower Northwest Library,Custodial Services Building,DM Smith Building,Daniel Laboratory,Delta Chi,Delta Sigma Phi,Delta Tau Delta,Delta Upsilon,Digital Fabrication Lab,Economic Development Building,Edge Athletic Center,Eighth Street Apartments,Engineered Biosystems Building,Engineering Center,Engineering Science and Mechanics Building,Engineers Bookstore,Facilities Building,Family Housing,Ferst Center for the Arts,Fiber Optic Network Building,Field Residence Hall,Fitten Residence Hall,Folk Residence Hall,Food Processing Technology Building,Ford Environmental Science and Technology Building,Fourth Street Apartments,Freeman Residence Hall,Fulmer Residence Hall,GTRI Conference Center,Georgia Tech Competition Center,Georgia Tech Global Learning Center,Georgia Tech Hotel and Conference Center,Georgia Tech Water Sports,Georgia Tech Yellow Jacket Ticketing Office,Glenn Residence Hall,Graduate Living Center,Griffin Track,Grinnell Building,Groseclose Building and ISyE Annex,Guggenheim Building,Habersham Building,Hall Building,Hanson Residence Hall,Harris Residence Hall,Harrison Residence Hall,Health Systems Institute,Hefner Residence Hall,Hemphill Avenue Apartments,Hinman Research Building,Holland Building,Hopkins Residence Hall,Howell Residence Hall,Howey Physics Building And Observatory,Institute of Paper Science and Technology,Instructional Center,Ivan Allen College,JC Shaw Sports Complex Athletic Association,Juniors Grill,Kappa Alpha,Kappa Sigma,Ken Byers Tennis Complex,Kessler Campanile,King Facilities Building,Klaus Advanced Computing Bldg,Klaus Advanced Computing Building,Lamar Allen Sustainable Education Building,Lambda Chi Alpha,Landscape Services,Love Building,Luck Building,Lutheran Center,Lyman Hall,Manufacturing Related Disiplines Complex,Manufacturing Research Center,Marcus Nanotechnology Building,Mason Civil Engineering Building,Matheson Residence Hall,Maulding Residence Hall,McCamish Pavilion,Mechanical Engineering Research Building,Mewborn Field,Molecular Science And Engineering Building,Montag Residence Hall,Montgomery Knight Building,Neely Nuclear Research Center,North Avenue Apartments,North Avenue Dining Hall,North Avenue East,North Avenue North,North Avenue South,North Avenue South Apartments,North Avenue West,North View Apartments,OIT Engineering,OKeefe Building,OKeefe Gym,Office of Human Resources,Office of Information Technology,Old CE Building,Parker H Petit Biotechnology Building,Paul Hefferna House,Perry Residence Hall,Pettit Microelectronics Research Center,Phi Delta Theta,Phi Gamma Delta,Phi Kappa Sigma,Phi Kappa Tau,Phi Kappa Theta,Phi Mu,Phi Sigma Kappa,Pi Kappa Alpha,Pi Kappa Alpha House,Pi Kappa Phi,Prince Gilbert Northwest Library,Psi Upsilon,R Kirk Landon Learning Center,Research Administration,Rice Center for Sports Performance,Rich Computer Center,Savant Building,Scheller College of Business,School of Applied Physiology,School of Physics,Sigma Alpha Epsilon,Sigma Chi,Sigma Nu,Sigma Phi Epsilon,Sixth Street Apartments,Skiles Classroom Building,Smith Residence Hall,Smithgall Student Services Building,Stamps Student Center Commons,Stein Hayes Goldin And Fourth Street E Houses,Structural Engineering and Materials Research Lab,Student Health Center,Student Health Services,Swann Building,Tau Kappa Epsilon,Tech Tower,Technology Square Research Building,Tenth and Home,Theta Chi,Theta Xi,Towers Residence Hall,UA Whitaker Building,Undergraduate Living Center,Van Leer School Of Electrical And Computer Engineering,Veron D and Helen D Crawford Pool,WH Emerson Building,Wardlaw Center,Weber Space Science And Technology Building Ii,Wenn Student Center,Wesley Foundation/Methodist Center,Westminster Christian Fellowship,Whitehead Building Health Center,Woodruff Residence Hall,Zelnak Basketball Practice Facility,Zeta Beta Tau,Zeta Tau Alpha";
            String[] LOCATIONS = s.split(",");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, LOCATIONS);
            location.setAdapter(adapter);

            if(convertViewWasNull) {
                description.addTextChangedListener(new GenericTextWatcher(description));
                location.addTextChangedListener(new GenericTextWatcher(location));
                start.addTextChangedListener(new GenericTextWatcher(start));
                end.addTextChangedListener(new GenericTextWatcher(end));
            }

            description.setTag("descriptionAt"+position);
            location.setTag("locationAt"+position);
            start.setTag("startAt"+position);
            end.setTag("endAt"+position);

            return convertView;
        }

        /**
         * Class that detects the changes in the textfields, which then allows us
         * to retrieve the user inputs.
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
                addEventsAdapter.this.textValues.put((String)view.getTag(), editable.toString());
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

        public String getStart(int position) {
            String result = textValues.get("startAt"+position);
            if(result == null)
                result = "00:00";
            return result;
        }

        public String getEnd(int position) {
            String result = textValues.get("endAt"+position);
            if(result == null)
                result = "00:00";
            return result;
        }
    }

}
