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
import android.view.Window;
import android.widget.RelativeLayout;
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
        
        // Create the user fragment
        // Display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        homeEarthquakeListFragment = new HomeEarthquakeListFragment();
        if(homeEarthquakeListFragment.isAdded()){
            ft.show(homeEarthquakeListFragment);
        }
        else{
            // Make change
            ft.add(R.id.flContainer, homeEarthquakeListFragment);
        }
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

    @Override
    public void onEarthquakeClicked(Earthquake earthquake)
    {
        if (isNetworkAvailable() && isOnline()) {
            Intent intent = new Intent(this, EarthquakeDetailActivity.class);
            intent.putExtra("earthquake", Parcels.wrap(earthquake));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Check you internet connection", Toast.LENGTH_SHORT).show();
        }
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
