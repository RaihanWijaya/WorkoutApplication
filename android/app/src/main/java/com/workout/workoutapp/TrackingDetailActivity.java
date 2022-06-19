package com.workout.workoutapp;

import androidx.annotation.MainThread;
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

public class TrackingDetailActivity extends AppCompatActivity {

    TextView trackingDetailID, trackingDetailDateTaken, trackingDetailWeight, trackingDetailHeight, trackingDetailBmi, trackingDetailProgress;
    Button trackingDetailBack, trackingDetailDelete;
    Context mContext;
    UserService userService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_tracking_detail);

        trackingDetailID = (TextView) findViewById(R.id.trackingDetailID);
        trackingDetailDateTaken = (TextView) findViewById(R.id.trackingDetailDateTaken);
        trackingDetailWeight = (TextView) findViewById(R.id.trackingDetailWeight);
        trackingDetailHeight = (TextView) findViewById(R.id.trackingDetailHeight);
        trackingDetailBmi = (TextView) findViewById(R.id.trackingDetailBmi);
        trackingDetailProgress = (TextView) findViewById(R.id.trackingDetailProgress);
        trackingDetailBack = (Button) findViewById(R.id.trackingDetailBack);
        trackingDetailDelete = (Button) findViewById(R.id.trackingDetailDelete);

        trackingDetailID.setText(TrackingActivity.selectedTrackingList.getStringTrackingID());
        trackingDetailDateTaken.setText(TrackingActivity.selectedTrackingList.getTrackingDateTaken());
        trackingDetailWeight.setText(TrackingActivity.selectedTrackingList.getTrackingWeight());
        trackingDetailHeight.setText(TrackingActivity.selectedTrackingList.getTrackingHeight());
        trackingDetailBmi.setText(TrackingActivity.selectedTrackingList.getTrackingBmi());
        trackingDetailProgress.setText(TrackingActivity.selectedTrackingList.getTrackingProgress());

        trackingDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackingActivity.class);
                startActivity(intent);
            }
        });

        trackingDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setTitle("Delete Tracking");
                builder.setMessage("Are you sure you want to delete this tracking?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTracking();
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
    }

    void deleteTracking(){
        userService.deleteTrackingRequest(TrackingActivity.selectedTrackingList.getTrackingID()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Tracking deleted successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, TrackingActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(mContext, "Tracking deletion failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Tracking deletion failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}