package com.workout.workoutapp;

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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutEditActivity extends AppCompatActivity {

    EditText editWorkoutName, editWorkoutBodypart, editWorkoutReps, editWorkoutSets;
    Button editWorkoutBack, editWorkoutSave;
    Context mContext;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_workout_edit);

        editWorkoutName = (EditText) findViewById(R.id.editWorkoutName);
        editWorkoutBodypart = (EditText) findViewById(R.id.editWorkoutBodypart);
        editWorkoutReps = (EditText) findViewById(R.id.editWorkoutReps);
        editWorkoutSets = (EditText) findViewById(R.id.editWorkoutSets);
        editWorkoutBack = (Button) findViewById(R.id.editWorkoutBack);
        editWorkoutSave = (Button) findViewById(R.id.editWorkoutSave);

        editWorkoutName.setText(ProfileActivity.selectedWorkoutList.getWorkoutName());
        editWorkoutBodypart.setText(ProfileActivity.selectedWorkoutList.getWorkoutBodypart());
        editWorkoutReps.setText(ProfileActivity.selectedWorkoutList.getWorkoutReps());
        editWorkoutSets.setText(ProfileActivity.selectedWorkoutList.getWorkoutSets());

        editWorkoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                startActivity(intent);
            }
        });

        editWorkoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workoutName = editWorkoutName.getText().toString();
                String workoutBodypart = editWorkoutBodypart.getText().toString();
                String workoutReps = editWorkoutReps.getText().toString();
                String workoutSets = editWorkoutSets.getText().toString();

                if (workoutName.isEmpty() || workoutBodypart.isEmpty() || workoutReps.isEmpty() || workoutSets.isEmpty()) {
                    Toast.makeText(mContext, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    userService.updateWorkoutRequest(ProfileActivity.selectedWorkoutList.getWorkoutID(), workoutName, workoutBodypart, workoutReps, workoutSets).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mContext, "Workout updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "Error updating workout", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "Error updating workout", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}