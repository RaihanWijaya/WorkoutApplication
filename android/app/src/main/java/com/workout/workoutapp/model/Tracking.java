package com.workout.workoutapp.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

// Class: Tracking
public class Tracking {
    private int trackingid;
    private Date date;
    private int weight;
    private int height;
    private Float bmi;
    private int userid;
    private int progress;
    public static final SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy/MM/dd");

    // Constructor: Tracking
    public Tracking(int trackingid, Date date, int weight, int height, Float bmi, int userid, int progress) {
        this.trackingid = trackingid;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.userid = userid;
        this.progress = progress;
    }

    public Tracking(int trackingid) {
        this.trackingid = trackingid;
    }

    // Get function
    public String getStringTrackingID(){
        return String.valueOf(trackingid);
    }

    public int getTrackingID() {
        return trackingid;
    }

    public String getTrackingDateTaken() {
        return (dateFormatOutput.format(date));
    }

    public String getTrackingWeight() {
        return String.valueOf(weight);
    }

    public int getHeight() {
        return height;
    }

    public String getTrackingHeight() {
        return String.valueOf(height);
    }

    public String getTrackingBmi() {
        return String.valueOf(bmi);
    }

    public String getTrackingProgress() {
        return String.valueOf(progress);
    }

    public int getUserid() {
        return userid;
    }

    // toString function for listview
    @NonNull
    @Override
    public String toString() {

        return "Date: "+(dateFormatOutput.format(date))+"\n"+"Weight: "+weight+"\n"+"Height: "+height+"\n"+"BMI: " + bmi;
    }
}