package com.codepath.earthquakemonitor.JsonHttpResponseHandler;

import android.util.Log;

import com.codepath.earthquakemonitor.models.Earthquake;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by emilie on 10/9/17.
 */

public class EarthquakesJsonHttpResponseHandler extends JsonHttpResponseHandler{

    private final String TAG = "EQJsonHandlerTAG";

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
        //todo here modifying what to do with the answer
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        try {
            JSONArray jsonArray = null;
            jsonArray = object.getJSONArray("features");
            Log.d(TAG, "Found " + jsonArray.length() + " earthquakes");
            for (int i = 0; i < jsonArray.length(); i++) {
                Earthquake earthquake = Earthquake.fromJson(jsonArray.getJSONObject(i));
                earthquakes.add(earthquake);
            }
        }
         catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d(TAG, errorResponse.toString());
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.d(TAG, errorResponse.toString());
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Log.d(TAG, responseString);
        super.onFailure(statusCode, headers, responseString, throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.d(TAG, responseString);
        super.onSuccess(statusCode, headers, responseString);
    }
}
