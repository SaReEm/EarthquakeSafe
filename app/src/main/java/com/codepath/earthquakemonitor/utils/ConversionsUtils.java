package com.codepath.earthquakemonitor.utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by emilie on 10/14/17.
 */

public class ConversionsUtils {

    public static final String TAG ="ConversionsUtilsTAG";

    public static Double milesToKm(Double miles) {
        Double km = 0.621371 * miles;
        return km;
    }

    public static Double kmToMiles(Double km) {
        Double miles = 1.609344 * km;
        Log.d(TAG, "Converting " + km + "km to " + miles + " miles");
        return miles;
    }

    public static int kmToMiles(int km) {
        double kmDouble = (double)km;

        double miles = 0.621371 * kmDouble;
        int milesInt = (int) miles;
        Log.d(TAG, "Converting " + km + "km to " + milesInt + " miles");
        return milesInt;
    }

    public static Calendar epochToCalendar(Long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return c;
    }

    public static String calendarToString(Calendar c) {
        String dateString = c.get(Calendar.MONTH) + "/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR);
        return dateString;
    }

    public static String epochToString(Double timeDouble) {
        long time = (new Double(timeDouble)).longValue();

        String dateString = calendarToString(epochToCalendar(time));

        return dateString;
    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(Double timeDouble) {
        long dateMillis = (new Double(timeDouble)).longValue();

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        //todo get local dynamically
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        //Uncomment the following to have a date with following format : "2d"
//        Log.d("dateFormat", "before " + relativeDate);
//
//        int firstSpace = relativeDate.indexOf(" ");
//        if (firstSpace != -1) {
//            relativeDate = relativeDate.substring(0, firstSpace + 2);
//            Log.d("dateFormat", "after " + relativeDate);
//        }

        return relativeDate;
    }
}
