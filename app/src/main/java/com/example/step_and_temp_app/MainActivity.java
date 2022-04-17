package com.example.step_and_temp_app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView stepCounterText;

    private SensorManager sensorManager;

    private Sensor stepSensor;
    private Sensor ambientTemp;
    private boolean isSensorPresent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent
        }
        else
        {

        }
    }

    protected void onResume() {        //Since this method has the protected access modifier, it can only be accessed within its own package,
                                       //and by a subclass of its class in another package
        super.onResume();
        sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override   //IS NOT USED but methods need to be implemented since SensorEventListener is implemented by MainActivity
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override   //IS NOT USED but methods need to be implemented since SensorEventListener is implemented by MainActivity
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}