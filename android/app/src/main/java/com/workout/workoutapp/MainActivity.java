package com.workout.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.workout.workoutapp.model.Workout;
import com.workout.workoutapp.request.ApiUtils;
import com.workout.workoutapp.request.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button mainBack, mainProfile, mainTracking, mainFinish;
    ListView lvDailyWorkout;
    Context mContext;
    UserService userService;
    private static ArrayAdapter<Workout> lvDailyWorkoutAdapter;
    private static ArrayList<Workout> dailyWorkout = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_main);

        lvDailyWorkout = (ListView) findViewById(R.id.lvDailyWorkout);
        mainBack = (Button) findViewById(R.id.mainBack);
        mainProfile = (Button) findViewById(R.id.mainProfile);
        mainTracking = (Button) findViewById(R.id.mainTracking);
        mainFinish = (Button) findViewById(R.id.mainFinish);

        getListWorkout();

        mainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        mainProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mainTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackingActivity.class);
                startActivity(intent);
            }
        });

        mainFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackingAddActivity.class);
                startActivity(intent);
            }
        });
    }

    void getListWorkout(){
        Gson gson = new Gson();
        userService.getWorkoutRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonRESULTS.getJSONArray("workout");
                        if (jsonArray.length() >= 0){
                            dailyWorkout = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Workout>>() {}.getType());
                            lvDailyWorkoutAdapter = new ArrayAdapter<Workout>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, dailyWorkout);
                            lvDailyWorkout.setAdapter(lvDailyWorkoutAdapter);
                        } else {
                            String error_message = jsonArray.getString(Integer.parseInt("User not logged in"));
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Sudah Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}