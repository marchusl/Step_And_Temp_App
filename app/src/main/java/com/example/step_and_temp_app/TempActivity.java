package com.example.step_and_temp_app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    private TextView ambientTempText;
    private TextView ambientTempExplainer;

    private SensorManager sensorManager;

    private Sensor ambientTemp;

    private boolean isAmbientTempPresent = false;

    private int ambientTempCelsius = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ambientTempText = findViewById(R.id.ambientTempUI);
        ambientTempExplainer = findViewById(R.id.ambTempExplainer);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);


        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isAmbientTempPresent = true;
        } else {
            ambientTempText.setText("No ambient temperature sensor detected");
            System.out.println("No ambient temperature sensor detected");
            isAmbientTempPresent = false;
        }

        System.out.println(isAmbientTempPresent);
    }

    protected void onResume() {        //Since this method has the protected access modifier, it can only be accessed within its own package,
        //and by a subclass of its class in another package
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, ambientTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    //IS NOT USED but methods need to be implemented since SensorEventListener is implemented by MainActivity
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor == ambientTemp) {
            ambientTempText.setText(String.valueOf(ambientTempCelsius));
        }

        if (ambientTempCelsius < 18) {
            ambientTempExplainer.setText("It's a bit chilly in here!");
        }

        if (ambientTempCelsius > 22) {
            ambientTempExplainer.setText("It's a bit hot in here!");
        }
    }

    //IS NOT USED but methods need to be implemented since SensorEventListener is implemented by MainActivity
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}