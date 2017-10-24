package com.codepath.earthquakemonitor.models;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by emilie on 10/9/17.
 */
@Parcel
public class Earthquake
{

    //todo clean up the unnecessary param
    public Double mag;
    public String place;
    public Double time;
    private String url;

    public Double longitude;
    public Double latitude;
    public Double depth;

    //For the Parcelable library
    public Earthquake(){
    }



    public Double getMag() {
        return mag;
    }
    public void setMag(Double mag) {
        this.mag = mag;
    }
    public Earthquake withMag(Double mag) {
        this.mag = mag;
        return this;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public Earthquake withPlace(String place) {
        this.place = place;
        return this;
    }
    public Double getTime() {
        return time;
    }
    public void setTime(Double time) {
        this.time = time;
    }
    public Earthquake withTime(Double time) {
        this.time = time;
        return this;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDepth() {
        return depth;
    }
    public void setDepth(Double depth) {
        this.depth = depth;
    }


    public static Earthquake fromJson(JSONObject jsonObject)throws JSONException{
        Earthquake earthquake = new Earthquake();

        JSONObject properties = jsonObject.getJSONObject("properties");

        earthquake.setMag(properties.getDouble("mag"));
        earthquake.setPlace(properties.getString("place"));

        earthquake.setTime(properties.getDouble("time"));

        earthquake.setUrl(properties.getString("url"));

        JSONArray coordinates = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");

        earthquake.setLongitude(coordinates.getDouble(0));
        earthquake.setLatitude(coordinates.getDouble(1));
        earthquake.setDepth(coordinates.getDouble(2));

        return earthquake;
    }

    //Those commented param might be interesting
//    private Object alert;
//    private String status;
//    private Integer tsunami;

//    public Object getAlert() {
//        return alert;
//    }
//    public void setAlert(Object alert) {
//        this.alert = alert;
//    }
//    public Earthquake withAlert(Object alert) {
//        this.alert = alert;
//        return this;
//    }
//    public String getStatus() {
//        return status;
//    }
//    public void setStatus(String status) {
//        this.status = status;
//    }
//    public Earthquake withStatus(String status) {
//        this.status = status;
//        return this;
//    }
//    public Integer getTsunami() {
//        return tsunami;
//    }
//    public void setTsunami(Integer tsunami) {
//        this.tsunami = tsunami;
//    }
//    public Earthquake withTsunami(Integer tsunami) {
//        this.tsunami = tsunami;
//        return this;
//    }


    ///// Here are param we could get for the earthquake but are they all interesting for our case?

//    private Integer updated;
//    private Integer tz;
//    private String detail;
//    private Object felt;
//    private Object cdi;
//    private Object mmi;
//    private Integer sig;
//    private String net;
//    private String code;
//    private String ids;
//    private String sources;
//    private String types;
//    private Object nst;
//    private Double dmin;
//    private Double rms;
//    private Integer gap;
//    private String magType;
//    private String type;
//    private String title;

//
//    public Integer getUpdated() {
//        return updated;
//    }
//    public void setUpdated(Integer updated) {
//        this.updated = updated;
//    }
//    public Earthquake withUpdated(Integer updated) {
//        this.updated = updated;
//        return this;
//    }
//    public Integer getTz() {
//        return tz;
//    }
//    public void setTz(Integer tz) {
//        this.tz = tz;
//    }
//    public Earthquake withTz(Integer tz) {
//        this.tz = tz;
//        return this;
//    }

//    public Earthquake withUrl(String url) {
//        this.url = url;
//        return this;
//    }
//    public String getDetail() {
//        return detail;
//    }
//    public void setDetail(String detail) {
//        this.detail = detail;
//    }
//    public Earthquake withDetail(String detail) {
//        this.detail = detail;
//        return this;
//    }
//    public Object getFelt() {
//        return felt;
//    }
//    public void setFelt(Object felt) {
//        this.felt = felt;
//    }
//    public Earthquake withFelt(Object felt) {
//        this.felt = felt;
//        return this;
//    }
//    public Object getCdi() {
//        return cdi;
//    }
//    public void setCdi(Object cdi) {
//        this.cdi = cdi;
//    }
//    public Earthquake withCdi(Object cdi) {
//        this.cdi = cdi;
//        return this;
//    }
//    public Object getMmi() {
//        return mmi;
//    }
//    public void setMmi(Object mmi) {
//        this.mmi = mmi;
//    }
//    public Earthquake withMmi(Object mmi) {
//        this.mmi = mmi;
//        return this;
//    }
//set
}
