package com.codepath.earthquakemonitor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.earthquakemonitor.EarthquakeClient;
import com.codepath.earthquakemonitor.models.Filters;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hezhang on 10/15/17.
 */

public class HomeEarthquakeListFragment extends EarthquakeListFragment
{
    private EarthquakeClient client;
    Filters filter = Filters.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        client = EarthquakeClient.getInstance(getContext());
        populateEarthquakeList();
    }

    private void populateEarthquakeList() {
        client.getEarthquakeAroundPointWithFilter(-122.077655, 37.395605, filter,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        addItems(response);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d("TwitterClient", response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("TwitterClient", responseString);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.d("TwitterClient", errorResponse.toString());
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("TwitterClient", errorResponse.toString());
                        throwable.printStackTrace();
                    }
                });
    }

}


