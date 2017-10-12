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

    private Double minMagnitude;
    private String startTime;
    private Double distance;
    private Double depth; //do we want min or max depth?

    private void Filters(){
        useMinMagnitude = false;
        useStartTime = false;
        useDistance = false;
        useDepth = false;
    }

    private void Filters(Double minMagnitude, String startTime, Double distance){
        this.minMagnitude = minMagnitude;
        this.startTime = startTime;
        this.distance = distance;

        this.useMinMagnitude = true;
        this.useStartTime = true;
        this.useDistance = true;
    }

    private void Filters(Double minMagnitude, String startTime, Double distance, Double depth){
        this.minMagnitude = minMagnitude;
        this.startTime = startTime;
        this.distance = distance;
        this.depth = depth;
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

    public Double getMinMagnitude() {
        return minMagnitude;
    }

    public void setMinMagnitude(Double minMagnitude) {
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
        this.useDistance = true;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
        this.useDepth = true;

    }
}
