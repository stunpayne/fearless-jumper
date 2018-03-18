package com.stunapps.fearlessjumper.system.input;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

/**
 * Created by sunny.s on 19/01/18.
 */

public interface InputSystem
{
	void processSensorInput(SensorEvent sensorEvent);

	void processTouchInput(MotionEvent motionEvent);
}
