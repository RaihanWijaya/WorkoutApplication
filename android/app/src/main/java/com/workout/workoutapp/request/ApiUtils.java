package com.workout.workoutapp.request;

public class ApiUtils {

    public static final String BASE_URL = "http://10.0.2.2:8610/workout/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}