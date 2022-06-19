package com.workout.workoutapp.request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {
    //Login
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);
    @DELETE("deleteUser")
    Call<ResponseBody> deleteUserRequest();

    //Register
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("username") String username,
                                       @Field("password") String password);

    //Workout
    @GET("workout")
    Call<ResponseBody> getWorkoutRequest();
    @FormUrlEncoded
    @POST("deleteWorkout")
    Call<ResponseBody> deleteWorkoutRequest(@Field("workoutid") String workoutid);
    @FormUrlEncoded
    @POST("workout")
    Call<ResponseBody> addWorkoutRequest(@Field("name") String name,
                                         @Field("bodypart") String bodypart,
                                         @Field("reps") String reps,
                                         @Field("sets") String sets);
    @FormUrlEncoded
    @PUT("workout")
    Call<ResponseBody> updateWorkoutRequest(@Field("workoutid") String workoutid,
                                            @Field("name") String name,
                                            @Field("bodypart") String bodypart,
                                            @Field("reps") String reps,
                                            @Field("sets") String sets);

    //Tracking
    @GET("tracking")
    Call<ResponseBody> getTrackingRequest();
    @FormUrlEncoded
    @POST("deleteTracking")
    Call<ResponseBody> deleteTrackingRequest(@Field("trackingid") int trackingid);
    @FormUrlEncoded
    @POST("tracking")
    Call<ResponseBody> addTrackingRequest(@Field("weight") String weight,
                                          @Field("height") String height,
                                          @Field("progress") String progress);

    //Misc
    @GET("getUser")
    Call<ResponseBody> getUserRequest();
}