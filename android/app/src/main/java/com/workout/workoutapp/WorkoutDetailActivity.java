package com.workout.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.workout.workoutapp.request.ApiUtils;
import com.workout.workoutapp.request.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class: WorkoutDetailActivity
 * Description: This class is used to show the details of a workout from the database.
 */

public class WorkoutDetailActivity extends AppCompatActivity {

    TextView workoutID, workoutName, workoutBodypart, workoutReps, workoutSets;
    Button workoutEdit, workoutDelete, workoutBack;
    Context mContext;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_workout_detail);

        workoutID = (TextView) findViewById(R.id.workoutID);
        workoutName = (TextView) findViewById(R.id.workoutName);
        workoutBodypart = (TextView) findViewById(R.id.workoutBodypart);
        workoutReps = (TextView) findViewById(R.id.workoutReps);
        workoutSets = (TextView) findViewById(R.id.workoutSets);
        workoutEdit = (Button) findViewById(R.id.workoutEdit);
        workoutDelete = (Button) findViewById(R.id.workoutDelete);
        workoutBack = (Button) findViewById(R.id.workoutBack);

        workoutID.setText(ProfileActivity.selectedWorkoutList.getWorkoutID());
        workoutName.setText(ProfileActivity.selectedWorkoutList.getWorkoutName());
        workoutBodypart.setText(ProfileActivity.selectedWorkoutList.getWorkoutBodypart());
        workoutReps.setText(ProfileActivity.selectedWorkoutList.getWorkoutReps());
        workoutSets.setText(ProfileActivity.selectedWorkoutList.getWorkoutSets());

        workoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WorkoutEditActivity.class);
                startActivity(intent);
            }
        });

        workoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Delete Workout");
                builder.setMessage("Are you sure you want to delete this workout?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteWorkout();
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

        workoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteWorkout(){
        userService.deleteWorkoutRequest(ProfileActivity.selectedWorkoutList.getWorkoutID()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Workout deleted successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(mContext, "Workout deletion failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Workout deletion failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}