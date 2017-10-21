package com.codepath.earthquakemonitor.models;

import java.util.Calendar;

/**
 * Created by emilie on 10/9/17.
 */

public class Filters {

    private static Filters filter;

    public static Filters getInstance(){

        if(filter == null){
            filter = new Filters();
        }
        return filter;
    }

    private boolean useMinMagnitude;
    private boolean useStartTime;
    private boolean useDistance;
    private boolean useDepth;
    private boolean usePosition;

    private int minMagnitude;
    private String startTime;
    private int distance;
    private int maxDepth;
    private Double latitude;
    private Double longitude;

    public final Double DEFAULT_LATITUDE = 37.395605;
    public final Double DEFAULT_LONGITUDE = -122.077655;
    public final int DEFAULT_MINMAG = 3;
    public final int DEFAULT_MINDISTANCE = 600;
    public final int DEFAULT_MAXDEPTH = 1000;

    private Filters(){
        latitude = DEFAULT_LATITUDE;
        longitude = DEFAULT_LONGITUDE;
        minMagnitude = DEFAULT_MINMAG;
        distance = DEFAULT_MINDISTANCE;
        startTime = getDefaultStartTime();
        maxDepth = DEFAULT_MAXDEPTH;

        useMinMagnitude = true;
        useStartTime = true;
        useDistance = true;
        usePosition = true;
        useDepth = false;

    }

    private void Filters(int minMagnitude, String startTime, int distance){
        this.minMagnitude = minMagnitude;
        this.startTime = startTime;
        this.distance = distance;

        this.useMinMagnitude = true;
        this.useStartTime = true;
        this.useDistance = true;
    }

    private void Filters(int minMagnitude, String startTime, int distance, int depth){
        this.minMagnitude = minMagnitude;
        this.startTime = startTime;
        this.distance = distance;
        this.maxDepth = depth;
    }

    //Default Start time is currently 2 months ago
    private String getDefaultStartTime(){
        String startTime;
        final Calendar c = Calendar.getInstance();

        c.add(Calendar.MONTH, -2);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        startTime = year + "-" + month + "-" + day;
        return startTime;
    }

    private void initFilters(){
        minMagnitude = DEFAULT_MINMAG;
        distance = DEFAULT_MINDISTANCE;
        //todo have a time like in the last 2 months instead of hard coded
        startTime = "2017-05-01";

        useMinMagnitude = true;
        useStartTime = true;
        useDistance = true;
        useDepth = false;
    }

    public void resetFilters(){
        useMinMagnitude = false;
        useStartTime = false;
        useDistance = false;
        useDepth = false;
        usePosition = false;
    }


    public boolean isUsePosition() {
        return usePosition;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public void setPosition(Double latitude,Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Double getLatitude(){
        return this.latitude;
    }
    public Double getLongitude(){
        return this.longitude;
    }

    public boolean isUseMinMagnitude() {
        return useMinMagnitude;
    }

    public void setUseMinMagnitude(boolean useMinMagnitude) {
        this.useMinMagnitude = useMinMagnitude;
    }

    public boolean isUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(boolean useStartTime) {
        this.useStartTime = useStartTime;
    }


    public boolean isUseDistance() {
        return useDistance;
    }

    public void setUseDistance(boolean useDistance) {
        this.useDistance = useDistance;

    }

    public boolean isUseDepth() {
        return useDepth;
    }

    public void setUseDepth(boolean useDepth) {
        this.useDepth = useDepth;
    }

    public int getMinMagnitude() {
        return minMagnitude;
    }

    public void setMinMagnitude(int minMagnitude) {
        this.minMagnitude = minMagnitude;
        this.useMinMagnitude = true;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        this.useStartTime = true;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        this.useDistance = true;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
        this.useDepth = true;

    }
}
