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

public class TrackingAddActivity extends AppCompatActivity {

    EditText addTrackingWeight, addTrackingHeight, addTrackingProgress;
    Button addTrackingBack, addTrackingAdd;
    Context mContext;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_tracking_add);

        addTrackingWeight = findViewById(R.id.addTrackingWeight);
        addTrackingHeight = findViewById(R.id.addTrackingHeight);
        addTrackingBack = findViewById(R.id.addTrackingBack);
        addTrackingAdd = findViewById(R.id.addTrackingAdd);
        addTrackingProgress = findViewById(R.id.addTrackingProgress);

        addTrackingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackingActivity.class);
                startActivity(intent);
            }
        });

        addTrackingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = addTrackingWeight.getText().toString();
                String height = addTrackingHeight.getText().toString();
                String progress = addTrackingProgress.getText().toString();

                if (weight.isEmpty() || height.isEmpty()) {
                    Toast.makeText(TrackingAddActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addTracking(weight, height, progress);
                }
            }
        });
    }

    void addTracking(String weight, String height, String progress) {
        Call<ResponseBody> call = userService.addTrackingRequest(weight, height, progress);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("message").equals("Tracking added")) {
                            Toast.makeText(TrackingAddActivity.this, "Tracking added successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, TrackingActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TrackingAddActivity.this, "Tracking fail to add", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(TrackingAddActivity.this, "Tracking not added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TrackingAddActivity.this, "Tracking not added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}