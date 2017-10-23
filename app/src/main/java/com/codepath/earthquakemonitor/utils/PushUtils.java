package com.codepath.earthquakemonitor.utils;

import com.parse.ParseCloud;
import com.parse.ParseInstallation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by emilie on 10/14/17.
 */

public class PushUtils {

    // Send location location, a radius and userID and execute Parse cloud code.
    public static JSONObject getPayloadFromSafeStatus(boolean safeStatus) throws JSONException {

        JSONObject payload = new JSONObject();
        payload.put("safeStatus", safeStatus);

        // Disambiguate this safeStatus ID as created by the installation and the unique safeStatus ID given to it.
        String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();

        // Use so we can discard push notifications sent to ourselves.
        payload.put("installationId", installationId);
        return payload;
    }

    public static void sendPushNotifSafeStatus(boolean safeStatus, String channelName) {
        try {
            JSONObject payload = getPayloadFromSafeStatus(safeStatus);
            HashMap<String, String> data = new HashMap<>();
            data.put("customData", payload.toString());
            data.put("channel", channelName);

            // The code that processes this function is listed at:
            //https://github.com/SaReEm/parse-server-example/blob/master/cloud/main.js
            // reference here :
            // https://github.com/rogerhu/parse-server-push-marker-example/blob/master/cloud/main.js
            ParseCloud.callFunctionInBackground("newSafeStatus", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
