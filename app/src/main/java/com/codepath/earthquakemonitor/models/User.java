package com.codepath.earthquakemonitor.models;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {
    public User() {
        super();
    }
    public User(ParseUser parseUser) {
        super();
        this.setUsername(parseUser.getUsername());
        this.setEmail(parseUser.getEmail());
    }

    public double getLatitude() {
        return getDouble("latitude");
    }

    public void setLatitude(double latitude) {
        put("latitude", latitude);
    }

    public double getLongitude() {
        return getDouble("longitude");
    }

    public void setLongitude(double longitude) {
        put("longitude", longitude);
    }

    public ParseQuery<ParseObject> getFriends() {
        return this.getRelation("friends").getQuery();
    }

    public void addFriend(User friend) {
        this.getRelation("friends").add(friend);
        this.saveInBackground();
    }
}
