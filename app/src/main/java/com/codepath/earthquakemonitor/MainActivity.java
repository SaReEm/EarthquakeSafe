package com.codepath.earthquakemonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.earthquakemonitor.JsonHttpResponseHandler.EarthquakesJsonHttpResponseHandler;
import com.codepath.earthquakemonitor.models.Filters;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        ////Piece of code to test earthquake API /////////////////////////////////////////
        EarthquakeClient client = EarthquakeClient.getInstance(this);
        EarthquakesJsonHttpResponseHandler handler = new EarthquakesJsonHttpResponseHandler();
        Double currentLongitude = 128.2355;
        Double currentLatitude = 3.5887;
        Filters filter = new Filters();
        filter.setDistance(20.0);
        filter.setStartTime("2017-01-01");
        client.getEarthquakeAroundPointWithFilter(128.2355, 3.5887, filter, handler);
        ///////////////////////////////////////////////////////////////////////////////////
    }
}
