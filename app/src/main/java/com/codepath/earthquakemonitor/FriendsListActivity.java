package com.codepath.earthquakemonitor;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.codepath.earthquakemonitor.Adapters.FollowAdapter;
import com.codepath.earthquakemonitor.Adapters.UserAdapter;
import com.codepath.earthquakemonitor.Adapters.UserPagerAdapter;
import com.codepath.earthquakemonitor.fragments.AllUserFragment;
import com.codepath.earthquakemonitor.fragments.BaseFragment;
import com.codepath.earthquakemonitor.fragments.MyFollowsFragment;
import com.codepath.earthquakemonitor.utils.ParseQueryClient;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity  implements FollowAdapter.FollowAdapterListener, UserAdapter.UserAdapterListener
{
    private UserPagerAdapter userPagerAdapter;

    private FloatingActionButton btnInvite;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(userPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(final int position, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int position) {
                BaseFragment fragment = (BaseFragment) userPagerAdapter.getRegisteredFragment(position);
                if (fragment != null) {
                    try {
                        fragment.populateUsers();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(final int position) {
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Send out the app's github url to invite friends
        btnInvite = (FloatingActionButton) findViewById(R.id.btnInvite);
        btnInvite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/SaReEm/EarthquakeSafe");
                startActivity(Intent.createChooser(shareIntent, "Share link using"));
            }
        });

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

    @Override
    public void onUnFollow(View view, int position) {
        MyFollowsFragment myFollowsFragment = (MyFollowsFragment) userPagerAdapter.getRegisteredFragment(0);
        AllUserFragment allUserFragment = (AllUserFragment) userPagerAdapter.getRegisteredFragment(1);
        allUserFragment.onUnFollow(myFollowsFragment.users.get(position));
        myFollowsFragment.onUnFollow(position);

    }

    @Override
    public void onFollow(View view, int position) {
        MyFollowsFragment myFollowsFragment = (MyFollowsFragment) userPagerAdapter.getRegisteredFragment(0);
        AllUserFragment allUserFragment = (AllUserFragment) userPagerAdapter.getRegisteredFragment(1);
        myFollowsFragment.onFollow(allUserFragment.users.get(position));
        allUserFragment.onFollow(position);

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
