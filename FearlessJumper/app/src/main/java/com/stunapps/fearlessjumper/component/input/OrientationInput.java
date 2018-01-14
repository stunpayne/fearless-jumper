package com.stunapps.fearlessjumper.component.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.helper.Constants;

/**
 * Created by anand.verma on 14/01/18.
 */

public class OrientationInput extends Input
{
    private OrientationData orientationData;
    private long frameTime;

    @Inject
    public OrientationInput(OrientationData orientationData)
    {
        this.orientationData = orientationData;
        frameTime = System.currentTimeMillis();
    }

    private Delta calculate()
    {
        float deltaX = 0;
        float deltaY = 0;
        if (frameTime < Constants.INIT_TIME)
            frameTime = Constants.INIT_TIME;
        int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();
        Delta delta = new Delta(0, 0);
        if (orientationData.getOrientation() != null && orientationData.getStartOrientation()
                != null)
        {

            //  Actually delta pitch and roll
            float pitch = orientationData.getOrientation()[1] - orientationData
                    .getStartOrientation()[1];
            float roll = orientationData.getOrientation()[2] - orientationData
                    .getStartOrientation()[2];

            float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;
            float ySpeed = pitch * Constants.SCREEN_HEIGHT / 1000f;

            deltaX = Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed *
                    elapsedTime : 0;
            deltaY = Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed *
                    elapsedTime : 0;
            delta = new Delta(deltaX, deltaY);
        } else
        {
            Log.d("orientationData", "No orientation found");
        }
        return delta;
    }

    public Delta getDeltaMovement()
    {
        return calculate();
    }
}
