package com.example.step_and_temp_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity implements SensorEventListener{
    private TextView stepCounterText;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorPresent;
    int stepCounter = 0;
    private Button goToTemp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepcounter);

        stepCounterText = findViewById(R.id.stepCounterUI);

        goToTemp = findViewById(R.id.firstappbutton);
        goToTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        }
        else
        {
            stepCounterText.setText("No step counter detected");
            isSensorPresent = false;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) { //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == stepSensor)
        {
            stepCounter = (int) sensorEvent.values[0];
            stepCounterText.setText(String.valueOf(stepCounter));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensorManager.registerListener((SensorEventListener) this,stepSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensorManager.unregisterListener((SensorEventListener) this, stepSensor);
        }
    }
}