package com.codepath.earthquakemonitor.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.earthquakemonitor.fragments.AllUserFragment;
import com.codepath.earthquakemonitor.fragments.MyFollowsFragment;

public class UserPagerAdapter extends SmartFragmentStatePagerAdapter {
    private String[] tabTitles = new String[] {"Other Users", "My Follows"};
    private Context context;

    public UserPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {return 2;}

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AllUserFragment();
        } else if (position == 1) {
            return new MyFollowsFragment();
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
}
