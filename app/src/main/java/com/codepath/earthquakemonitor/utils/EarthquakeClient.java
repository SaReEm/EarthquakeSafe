package com.codepath.earthquakemonitor.utils;

import android.content.Context;
import android.util.Log;

import com.codepath.earthquakemonitor.models.Filters;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by emilie on 10/8/17.
 */

public class EarthquakeClient {

    public static EarthquakeClient earthquakeClient;
    public static AsyncHttpClient client;
    private final String TAG = "EarthquakeClientTAG";
    private static final String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";

    public static EarthquakeClient getInstance(Context context){
        if (earthquakeClient == null){
            earthquakeClient = new EarthquakeClient(context);
        }
        return earthquakeClient;

    }
    private EarthquakeClient(Context context) {
        client = new AsyncHttpClient();
    }

    /**
     * buildParamWithFilter is building Parameter to prepare for the request according to the filter
     */
    private RequestParams buildParamWithFilter(Filters filter){
        RequestParams param = new RequestParams();
        if(filter.isUsePosition()){
            param.put("longitude", filter.getLongitude());
            param.put("latitude", filter.getLatitude());

            //If we don't specify a radius we have to use one in a request calling with along and a lat
            // in order to create the appropriate circle of search
            if(!filter.isUseDistance()){
                param.put("maxradiuskm", 200.0);
            }
        }
        if(filter.isUseMinMagnitude()){
            param.put("minmagnitude", filter.getMinMagnitude());
        }
        if(filter.isUseStartTime()){
            param.put("starttime", filter.getStartTime());
        }
        if(filter.isUseDistance()){
            param.put("maxradiuskm", filter.getDistance());
        }
        if(filter.isUseDepth()){
            param.put("maxdepth", filter.getMaxDepth());
        }

        return param;
    }

    /**
     * getEarthquakeWithFilter calls for earthquake around a specific place according to filters
     */
    public void getEarthquakeWithFilter(Filters filter,
                                        AsyncHttpResponseHandler handler){
        String apiUrl = BASE_URL + "query";
        RequestParams params = buildParamWithFilter(filter);
        params.put("format", "geojson");
        Log.d(TAG, "Send request : " + apiUrl + "?" + params.toString());
        client.get(apiUrl, params, handler);
    }

    /**
     * getEarthquakeAroundPoint gives earthquake that happened in the last 30 days at the specified
     * location with the specified radius
     */
    public void getEarthquakeAroundPoint(Double longitude, Double latitude, Double maxradiuskm,
                                         String starttime, AsyncHttpResponseHandler handler){
        String apiUrl = BASE_URL + "query";
        RequestParams params = new RequestParams();
        params.put("format", "geojson");
        params.put("starttime", starttime);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("maxradiuskm", maxradiuskm);

        Log.d(TAG, "Send request : " + apiUrl + "?" + params.toString());
        client.get(apiUrl, params, handler);

    }

    /**
     * getQueryTestHandler THIS FUNCTION IS ONLY TO TEST THE API.
     */
    public void getQueryTestHandler(AsyncHttpResponseHandler handler) {
        String apiUrl = BASE_URL + "query";
        RequestParams params = new RequestParams();
        params.put("format", "geojson");
        params.put("starttime", "2017-01-01");
        params.put("endtime", "2017-01-02");
        params.put("minmagnitude", "5");


        client.get(apiUrl, params, handler);
    }

}
