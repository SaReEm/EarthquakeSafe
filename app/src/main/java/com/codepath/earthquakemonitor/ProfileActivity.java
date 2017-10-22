package com.codepath.earthquakemonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.codepath.earthquakemonitor.utils.PushUtils;

public class ProfileActivity extends AppCompatActivity
{
    private final String TAG = "ProfileActivity";
    private Button btnLogout;
    private Switch swMySafeStatus;

    public static final String CHANNEL_NAME = "earthquake-2017";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Handle log out onClick
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        swMySafeStatus = findViewById(R.id.swMySafeStatus);
        swMySafeStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG, "send: " + b);
                PushUtils.sendPushNotifSafeStatus(b, CHANNEL_NAME);
            }
        });
    }


}
