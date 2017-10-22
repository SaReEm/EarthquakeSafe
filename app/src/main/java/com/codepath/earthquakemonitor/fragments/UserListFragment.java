package com.codepath.earthquakemonitor.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.earthquakemonitor.Adapters.UserAdapter;
import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.utils.ParseQueryClient;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {
    List<ParseUser> users;
    RecyclerView rvUsers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_users_list, container, false);
        users = new ArrayList<>();
        rvUsers = v.findViewById(R.id.rvFriendsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUsers.setLayoutManager(linearLayoutManager);
        populateUsers();
        return v;
    }

    public void populateUsers() {}

}
