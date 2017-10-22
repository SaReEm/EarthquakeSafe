package com.codepath.earthquakemonitor.utils;

import com.codepath.earthquakemonitor.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ParseQueryClient {
    public static List<ParseUser> getAllUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereNotContainedIn("follows", getFollows());
        try {
            return query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void follow(ParseUser newFollow) {
        List<ParseUser> follows = getFollows();
        if (!follows.contains(newFollow))
            follows.add(newFollow);
        ParseUser.getCurrentUser().put("follows", follows);
        ParseUser.getCurrentUser().saveInBackground();
    }

    public static List<ParseUser> getFollows() {
        List<ParseUser> follows = ParseUser.getCurrentUser().getList("follows");
        if (follows == null) {
            follows = new ArrayList<>();
        }
        return follows;
    }
}
