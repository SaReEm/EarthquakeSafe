package com.codepath.earthquakemonitor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.earthquakemonitor.fragments.MyProfileFragment;
import com.codepath.earthquakemonitor.utils.FBClient;
import com.codepath.earthquakemonitor.utils.ImageStorageTest;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {
    EditText etName;
    EditText etPassword;
    TextView tvSafe;

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

        // Find views
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        tvSafe = findViewById(R.id.tvSafe);

        //Perform animation
        ObjectAnimator moveAnim = ObjectAnimator.ofFloat(tvSafe, "Y", 30);
        moveAnim.setDuration(1000);
        moveAnim.setInterpolator(new BounceInterpolator());
        moveAnim.start();

        // Branch init
        Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Log.i("BRANCH SDK", referringParams.toString());
                } else {
                    Log.i("BRANCH SDK", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);

        // Handle sigin button onClick
        Button btnLogIn = findViewById(R.id.btn_login);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*MyProfileFragment myProfileFragment = MyProfileFragment.getInstance(callTest());
                myProfileFragment.show(getSupportFragmentManager(), "test");*/
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

    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
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
                    Log.d("MyApp", "User signed up and logged in thr    ough Facebook!");
                    //change the parseUser to User
                    /*User toUser = new User(user);
                    toUser.saveInBackground();*/
                    Intent i = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(i);
                    user.put("safeStatus", "NC");
                    String token = null;
                    token = AccessToken.getCurrentAccessToken().getToken();
                    getFBUserName(token, user);
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

    private void getFBUserName(final String token, final ParseUser user) {
        FBClient fbClient = new FBClient();
        fbClient.getCurrentFBUserName(token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user.put("username", response.getString("name"));
                    user.saveInBackground();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
            }
        });
    }

/*    public ParseObject callTest() {
        return ImageStorageTest.save();
    }*/

}
