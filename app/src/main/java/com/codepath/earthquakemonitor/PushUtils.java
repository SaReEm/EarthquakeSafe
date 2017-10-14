package com.codepath.earthquakemonitor;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseCloud;
import com.parse.ParseInstallation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by emilie on 10/14/17.
 */

public class PushUtils {

    // Send location location and userID and execute Parse cloud code.
    public static JSONObject getPayloadFromLatLng(LatLng position) throws JSONException {

            JSONObject location = new JSONObject();
            location.put("lat", position.latitude);
            location.put("long", position.longitude);

            JSONObject payload = new JSONObject();
            payload.put("location", location);

            // Disambiguate this marker ID as created by the installation and the unique marker ID given to it.
            String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();

//        String markerId = installationId + "_" + marker.getId();
//        payload.put("markerId", markerId);

            // Use so we can discard push notifications sent to ourselves.
            payload.put("installationId", installationId);
            return payload;
        }

    public static void sendPushNotifWithNewPos(LatLng position, String channelName) {
        try {
            JSONObject payload = getPayloadFromLatLng(position);
            HashMap<String, String> data = new HashMap<>();
            data.put("customData", payload.toString());
            data.put("channel", channelName);

            // The code that processes this function is listed at:
            // https://github.com/rogerhu/parse-server-push-marker-example/blob/master/cloud/main.js
            ParseCloud.callFunctionInBackground("newPosition", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Send location location, a radius and userID and execute Parse cloud code.
    public static JSONObject getPayloadFromLatLngRadius(LatLng position, int radius) throws JSONException {

        JSONObject location = new JSONObject();
        location.put("lat", position.latitude);
        location.put("long", position.longitude);
        location.put("radius", radius);

        JSONObject payload = new JSONObject();
        payload.put("locationRadius", location);

        // Disambiguate this marker ID as created by the installation and the unique marker ID given to it.
        String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();

//        String markerId = installationId + "_" + marker.getId();
//        payload.put("markerId", markerId);

        // Use so we can discard push notifications sent to ourselves.
        payload.put("installationId", installationId);
        return payload;
    }

    public static void sendPushNotifWithNewPosAndRadius(LatLng position, int radius, String channelName) {
        try {
            JSONObject payload = getPayloadFromLatLngRadius(position,radius);
            HashMap<String, String> data = new HashMap<>();
            data.put("customData", payload.toString());
            data.put("channel", channelName);

            // The code that processes this function is listed at:
            // https://github.com/rogerhu/parse-server-push-marker-example/blob/master/cloud/main.js
            ParseCloud.callFunctionInBackground("newPositionRadius", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Send location location, a radius and userID and execute Parse cloud code.
    public static JSONObject getPayloadFromSafeStatus(boolean safeStatus) throws JSONException {

        JSONObject payload = new JSONObject();
        payload.put("safeStatus", safeStatus);

        // Disambiguate this marker ID as created by the installation and the unique marker ID given to it.
        String installationId = ParseInstallation.getCurrentInstallation().getInstallationId();

//        String markerId = installationId + "_" + marker.getId();
//        payload.put("markerId", markerId);

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
            // https://github.com/rogerhu/parse-server-push-marker-example/blob/master/cloud/main.js
            ParseCloud.callFunctionInBackground("newSafeStatus", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
