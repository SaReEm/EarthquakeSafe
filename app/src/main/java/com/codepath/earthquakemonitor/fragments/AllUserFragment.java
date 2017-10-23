package com.codepath.earthquakemonitor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.earthquakemonitor.Adapters.FollowAdapter;
import com.codepath.earthquakemonitor.Adapters.UserAdapter;
import com.codepath.earthquakemonitor.FriendsListActivity;
import com.codepath.earthquakemonitor.R;
import com.codepath.earthquakemonitor.utils.ParseQueryClient;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AllUserFragment extends Fragment {
    UserAdapter userAdapter;
    public List<ParseUser> users;
    RecyclerView rvUsers;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_users_list, container, false);
        users = new ArrayList<>();
        rvUsers = v.findViewById(R.id.rvFriendsList);
        userAdapter = new UserAdapter(users, (FriendsListActivity) getActivity());
        rvUsers.setAdapter(userAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUsers.setLayoutManager(linearLayoutManager);
        try {
            populateUsers();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return v;
    }

    public void populateUsers() throws ParseException {
        users.clear();
        users.addAll(ParseQueryClient.getAllUsers());
        userAdapter.notifyDataSetChanged();
    }

    public void onFollow(int position) {
        ParseUser user = users.get(position);
        try {
            ParseQueryClient.follow(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        users.remove(position);
        userAdapter.notifyDataSetChanged();
    }

    public void onUnFollow(ParseUser user) {
        users.add(user);
        userAdapter.notifyDataSetChanged();
    }
}
