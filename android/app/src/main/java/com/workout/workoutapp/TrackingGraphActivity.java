package com.workout.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class TrackingGraphActivity extends AppCompatActivity {

    ArrayList<BarEntry> barArraylist;
    Button trackingGraphBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_graph);
        BarChart barChart = findViewById(R.id.barchart);

        trackingGraphBack = findViewById(R.id.trackingGraphBack);

        getData();

        BarDataSet barDataSet = new BarDataSet(barArraylist,"Progress Graph");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

        trackingGraphBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackingGraphActivity.this, TrackingActivity.class);
                startActivity(intent);
            }
        });
    }

    void getData()
    {
        barArraylist = new ArrayList<BarEntry>();
        for(int i = 0; i < TrackingActivity.listTracking.size(); i++) {
            barArraylist.add(new BarEntry(i, Float.parseFloat(TrackingActivity.listTracking.get(i).getTrackingProgress())));
        }
    }
}