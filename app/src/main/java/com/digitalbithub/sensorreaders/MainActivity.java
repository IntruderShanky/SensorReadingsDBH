package com.digitalbithub.sensorreaders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor accelerometerSensor;
    private SensorManager sensorManager;


    private TextView xAxisView;
    private TextView yAxisView;
    private TextView zAxisView;

    private boolean accelAvailable = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        xAxisView = findViewById(R.id.x_axis);
        yAxisView = findViewById(R.id.y_axis);
        zAxisView = findViewById(R.id.z_axis);

        if (sensorManager == null)
            return;

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelAvailable = accelerometerSensor != null;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (accelAvailable) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
            case Sensor.TYPE_GYROSCOPE:
                xAxisView.setText(String.format(Locale.getDefault(), "%.2f", event.values[0]));
                yAxisView.setText(String.format(Locale.getDefault(), "%.2f", event.values[1]));
                zAxisView.setText(String.format(Locale.getDefault(), "%.2f", event.values[2]));
                break;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
