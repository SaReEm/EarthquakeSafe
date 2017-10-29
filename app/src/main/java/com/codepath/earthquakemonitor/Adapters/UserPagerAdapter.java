package com.codepath.earthquakemonitor.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.earthquakemonitor.fragments.AllUserFragment;
import com.codepath.earthquakemonitor.fragments.MyFollowsFragment;
import com.codepath.earthquakemonitor.fragments.MyProfileFragment;

public class UserPagerAdapter extends SmartFragmentStatePagerAdapter {
    private String[] tabTitles = new String[] {"My Follows", "Other Users", "My Profile"};
    private Context context;

    public UserPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {return 3;}

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new AllUserFragment();
        } else if (position == 0) {
            return new MyFollowsFragment();
        } else if (position == 2) {
            return new MyProfileFragment();
        }else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
}
