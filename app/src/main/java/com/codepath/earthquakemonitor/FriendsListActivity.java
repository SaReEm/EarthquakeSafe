package com.codepath.earthquakemonitor;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.codepath.earthquakemonitor.Adapters.UserAdapter;
import com.codepath.earthquakemonitor.Adapters.UserPagerAdapter;
import com.codepath.earthquakemonitor.utils.ParseQueryClient;

import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity
{
    private UserPagerAdapter userPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(userPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                // perform query here
                //Toast.makeText(getApplicationContext(),"Perform search", Toast.LENGTH_LONG).show();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /*public void showFriendsLocation(View view)
    {
        TextView friendName = (TextView) findViewById(R.id.tvDummyFriend);
        friendName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(FriendsListActivity.this, FriendsLocationActivity.class);
                startActivity(i);
            }
        });
    }*/

}
