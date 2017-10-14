package com.codepath.earthquakemonitor.models;

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

    private int minMagnitude;
    private String startTime;
    private int distance;
    private int maxDepth; //max maxDepth

    private Filters(){

        minMagnitude = 2;
        distance = 60;
        //todo have a time like in the last 2 months instead of hard coded
        startTime = "2017-05-01";

        useMinMagnitude = true;
        useStartTime = true;
        useDistance = true;
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

    private void initFilters(){
        minMagnitude = 2;
        distance = 60;
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
