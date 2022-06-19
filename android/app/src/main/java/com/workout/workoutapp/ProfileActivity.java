package com.workout.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileActivity extends AppCompatActivity {

    TextView profileUserID, profileUsername;
    Context mContext;
    UserService userService;
    ListView lvDailyWorkout;
    Button profileLogout, profileDelete, ProfileAddWorkout, profileBack;
    private static ArrayAdapter<Workout> lvDailyWorkoutAdapter;
    private static ArrayList<Workout> dailyWorkout = new ArrayList<>();
    public static Workout selectedWorkoutList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_profile);

        lvDailyWorkout = (ListView) findViewById(R.id.lvDailyWorkout);
        profileUserID = (TextView) findViewById(R.id.profileUserID);
        profileUsername = (TextView) findViewById(R.id.profileUsername);
        profileLogout = (Button) findViewById(R.id.profileLogout);
        profileDelete = (Button) findViewById(R.id.profileDelete);
        ProfileAddWorkout = (Button) findViewById(R.id.profileAddWorkout);
        profileBack = (Button) findViewById(R.id.profileBack);

        getUser();
        getListWorkout();

        lvDailyWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedWorkoutList = dailyWorkout.get(position);
                Intent intent = new Intent(mContext, WorkoutDetailActivity.class);
                startActivity(intent);
            }
        });

        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StartActivity.class);
                startActivity(intent);
            }
        });

        profileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Delete My Account");
                builder.setMessage("Are you sure you want to delete your account?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteUser();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ProfileAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddWorkoutActivity.class);
                startActivity(intent);
            }
        });

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void getUser(){
        userService.getUserRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONArray result = jsonRESULTS.getJSONArray("workout");
                        profileUsername.setText(result.getJSONObject(0).getString("username"));
                        profileUserID.setText(result.getJSONObject(0).getString("userid"));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Gagal melakukan fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getListWorkout(){
        Gson gson = new Gson();
        userService.getWorkoutRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
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
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }

    void deleteUser(){
        userService.deleteUserRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, StartActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
            }
        });
    }
}