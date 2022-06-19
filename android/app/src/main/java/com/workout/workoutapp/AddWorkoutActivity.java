package com.workout.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.workout.workoutapp.request.ApiUtils;
import com.workout.workoutapp.request.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkoutActivity extends AppCompatActivity {

    EditText addWorkoutName, addWorkoutBodypart, addWorkoutReps, addWorkoutSets;
    Button addWorkoutBack, addWorkoutAdd;
    Context mContext;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_add_workout);

        addWorkoutName = (EditText) findViewById(R.id.addWorkoutName);
        addWorkoutBodypart = (EditText) findViewById(R.id.addWorkoutBodypart);
        addWorkoutReps = (EditText) findViewById(R.id.addWorkoutReps);
        addWorkoutSets = (EditText) findViewById(R.id.addWorkoutSets);
        addWorkoutBack = (Button) findViewById(R.id.addWorkoutBack);
        addWorkoutAdd = (Button) findViewById(R.id.addWorkoutAdd);

        addWorkoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        addWorkoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addWorkoutName.getText().toString();
                String bodypart = addWorkoutBodypart.getText().toString();
                String reps = addWorkoutReps.getText().toString();
                String sets = addWorkoutSets.getText().toString();

                if (name.isEmpty() || bodypart.isEmpty() || reps.isEmpty() || sets.isEmpty()) {
                    Toast.makeText(AddWorkoutActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addWorkout(name, bodypart, reps, sets);
                }
            }
        });
    }

    void addWorkout(String name, String bodypart, String reps, String sets) {
        Call<ResponseBody> call = userService.addWorkoutRequest(name, bodypart, reps, sets);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("message").equals("Workout added")) {
                            Toast.makeText(AddWorkoutActivity.this, "Workout added successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddWorkoutActivity.this, "Workout fail to add", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddWorkoutActivity.this, "Workout not added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddWorkoutActivity.this, "Workout not added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}