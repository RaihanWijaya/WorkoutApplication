package com.workout.workoutapp.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

// Class: Workout
public class Workout {
    private int workoutid;
    private String name;
    private String bodypart;
    private int reps;
    private int sets;
    private int userid;

    // Constructor: Workout
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

    // Get function
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

    public String getName() {
        return name;
    }

    // toString function for listview
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
}
