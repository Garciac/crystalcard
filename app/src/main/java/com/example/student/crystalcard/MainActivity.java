package com.example.student.crystalcard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    private TextView answerText;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private double acceleration;
    private double currentAcceleration;
    private double previousAcceleration;

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];

           previousAcceleration = currentAcceleration;
           currentAcceleration = Math.sqrt(x * x + y * y + z * z);
            double delta = currentAcceleration - previousAcceleration;
           acceleration = acceleration * 0.9f + delta;

            if(acceleration > 15){
                //Audio
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound_file_1);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you

                String ans = Predictions.get().getPrediction();
                answerText.setText(ans);

                Toast toast = Toast.makeText(getApplication(), "Device has shaken", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        previousAcceleration = SensorManager.GRAVITY_EARTH;

        answerText = (TextView) findViewById(R.id.answerText);
        answerText.setText(Predictions.get().getPrediction());
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}
