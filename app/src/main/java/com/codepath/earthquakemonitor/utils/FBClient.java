package com.codepath.earthquakemonitor.utils;

import android.content.Context;

import com.facebook.AccessToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class FBClient {
    public static final String REST_URL = "https://graph.facebook.com/v2.10";
    public AsyncHttpClient client;
    public FBClient() {
        this.client = new AsyncHttpClient();
    }

    public void getFBFriends(String token, AsyncHttpResponseHandler handler) {
        String apiUrl = REST_URL + "/me/friends";
        RequestParams params = new RequestParams();
        params.put("access_token", token);
        client.get(apiUrl, params, handler);
    }

    public void getCurrentFBUserName(String token, AsyncHttpResponseHandler handler) {
        String apiUrl = REST_URL + "/me";
        RequestParams params = new RequestParams();
        params.put("access_token", token);
        client.get(apiUrl, params, handler);
    }
}
