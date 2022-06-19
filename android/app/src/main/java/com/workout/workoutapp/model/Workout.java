package com.workout.workoutapp.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Workout {
    private int workoutid;
    private String name;
    private String bodypart;
    private int reps;
    private int sets;
    private int userid;

    public Workout(int workoutid, String name, String bodypart, int reps, int sets, int userid) {
        this.workoutid = workoutid;
        this.name = name;
        this.bodypart = bodypart;
        this.reps = reps;
        this.sets = sets;
        this.userid = userid;
    }

    public Workout(String name, String bodypart, int reps, int sets, int userid) {
        this.name = name;
        this.bodypart = bodypart;
        this.reps = reps;
        this.sets = sets;
        this.userid = userid;
    }

    public Workout(int workoutid) {
        this.workoutid = workoutid;
    }

    public int getWorkoutid() {
        return workoutid;
    }

    public String getName() {
        return name;
    }

    public String getBodypart() {
        return bodypart;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getUserid() {
        return userid;
    }

    @NonNull
    @Override
    public String toString() {
        return name+"\n"+bodypart;
    }

    public JSONObject getJSONObject() throws JSONException {
        return new JSONObject()
                .put("workoutid", workoutid)
                .put("name", name)
                .put("bodypart", bodypart)
                .put("reps", reps)
                .put("sets", sets)
                .put("userid", userid);
    }

    public String getWorkoutID() {
        return String.valueOf(workoutid);
    }

    public String getWorkoutName() {
        return name;
    }

    public String getWorkoutBodypart() {
        return bodypart;
    }

    public String getWorkoutReps() {
        return String.valueOf(reps);
    }

    public String getWorkoutSets() {
        return String.valueOf(sets);
    }
}