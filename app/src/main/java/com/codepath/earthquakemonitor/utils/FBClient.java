package com.codepath.earthquakemonitor.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class FBClient {
    public static final String REST_URL = "https://graph.facebook.com/v2.10";
    public AsyncHttpClient client;
    public FBClient() {
        this.client = new AsyncHttpClient();
    }

    public void getFBFriends(AsyncHttpResponseHandler handler) {
        String apiUrl = REST_URL + "/me/friends";
        client.get(apiUrl, handler);
    }
}
