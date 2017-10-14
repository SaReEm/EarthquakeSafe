package com.codepath.earthquakemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        LoginButton btnFBLogIn = findViewById(R.id.btn_fblogin);
        //btnFBLogIn.setReadPermissions("email");
        btnFBLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseFBLogin();
            }
        });

        // Handle sigin button onClick
        Button btnLogIn = (Button) findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, null, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    user.saveInBackground();
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                }
            }
        });
    }

}
