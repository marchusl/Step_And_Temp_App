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
    private TextView ambientTempText;
    private TextView ambientTempExplainer;

    private SensorManager sensorManager;

    private Sensor stepSensor;
    private Sensor ambientTemp;

    private boolean isStepSensorPresent = false;
    private boolean isAmbientTempPresent = false;

    private int ambientTempCelsius = 0;
    private int StepCounter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCounterText = findViewById(R.id.stepCounterUI);

        ambientTempText = findViewById(R.id.ambientTempUI);
        ambientTempExplainer = findViewById(R.id.ambTempExplainer);

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isStepSensorPresent = true;
            System.out.println("STEP COUNTER DETECTED SENSOR LETS GO");
        }
        else
        {
            //stepCounterText.setText("No step counter detected");
            System.out.println("No step counter detected");
            isStepSensorPresent = false;
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null) {
            ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isAmbientTempPresent = true;
        }
        else {
            ambientTempText.setText("No ambient temperature sensor detected");
            System.out.println("No ambient temperature sensor detected");
            isAmbientTempPresent = false;
        }

        System.out.println(isAmbientTempPresent);
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

        if (sensorEvent.sensor == ambientTemp) {
            StepCounter = (int) sensorEvent.values[0];
            ambientTempText.setText(String.valueOf(ambientTempCelsius));
        }

        if (sensorEvent.sensor == stepSensor) {
            StepCounter = (int) sensorEvent.values[0];
            stepCounterText.setText(String.valueOf(StepCounter));
        }

        if (ambientTempCelsius < 18) {
            ambientTempExplainer.setText("It's a bit chilly in here!");
        }

        if (ambientTempCelsius > 22) {
            ambientTempExplainer.setText("It's a bit hot in here!");
        }
    }

    @Override   //IS NOT USED but methods need to be implemented since SensorEventListener is implemented by MainActivity
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}