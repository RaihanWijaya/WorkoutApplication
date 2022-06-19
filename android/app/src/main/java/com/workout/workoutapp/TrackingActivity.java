package com.workout.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.workout.workoutapp.model.Tracking;
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

public class TrackingActivity extends AppCompatActivity {

    Button trackingBack, trackingAdd, trackingGraph;
    ListView lvTracking;
    Context mContext;
    UserService userService;
    private static ArrayAdapter<Tracking> lvTrackingAdapter;
    public static ArrayList<Tracking> listTracking = new ArrayList<>();
    public static Tracking selectedTrackingList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        mContext = this;
        userService = ApiUtils.getUserService();
        setContentView(R.layout.activity_tracking);

        lvTracking = (ListView) findViewById(R.id.lvTracking);
        trackingBack = (Button) findViewById(R.id.trackingBack);
        trackingAdd = (Button) findViewById(R.id.trackingAdd);
        trackingGraph = (Button) findViewById(R.id.trackingGraph);

        getTracking();

        lvTracking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTrackingList = listTracking.get(position);
                Intent intent = new Intent(mContext, TrackingDetailActivity.class);
                startActivity(intent);
            }
        });

        trackingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        trackingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackingAddActivity.class);
                startActivity(intent);
            }
        });

        trackingGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackingGraphActivity.class);
                startActivity(intent);
            }
        });
    }

    void getTracking() {
        Gson gson = new Gson();
        userService.getTrackingRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("workout");
                        listTracking = gson.fromJson(jsonArray.toString(), new TypeToken<ArrayList<Tracking>>() {
                        }.getType());
                        lvTrackingAdapter = new ArrayAdapter<Tracking>(mContext, android.R.layout.simple_list_item_1, listTracking);
                        lvTracking.setAdapter(lvTrackingAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}