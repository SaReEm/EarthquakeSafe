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
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import static android.R.attr.permission;

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

        ////Piece of code to test earthquake API /////////////////////////////////////////
//        EarthquakeClient client = EarthquakeClient.getInstance(this);
//        EarthquakesJsonHttpResponseHandler handler = new EarthquakesJsonHttpResponseHandler();
//        Double currentLongitude = 128.2355;
//        Double currentLatitude = 3.5887;
//        Filters filter = new Filters();
//        filter.setDistance(20.0);
//        filter.setStartTime("2017-01-01");
//        client.getEarthquakeAroundPointWithFilter(128.2355, 3.5887, filter, handler);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void parseFBLogin() {
        ArrayList<String> permissions = new ArrayList();
        permissions.add("email");
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
                    User toUser = new User(user);
                    toUser.saveInBackground();

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
