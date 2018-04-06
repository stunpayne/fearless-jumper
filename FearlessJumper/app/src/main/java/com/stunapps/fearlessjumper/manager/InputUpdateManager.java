package com.stunapps.fearlessjumper.manager;

import android.view.MotionEvent;

import com.stunapps.fearlessjumper.component.input.SensorDataAdapter.SensorData;

/**
 * Created by sunny.s on 21/03/18.
 */

public interface InputUpdateManager
{
	void updateState(MotionEvent motionEvent);

	void updateRotationState(SensorData sensorData);
}
