package com.codepath.earthquakemonitor.fragments;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.earthquakemonitor.Adapters.EarthquakeAdapter;
import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.models.Earthquake;
import com.codepath.earthquakemonitor.models.Filters;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Created by hezhang on 10/14/17.
 */

@RuntimePermissions
public class EarthquakeListFragment extends Fragment
    implements EarthquakeAdapter.EarthquakeAdapterListener
{
    private final String TAG = "EarthquakeListFragment";

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private LocationRequest mLocationRequest;
    Location mCurrentLocation;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 50000; /* 5 secs */

    private Filters filter = Filters.getInstance();

    private final static String KEY_LOCATION = "location";


    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    public interface EarthquakeSelectedListener {
        public void onEarthquakeClicked(Earthquake earthquake);
    }

    private RecyclerView rvEarthquakes;
    private ArrayList<Earthquake> earthquakes;
    private EarthquakeAdapter earthquakeAdapter;
    private ArrayList<Marker> mMarkers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        // Create the map fragment
        mapFragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(getContext(), "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }



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

        mMarkers = new ArrayList<>();
        return v;


    }

    public void cleanEarthquakes(){
        if(earthquakes != null){
            earthquakes.clear();
            earthquakeAdapter.notifyDataSetChanged();
            deleteAllMarkers();
        }
    }

    public void addItems(JSONObject response) {
        //todo here modifying what to do with the answer
        try {
            JSONArray jsonArray = null;
            jsonArray = response.getJSONArray("features");
            int nbEarthquake = jsonArray.length();
            if(nbEarthquake == 0){
                Toast.makeText(getContext(),"No eathquake found with this filter", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "Found " + nbEarthquake + " earthquakes");
            Toast.makeText(getActivity(), "Found " + nbEarthquake + " earthquakes",Toast.LENGTH_SHORT).show();
            for (int i = 0; i < nbEarthquake; i++) {
                Earthquake earthquake = Earthquake.fromJson(jsonArray.getJSONObject(i));
                earthquakes.add(earthquake);
                earthquakeAdapter.notifyItemInserted(earthquakes.size() - 1);

                addMarkerOnEarthquake(earthquake);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addMarkerOnEarthquake(Earthquake earthquake){
        // Customize marker
        IconGenerator iconGenerator = new IconGenerator(getContext());
        // Possible color options:
        // STYLE_WHITE, STYLE_RED, STYLE_BLUE, STYLE_GREEN, STYLE_PURPLE, STYLE_ORANGE
        iconGenerator.setStyle(IconGenerator.STYLE_ORANGE);
        // Swap text here to live inside speech bubble
        Bitmap bitmap = iconGenerator.makeIcon();
        // Use BitmapDescriptorFactory to create the marker
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

        // listingPosition is a LatLng point
        LatLng listingPosition = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());
        // Create the marker on the fragment
        Marker mapMarker = map.addMarker(new MarkerOptions()
                .position(listingPosition)
                .title("Some title here")
                .snippet("Some description here")
                .icon(icon));
        mMarkers.add(mapMarker);
    }

    private void deleteAllMarkers(){
        int size = mMarkers.size();
        for (int i = 0; i < size; i++){
            mMarkers.get(i).remove();
        }
    }

//
    @Override
    public void onItemSelected(View view, int position) {
        Earthquake earthquake = earthquakes.get(position);
        ((EarthquakeSelectedListener) getActivity()).onEarthquakeClicked(earthquake);
    }

    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            Log.d(TAG, "Map Fragment was loaded properly!");
            EarthquakeListFragmentPermissionsDispatcher.getMyLocationWithCheck(this);
            EarthquakeListFragmentPermissionsDispatcher.startLocationUpdatesWithCheck(this);
        } else {
            Log.d(TAG, "Error - Map was null!!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EarthquakeListFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void getMyLocation() {
        //noinspection MissingPermission
        map.setMyLocationEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(getActivity());
        //noinspection MissingPermission
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    /*
     * Called when the Activity becomes visible.
    */
    @Override
    public void onStart() {
        super.onStart();
    }

    /*
     * Called when the Activity is no longer visible.
	 */
    @Override
    public void onStop() {
        super.onStop();
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                EarthquakeListFragment.ErrorDialogFragment errorFragment = new EarthquakeListFragment.ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getFragmentManager(), "Location Updates");
            }

            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Display the connection status

        if (mCurrentLocation != null) {
            Log.d(TAG, "GPS location was found!");
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
        } else {
            Log.d(TAG, "Current location was null, enable GPS on emulator!");
        }
        EarthquakeListFragmentPermissionsDispatcher.startLocationUpdatesWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        getFusedLocationProviderClient(getActivity()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // GPS may be turned off
        if (location == null) {
            Toast.makeText(getContext(),"2-TurnOn your GPS to sea earthquake around you", Toast.LENGTH_SHORT).show();
            return;
        }

        // Report to the UI that the location was updated

        mCurrentLocation = location;
        filter.setPosition(location.getLatitude(),location.getLongitude());
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.d(TAG, msg);

        // Center the camera to the current location and zoom in
        LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,10);
        map.animateCamera(cameraUpdate);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends android.support.v4.app.DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

}
