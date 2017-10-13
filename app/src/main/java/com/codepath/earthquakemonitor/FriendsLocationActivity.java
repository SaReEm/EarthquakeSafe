package com.codepath.earthquakemonitor;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.earthquakemonitor.fragments.FilterDialogFragment;

public class FriendsLocationActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_location);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

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
            // Start filter fragment
            FragmentManager fm = getSupportFragmentManager();
            FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance();
            filterDialogFragment.show(fm, "fragment_filters");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
