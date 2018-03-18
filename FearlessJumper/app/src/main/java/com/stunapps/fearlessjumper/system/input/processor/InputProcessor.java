package com.stunapps.fearlessjumper.system.input.processor;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.update.InputProcessSystem.State;

/**
 * Created by sunny.s on 20/01/18.
 */

public interface InputProcessor
{
	void handleTouchEvent(final Entity entity, final MotionEvent motionEvent);

	void handleSensorEvent(final Entity entity, final SensorEvent sensorEvent);

	void update(long deltaTime, Entity player, State screenTouchState);
}
