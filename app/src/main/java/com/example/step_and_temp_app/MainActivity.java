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

import android.hardware.SensorListener;

import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView ambientTempText;
    private TextView ambientTempExplainer;

    private Button goToStepCounterPage;

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

        goToStepCounterPage = findViewById(R.id.buttonlol);

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);


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

        goToStepCounterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TempActivity.class);
                startActivity(intent);
            }
        });
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
            ambientTempText.setText(String.valueOf(ambientTempCelsius));
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