/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity that prompts the user for the Georgia Tech
 * username and password, and scrapes OSCAR to retrieve a user's
 * class information. Also handles storing the information in the database
 * and the local storage.
 */
public class ImportScheduleActivity extends AppCompatActivity {

    Map<String,String> cookies;
    boolean loggedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedin = false;
        setContentView(R.layout.activity_import_schedule);

        //Obtain the username and password from the user.
        final EditText username = (EditText) findViewById(R.id.input_name);
        final EditText password = (EditText) findViewById(R.id.input_password);
        Button button = (Button) findViewById(R.id.btn_import);

        //Setup the button so that when it's clicked, the scraping will
        // be done in the background.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setUser = username.getText().toString();
                String setPassword = password.getText().toString();

                String[] params = {setUser, setPassword};
                new loginTask().execute(params);
            }
        });


    }

    /**
     * AsyncTask that logs the user into Georgia Tech system,
     * and makes sure that they are authenticated.
     */
    class loginTask extends AsyncTask<String, Void, Boolean> {
        private Exception exception;

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                String username = params[0];
                String password = params[1];
                Connection.Response loginFormRes = Jsoup.connect("https://login.gatech.edu/cas/login")
                        .method(Connection.Method.GET)
                        .execute();

                cookies = loginFormRes.cookies();
                Document loginForm = loginFormRes.parse();
                Elements hidden = loginForm.select("input[type=hidden]");

                Connection loginCon = Jsoup.connect("https://login.gatech.edu/cas/login")
                        //.data("cookieexists", "false")
                        .data("username", username)
                        .data("password", password)
                        .data("submit", "LOGIN")
                        .cookies(loginFormRes.cookies())
                        .method(Connection.Method.POST);

                for(Element e: hidden){
                    loginCon.data(e.attr("name"),e.attr("value"));
                }

                Connection.Response loginRes = loginCon.execute();
                cookies.putAll(loginRes.cookies());
                for(Element e: hidden){
                    System.out.println("name="+e.attr("name")+ " val="+e.attr("value"));
                }
                Connection.Response sso = Jsoup.connect("https://buzzport.gatech.edu/sso")
                        .cookies(cookies).method(Connection.Method.GET).execute();
                cookies.putAll(sso.cookies());
                loggedin= cookies.containsKey("CASTGT");
                return loggedin;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) new obtainTask().execute(true);
        }
    }

    /**
     * Class that obtains the class schedule once the user is logged into
     * Georgia Tech. Once the information is scraped, the classes are saved in the
     * form of locationsAndEvents class.
     */
    class obtainTask extends AsyncTask<Boolean, Void, List<locationsAndEvents>> {
        private Exception exception;
        List<locationsAndEvents> listOfClasses = new ArrayList<>();

        @Override
        protected List<locationsAndEvents> doInBackground(Boolean... params) {
            try {
                Connection.Response oscarAuth = Jsoup.connect("https://buzzport.gatech.edu/cp/ip/login?sys=sct&url=https://oscar.gatech.edu/pls/bprod/ztgkauth.zp_authorize_from_login")
                        .cookies(cookies).method(Connection.Method.GET).execute();
                cookies.putAll(oscarAuth.cookies());
                Document doc = Jsoup.connect("https://oscar.gatech.edu/pls/bprod/bwskfshd.P_CrseSchdDetl").cookies(cookies).get();
                Element termSelector = doc.getElementById("term_id");
                Elements terms = termSelector.children();
                boolean termFound = false;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                Date today = new Date();
                String chosenTerm = "";
                int i;
                for (i = 2; !termFound; i++) {
                    try {
                        Date termDay = sdf.parse(terms.get(i).attr("value"));
                        if (today.after(termDay)) {
                            termFound = true;
                            chosenTerm = terms.get(i).attr("value");
                        }
                    } catch (ParseException e) {
                        System.out.println("error parsing term");
                        e.printStackTrace();
                    }
                }
                System.out.println("chosen term: " + chosenTerm);
                doc = Jsoup.connect("https://oscar.gatech.edu/pls/bprod/bwskfshd.P_CrseSchdDetl")
                        .cookies(cookies)
                        .data("term_in", chosenTerm).get();
                Element schedCheck = doc.select("[class=pagebodydiv]").first();
                if (schedCheck.text().contains("not currently registered")) {
                    chosenTerm = terms.get(i).attr("value");
                    System.out.println("new term : " + chosenTerm);
                }
                doc = Jsoup.connect("https://oscar.gatech.edu/pls/bprod/bwskfshd.P_CrseSchdDetl")
                        .cookies(cookies)
                        .data("term_in", chosenTerm).get();
                Elements scheduleTables = doc.select("table[class=datadisplaytable]");
                int numClasses = scheduleTables.size() / 2;
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
                SimpleDateFormat sdf3 = new SimpleDateFormat("h:mm aa");
                Map<String, String> weekdays = new HashMap<String, String>();
                weekdays.put("M", "Monday");
                weekdays.put("T", "Tuesday");
                weekdays.put("W", "Wednesday");
                weekdays.put("R", "Thursday");
                weekdays.put("F", "Friday");
                for (int j = 1; j < scheduleTables.size(); j = j + 2) {
                    Element dataTable = scheduleTables.get(j);
                    Element classNameTable = scheduleTables.get(j - 1);
                    String className = classNameTable.children().first().text();
                    System.out.println("Class Name: " + className);
                    Element data = dataTable.children().get(1).children().get(1);
                    String[] times = data.children().get(1).text().split("-");
                    String beginTime = times[0].trim();
                    String endTime = times[1].trim();
                    String days = data.children().get(2).text();
                    String location = data.children().get(3).text();
                    String dateRange = data.children().get(4).text(); //the date range for the class (could be used later)
                    Date beginDate = null;
                    Date endDate = null;

                    System.out.println("Location: " + location);
                    System.out.println("Days: " + days);
                    System.out.println(beginTime);
                    System.out.println(endTime);
                    //for(int k = 0; k < days.trim().length(); k++){

                    String begin = beginTime;//+ " "+weekdays.get(""+days.charAt(k));
                    String end = endTime;//+ " "+weekdays.get(""+days.charAt(k));
                    try {
                        beginDate = sdf2.parse(begin);
                    } catch (ParseException e) {
                        try {
                            beginDate = sdf3.parse(begin);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        endDate = sdf2.parse(end);
                    } catch (ParseException e) {
                        try {
                            endDate = sdf3.parse(end);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                    String[] beginSplit = beginTime.split(":|\\s+");
                    System.out.println(beginSplit[0] + "-" + beginSplit[1]);
                    String[] endSplit = endTime.split(":|\\s+");
                    Time bTime = new Time((beginSplit[2].equals("pm") && !beginSplit[0].equals("12")? 12 + Integer.parseInt(beginSplit[0]) : Integer.parseInt(beginSplit[0])), Integer.parseInt(beginSplit[1]), 0);
                    Time eTime = new Time((endSplit[2].equals("pm") && !endSplit[0].equals("12")? 12 + Integer.parseInt(endSplit[0]) : Integer.parseInt(endSplit[0])), Integer.parseInt(endSplit[1]), 0);

                    System.out.println("Begin time: " + bTime);
                    System.out.println("End Time: " + eTime);
                    System.out.println("Begin Date: " + beginDate.toString());
                    System.out.println("End Date: " + endDate);
                    System.out.println("\n");
                    String stringOfDays = "";
                    for(char c:days.toCharArray()){
                        stringOfDays +=weekdays.get(""+c);
                    }
                    //TODO:change locations and Events to incorporate stringOfDays
                    locationsAndEvents sClass = new locationsAndEvents(className,location,bTime, eTime,true);
                    listOfClasses.add(sClass);
                    //}
                }
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
            return listOfClasses;
        }

        @Override
        protected void onPostExecute(List<locationsAndEvents> listOfClasses) {
            userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(getBaseContext()));
            ArrayList<locationsAndEvents> events = user.getEvents();
            events.addAll(listOfClasses);
            user.setEvents(events);
            Firebase firebaseRef = new Firebase("https://gtbus-tracker.firebaseio.com/");
            firebaseRef.child("users").child(firebaseRef.getAuth().getUid()).child("events").setValue(events);

            AlertDialog.Builder alert = new AlertDialog.Builder(ImportScheduleActivity.this);
            alert.setTitle("Add Events");
            final String message = "Your classes have been imported as events. Would you like to add more events?";
            alert.setMessage(message).setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getBaseContext(), AddLocationsActivity.class);
                    startActivity(intent);
                }
            }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getBaseContext(), AddEventsActivity.class);
                    startActivity(intent);
                }
            }).show();

        }
    }


}
