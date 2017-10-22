package com.codepath.earthquakemonitor.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.earthquakemonitor.utils.ParseQueryClient;

public class MyFollowsFragment extends UserListFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void populateUsers() {
        users.clear();
        users.addAll(ParseQueryClient.getFollows());
        userAdapter.notifyDataSetChanged();
    }
}
