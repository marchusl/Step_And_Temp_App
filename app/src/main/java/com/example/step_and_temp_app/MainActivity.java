package com.example.step_and_temp_app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView stepCounterText;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorPresent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
    }
}