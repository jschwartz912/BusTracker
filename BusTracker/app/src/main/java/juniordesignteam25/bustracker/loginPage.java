/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */

package juniordesignteam25.bustracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main Login class of the application, handles authentication with the user as well
 * as setting up the user for local storage.
 */

public class loginPage extends AppCompatActivity {
    userLocalStore localUser;
    protected String username;
    protected String password;
    private Firebase firebaseRef;

    @Bind(R.id.usernameSubmission) EditText usernameInput;
    @Bind(R.id.passwordSubmission) EditText passwordInput;
    @Bind(R.id.loginButton) Button submitButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);

        //Dynamically setting the fonts in the View
        Typeface yanoneReg = Typeface.createFromAsset(getAssets(),"fonts/YanoneKaffeesatz-Regular.ttf");
        Typeface yanoneLight = Typeface.createFromAsset(getAssets(), "fonts/YanoneKaffeesatz-Light.ttf");


        //Setting up the Firebase connection
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://gtbus-tracker.firebaseio.com/");

        //Stores username and password and automatically redirects to ExportScheduleActivity
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();

                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        submitButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(loginPage.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        firebaseRef.authWithPassword(username, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Query userRef = firebaseRef.child("users").child(authData.getUid());
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //getting the name, email, and class information of each user
                        String name = (String) dataSnapshot.child("name").getValue();
                        String email = (String) dataSnapshot.child("email").getValue();
                        ArrayList<locationsAndEvents> events = new ArrayList<locationsAndEvents>();

                        DataSnapshot classSnap = dataSnapshot.child("events");
                        for (DataSnapshot child : classSnap.getChildren()) {
                            events.add(child.getValue(locationsAndEvents.class));
                        }

                        ArrayList<notification> notifications = new ArrayList<notification>();
                        DataSnapshot notifSnap = dataSnapshot.child("notifications");
                        for (DataSnapshot child : notifSnap.getChildren()) {
                            notifications.add(child.getValue(notification.class));
                        }

                        localUser = new userLocalStore(name, username, events, notifications, PreferenceManager.getDefaultSharedPreferences(loginPage.this));
                        localUser.login();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                Intent intent = new Intent(loginPage.this, NotificationHomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(getBaseContext(), "Login Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onLoginSuccess() {
        submitButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        submitButton.setEnabled(false);
    }

    public boolean validate() {
        boolean valid = true;

        String email = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            usernameInput.setError("enter a valid email address");
            valid = false;
        } else {
            usernameInput.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordInput.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        return valid;
    }

}
