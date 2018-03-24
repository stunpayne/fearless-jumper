package com.stunapps.fearlessjumper.component.input;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.core.SensorDataProvider;
import com.stunapps.fearlessjumper.game.Time;

import lombok.Getter;

/**
 * Created by anand.verma on 14/01/18.
 */

@Singleton
public class SensorDataAdapter
{
	private final SensorDataProvider sensorDataProvider;
	private long frameTime;

	@Inject
	public SensorDataAdapter(SensorDataProvider sensorDataProvider)
	{
		this.sensorDataProvider = sensorDataProvider;
		frameTime = System.currentTimeMillis();
		this.sensorDataProvider.register();
	}

	public SensorData update()
	{
		if (frameTime < Time.INIT_TIME) frameTime = Time.INIT_TIME;
		frameTime = System.currentTimeMillis();

		Log.d("sensorDataAdapter", "sensorDataProvider.getOrientation() = " +
				String.valueOf(sensorDataProvider.getOrientation()) +
				", and sensorDataProvider.getStartOrientation() = " +
				String.valueOf(sensorDataProvider.getStartOrientation()));
		if (sensorDataProvider.getOrientation() != null &&
				sensorDataProvider.getStartOrientation() != null)
		{

			//  Actually delta pitch and roll
			float pitch = sensorDataProvider.getOrientation()[1] -
					sensorDataProvider.getStartOrientation()[1];
			float roll = sensorDataProvider.getOrientation()[2] -
					sensorDataProvider.getStartOrientation()[2];
			return new SensorData(pitch, roll);
		}
		else
		{
			Log.d("sensorDataProvider", "No orientation found");
			return null;
		}
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
