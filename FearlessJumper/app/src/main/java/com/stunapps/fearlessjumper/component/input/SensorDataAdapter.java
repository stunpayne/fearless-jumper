package com.stunapps.fearlessjumper.component.input;

import android.util.Log;

import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.core.SensorDataProvider;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem;

import lombok.Getter;

/**
 * Created by anand.verma on 14/01/18.
 */

public class SensorDataAdapter
{
	private final SensorDataProvider sensorDataProvider;
	private final InputUpdateSystem inputUpdateSystem;
	private long frameTime;

	public SensorDataAdapter(SensorDataProvider sensorDataProvider)
	{
		this.sensorDataProvider = sensorDataProvider;
		this.inputUpdateSystem = DI.di().getInstance(InputUpdateSystem.class);
		frameTime = System.currentTimeMillis();
	}

	private Delta calculate()
	{
		float deltaX = 0;
		float deltaY = 0;
		if (frameTime < Time.INIT_TIME) frameTime = Time.INIT_TIME;
		int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
		frameTime = System.currentTimeMillis();

		Delta delta = Delta.ZERO;
		Log.d("sensorDataProvider",
				"sensorDataProvider.getOrientation() = " + sensorDataProvider.getOrientation() +
						", and sensorDataProvider.getStartOrientation() = " +
						sensorDataProvider.getStartOrientation());
		if (sensorDataProvider.getOrientation() != null &&
				sensorDataProvider.getStartOrientation() != null)
		{

			//  Actually delta pitch and roll
			float pitch = sensorDataProvider.getOrientation()[1] -
					sensorDataProvider.getStartOrientation()[1];
			float roll = sensorDataProvider.getOrientation()[2] -
					sensorDataProvider.getStartOrientation()[2];

			inputUpdateSystem.updateRotationState(new SensorData(pitch, roll));

			float xSpeed = 2 * roll * Device.SCREEN_WIDTH / 1000f;
			float ySpeed = pitch * Device.SCREEN_HEIGHT / 1000f;

			deltaX = Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
			deltaY = Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
			delta = new Delta(deltaX / 2, -deltaY / 2);
		}
		else
		{
			Log.d("sensorDataProvider", "No orientation found");
		}
		return delta;
	}

	public Delta getDeltaMovement()
	{
		return calculate();
	}

	@Getter
	public class SensorData
	{
		float pitch;
		float roll;

		SensorData(float pitch, float roll)
		{
			this.pitch = pitch;
			this.roll = roll;
		}
	}
}
