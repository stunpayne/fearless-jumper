package com.stunapps.fearlessjumper.core;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.helper.Environment;

/**
 * Created by anand.verma on 14/01/18.
 */

@Singleton
public class OrientationData implements SensorEventListener
{
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] accelOutput;
    private float[] magOutput;

    private float[] orientation = new float[3];
    private float[] startOrientation = null;

    public float[] getOrientation()
    {
        return orientation;
    }

    public float[] getAccelOutput()
    {
        return accelOutput;
    }

    public float[] getMagOutput()
    {
        return magOutput;
    }

    public float[] getStartOrientation()
    {
        return startOrientation;
    }

    public void newGame()
    {
        startOrientation = null;
    }

    public OrientationData()
    {
        manager = (SensorManager) Environment.CONTEXT.getSystemService(Context
                .SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register()
    {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause()
    {
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;

        if (accelOutput != null && magOutput != null)
        {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);

            if (success)
            {
                SensorManager.getOrientation(R, orientation);
                if (startOrientation == null)
                {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
}
