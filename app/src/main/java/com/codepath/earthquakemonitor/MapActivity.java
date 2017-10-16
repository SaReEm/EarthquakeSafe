package com.codepath.earthquakemonitor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.earthquakemonitor.fragments.EarthquakeListFragment;
import com.codepath.earthquakemonitor.fragments.FilterDialogFragment;
import com.codepath.earthquakemonitor.fragments.HomeEarthquakeListFragment;
import com.codepath.earthquakemonitor.models.Earthquake;

import org.parceler.Parcels;

import java.io.IOException;

//@RuntimePermissions
public class MapActivity extends AppCompatActivity
    implements EarthquakeListFragment.EarthquakeSelectedListener,DialogInterface.OnDismissListener
{

//    private SupportMapFragment mapFragment;
//    private GoogleMap map;
//    private LocationRequest mLocationRequest;
//    Location mCurrentLocation;
//    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
//    private long FASTEST_INTERVAL = 5000; /* 5 secs */
//
//    private final static String KEY_LOCATION = "location";

    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private HomeEarthquakeListFragment homeEarthquakeListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

//        if (TextUtils.isEmpty(getResources().getString(R.string.google_maps_api_key))) {
//            throw new IllegalStateException("You forgot to supply a Google Maps API key");
//        }

//        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
//            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
//            // is not null.
//            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//        }

        //TODO Create the earthquake list fragment
        // Create the user fragment
        homeEarthquakeListFragment = new HomeEarthquakeListFragment();
        // Display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Make change
        ft.replace(R.id.flContainer, homeEarthquakeListFragment);
        // Commit
        ft.commit();

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.miProfile) {
            // Start profile activity
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.miSettings) {
            FragmentManager fm = getSupportFragmentManager();
            FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance();
            filterDialogFragment.show(fm, "fragment_filters");
            return true;
        } else if (id == R.id.miFriends) {
            // Start friends activity
            Intent i = new Intent(this, FriendsListActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        //Fragment dialog had been dismissed
        homeEarthquakeListFragment.populateEarthquakeList();
    }


    //    protected void loadMap(GoogleMap googleMap) {
//        map = googleMap;
//        if (map != null) {
//            // Map is ready
//            Toast.makeText(this, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
//            MapActivityPermissionsDispatcher.getMyLocationWithCheck(this);
//            MapActivityPermissionsDispatcher.startLocationUpdatesWithCheck(this);
//        } else {
//            Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        MapActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
//
//    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//    void getMyLocation() {
//        //noinspection MissingPermission
//        map.setMyLocationEnabled(true);
//
//        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
//        //noinspection MissingPermission
//        locationClient.getLastLocation()
//                .addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            onLocationChanged(location);
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("MapActivity", "Error trying to get last GPS location");
//                        e.printStackTrace();
//                    }
//                });
//    }
//
//    /*
//     * Called when the Activity becomes visible.
//    */
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    /*
//     * Called when the Activity is no longer visible.
//	 */
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    private boolean isGooglePlayServicesAvailable() {
//        // Check that Google Play services is available
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        // If Google Play services is available
//        if (ConnectionResult.SUCCESS == resultCode) {
//            // In debug mode, log the status
//            Log.d("Location Updates", "Google Play services is available.");
//            return true;
//        } else {
//            // Get the error dialog from Google Play services
//            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
//                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
//
//            // If Google Play services can provide an error dialog
//            if (errorDialog != null) {
//                // Create a new DialogFragment for the error dialog
//                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
//                errorFragment.setDialog(errorDialog);
//                errorFragment.show(getSupportFragmentManager(), "Location Updates");
//            }
//
//            return false;
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Display the connection status
//
//        if (mCurrentLocation != null) {
//            Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
//            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
//            map.animateCamera(cameraUpdate);
//        } else {
//            Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
//        }
//        MapActivityPermissionsDispatcher.startLocationUpdatesWithCheck(this);
//    }
//
//    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
//    protected void startLocationUpdates() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        mLocationRequest.setInterval(UPDATE_INTERVAL);
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
//        builder.addLocationRequest(mLocationRequest);
//        LocationSettingsRequest locationSettingsRequest = builder.build();
//
//        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
//        settingsClient.checkLocationSettings(locationSettingsRequest);
//        //noinspection MissingPermission
//        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        onLocationChanged(locationResult.getLastLocation());
//                    }
//                },
//                Looper.myLooper());
//    }
//
//    public void onLocationChanged(Location location) {
//        // GPS may be turned off
//        if (location == null) {
//            return;
//        }
//
//        // Report to the UI that the location was updated
//
//        mCurrentLocation = location;
//        String msg = "Updated Location: " +
//                Double.toString(location.getLatitude()) + "," +
//                Double.toString(location.getLongitude());
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
//
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//    // Define a DialogFragment that displays the error dialog
//    public static class ErrorDialogFragment extends android.support.v4.app.DialogFragment {
//
//        // Global field to contain the error dialog
//        private Dialog mDialog;
//
//        // Default constructor. Sets the dialog field to null
//        public ErrorDialogFragment() {
//            super();
//            mDialog = null;
//        }
//
//        // Set the dialog to display
//        public void setDialog(Dialog dialog) {
//            mDialog = dialog;
//        }
//
//        // Return a Dialog to the DialogFragment.
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            return mDialog;
//        }
//    }

    @Override
    public void onEarthquakeClicked(Earthquake earthquake)
    {
        if(isNetworkAvailable() && isOnline()) {

            Intent intent = new Intent(this, EarthquakeDetailActivity.class);
            intent.putExtra("earthquake", Parcels.wrap(earthquake));
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Check you internet connection", Toast.LENGTH_SHORT).show();
        }
//        // Set the color of the marker to green
//        BitmapDescriptor defaultMarker =
//                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
//        // listingPosition is a LatLng point
//        LatLng listingPosition = new LatLng(-33.867, 151.206);
//        // Create the marker on the fragment
//        Marker mapMarker = map.addMarker(new MarkerOptions()
//                .position(listingPosition)
//                .title("Some title here")
//                .snippet("Some description here")
//                .icon(defaultMarker));
//        Toast.makeText(getApplicationContext(), "Please launch webpage for details!", Toast.LENGTH_LONG).show();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
}
