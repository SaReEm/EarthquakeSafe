package com.codepath.earthquakemonitor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.earthquakemonitor.Adapters.EarthquakeAdapter;
import com.codepath.earthquakemonitor.EarthquakeClient;
import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Earthquake;
import com.codepath.earthquakemonitor.models.Filters;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hezhang on 10/14/17.
 */

public class EarthquakeListFragment extends Fragment
    implements EarthquakeAdapter.EarthquakeAdapterListener
{
    private final String TAG = "EarthquakeListFragment";
//
//    private EarthquakeClient client;
//    Filters filter = Filters.getInstance();

    public interface EarthquakeSelectedListener {
        public void onEarthquakeClicked(Earthquake earthquake);
    }

    RecyclerView rvEarthquakes;
    ArrayList<Earthquake> earthquakes;
    EarthquakeAdapter earthquakeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_earthquake_list, container, false);
        // Find the recycler view
        rvEarthquakes = (RecyclerView) v.findViewById(R.id.rvEarthquake);
        // Construct earthquakes array
        earthquakes = new ArrayList<>();
        // Construct the adapter from data source
        earthquakeAdapter = new EarthquakeAdapter(earthquakes, this);
        // Set the adapter
        rvEarthquakes.setAdapter(earthquakeAdapter);
        // Recycler view setup
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvEarthquakes.setLayoutManager(linearLayoutManager);
        return v;
    }

    public void addItems(JSONObject response) {
        //todo here modifying what to do with the answer
        try {
            JSONArray jsonArray = null;
            jsonArray = response.getJSONArray("features");
            Log.d(TAG, "Found " + jsonArray.length() + " earthquakes");
            for (int i = 0; i < jsonArray.length(); i++) {
                Earthquake earthquake = Earthquake.fromJson(jsonArray.getJSONObject(i));
                earthquakes.add(earthquake);
                earthquakeAdapter.notifyItemInserted(earthquakes.size() - 1);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
    @Override
    public void onItemSelected(View view, int position) {
        Earthquake earthquake = earthquakes.get(position);
        ((EarthquakeSelectedListener) getActivity()).onEarthquakeClicked(earthquake);
    }
}
