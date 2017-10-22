package com.codepath.earthquakemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.earthquakemonitor.models.User;
import com.crashlytics.android.Crashlytics;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {
    EditText etName;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Button btnFBLogIn = findViewById(R.id.btn_fblogin);
        btnFBLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseFBLogin();
            }
        });

        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);

        // Handle sigin button onClick
        Button btnLogIn = findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etName.getText().toString();
                String password = etPassword.getText().toString();
                if (userName == null || password == null) {
                    return;
                }
                ParseUser.logInInBackground(userName, password, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Intent i = new Intent(LoginActivity.this, MapActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT);
                        }
                    }
                });
                Intent i = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(i);
            }
        });

        // Handle click sign up button
        TextView btnSignUp = (TextView) findViewById(R.id.tvSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                // uncomment to test push
                //PushTest.sendPushTest();
            }
        });

        //Piece of code to test earthquake API /////////////////////////////////////////
//        EarthquakeClient client = EarthquakeClient.getInstance(this);
//        EarthquakesJsonHttpResponseHandler handler = new EarthquakesJsonHttpResponseHandler();
//        Double currentLatitude = -122.077655;
//        Double currentLongitude = 37.395605;
//        Filters filter = Filters.getInstance();
//        client.getEarthquakeWithFilter(currentLatitude, currentLongitude, filter, handler);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void parseFBLogin() {
        ArrayList<String> permissions = new ArrayList();
        permissions.add("public_profile");
        permissions.add("user_friends");
        ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions,
                new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (err != null) {
                    Log.d("MyApp", "Uh oh. Error occurred" + err.toString());
                } else if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    //change the parseUser to User
                    /*User toUser = new User(user);
                    toUser.saveInBackground();*/

                    Intent i = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("MyApp", "User logged in through Facebook!");
                    Intent i = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(i);
                }

            }
        });
    }

}
