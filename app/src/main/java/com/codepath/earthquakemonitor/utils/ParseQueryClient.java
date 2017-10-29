package com.codepath.earthquakemonitor.utils;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ParseQueryClient {
    public static List<ParseUser> getAllUsers() throws ParseException{
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        //query.whereNotContainedIn("_id", ParseUser.getCurrentUser().getList("follows"));
        List<ParseUser> follows = ParseUser.getCurrentUser().getList("follows");
        if (follows == null) follows = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < follows.size(); i++) {
            set.add(follows.get(i).getObjectId());
        }
        try {
            List<ParseUser> res =  query.find();
            for (int i = 0; i < res.size(); i++) {
                if (set.contains(res.get(i).getObjectId())) {
                    res.remove(i);
                }
            }
            return res;
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
        for (int i = 0; i < follows.size(); i ++) {
            if (follows.get(i).getUsername().equals(unFollow.getUsername())) {
                follows.remove(i);
                break;
            }
        }
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

    public static void changeSafeStatus(boolean safeStatus){
        ParseUser user = ParseUser.getCurrentUser();
        String safeStatusStr;
        if(safeStatus){
            safeStatusStr = "safe";
        }
        else
            safeStatusStr = "NC";

        user.put("safeStatusString", safeStatusStr);
        user.saveInBackground();
    }

}
