/*
 * GT Bus Tracker Application version 0.0.1
 * Released in 2016
 * Created by Nicolette Fink, Yueting Lee, Jared Moore, Jules Schwartz, Hualong Zhang
 *
 */
package juniordesignteam25.bustracker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Allows the user to sign up to the service. This class creates a
 * connection with the firebase and creates a user using the
 * Firebase API. Also, this starts the process of inputting the user's
 * preferences into both the local data storage and the database.
 */
public class SignupActivity extends AppCompatActivity {
    private Firebase firebaseRef;

    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_name) EditText nameText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.btn_signup) Button signUp;
    @Bind(R.id.link_login) TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        //Setting up the local storage
        userLocalStore user = new userLocalStore(PreferenceManager.getDefaultSharedPreferences(this));
        user.logout();

        //Setting up Firebase
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://gtbus-tracker.firebaseio.com/");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, loginPage.class);
                startActivity(intent);
            }
        });
    }

    //Signing in the user
    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = nameText.getText().toString();
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

        //Creating the user with Firebase
        firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                //firebaseRef.updateChildren(stringObjectMap);
                Map<String, Object> userData = new HashMap<String, Object>();
                userData.put("email", email);
                userData.put("name", name);
                firebaseRef.child("users").child(stringObjectMap.get("uid").toString()).updateChildren(userData);
                firebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignupActivity.this);
                        alert.setTitle("Import Schedule");
                        final String message = "Would you like to import your classes from OSCAR as events using your Georgia Tech username and password?";
                        alert.setMessage(message).setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext(), AddLocationsActivity.class);
                                startActivity(intent);
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getBaseContext(), ImportScheduleActivity.class);
                                startActivity(intent);
                            }
                        }).show();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getBaseContext(), "Signup Error: " + firebaseError.getMessage(), Toast.LENGTH_LONG);
                Toast.makeText(getBaseContext(), "Please try again", Toast.LENGTH_SHORT);
            }
        });
    }


    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signUp.setEnabled(false);
    }

    //Validating the user inputs
    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

}
