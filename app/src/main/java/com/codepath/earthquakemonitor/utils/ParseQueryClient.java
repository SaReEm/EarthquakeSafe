package com.codepath.earthquakemonitor.utils;

import com.codepath.earthquakemonitor.models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ParseQueryClient {
    public static List<ParseUser> getAllUsers() throws ParseException{
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

    public static void follow(ParseUser newFollow) throws ParseException{
        List<ParseUser> follows = getFollows();
        if (!follows.contains(newFollow))
            follows.add(newFollow);
        ParseUser.getCurrentUser().put("follows", follows);
        ParseUser.getCurrentUser().saveInBackground();
    }

    public static void unfollow(ParseUser unFollow) throws ParseException{
        List<ParseUser> follows = getFollows();
        follows.remove(unFollow);
        ParseUser.getCurrentUser().put("follows", follows);
        ParseUser.getCurrentUser().saveInBackground();
    }

    public static List<ParseUser> getFollows() throws ParseException {
        List<ParseUser> follows = ParseUser.getCurrentUser().getList("follows");
        List<ParseUser> res = new ArrayList<>();
        if (follows == null) {
            follows = new ArrayList<>();
        }
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        for (int i = 0; i < follows.size(); i++) {
            String id = follows.get(i).getObjectId();
            res.add(query.get(id));
        }
        return res;
    }

}
