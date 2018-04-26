package com.stunapps.fearlessjumper.system.input.processor;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.input.SensorDataAdapter.SensorData;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Velocity;
import com.stunapps.fearlessjumper.system.Systems;
import com.stunapps.fearlessjumper.system.model.InputWrapper;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem.ScreenState;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by sunny.s on 20/01/18.
 */

@Singleton
public class PlayerInputProcessor implements InputProcessor
{
	private static final String TAG = PlayerInputProcessor.class.getSimpleName();

	private static float FUEL_DISCHARGE = 80f;

	private static float JUMP_IMPULSE = -2400f;

	private static float X_MULTIPLIER = 1800f;
	private static float MAX_X_SPEED = 420f;
	private static float MIN_X_SPEED = -420f;

	private static long lastProcessTime = System.nanoTime();
	private static Map<Class, Long> debugSystemRunTimes = new HashMap<>();

	@Inject
	public PlayerInputProcessor()
	{
	}

	public void update(long deltaTime, Entity player, InputWrapper inputWrapper)
	{
		lastProcessTime = System.currentTimeMillis();

		handleTouch(inputWrapper.getScreenState(), player, deltaTime);
		handleSensorData(inputWrapper.getSensorData(), player);
	}


	private void handleTouch(ScreenState screenTouchState, Entity player, long deltaTime)
	{
		if (screenTouchState == ScreenState.SCREEN_PRESSED)
		{
			//  To test is input is occurring between collision and transform update
			//			logSystemTimes();
			Fuel fuel = player.getComponent(Fuel.class);
			if (fuel.getFuel() > 0)
			{
				applyForceToPlayer(player.getComponent(PhysicsComponent.class));
				dischargeFuel(fuel, deltaTime);
				player.getComponent(Emitter.class).activate();
			}
			else
			{
				player.getComponent(Emitter.class).deactivate();
			}
		}
		else if (screenTouchState == ScreenState.SCREEN_RELEASED)
		{
			player.getComponent(Emitter.class).deactivate();
		}
	}

	private void handleSensorData(SensorData sensorData, Entity player)
	{
		if (sensorData == null) return;

		float roll = sensorData.getRoll();

		float xSpeed = scaleX(X_MULTIPLIER * roll * Time.DELTA_TIME);

		Velocity velocity = player.getComponent(PhysicsComponent.class).getVelocity();
		float newXVel = velocity.getX() + xSpeed;

		/*
		 * Clamp x speed
		 */
		newXVel = Math.max(scaleX(MIN_X_SPEED), newXVel);
		newXVel = Math.min(scaleX(MAX_X_SPEED), newXVel);

		velocity.setX(newXVel);
	}

	private void applyForceToPlayer(PhysicsComponent physicsComponent)
	{
		physicsComponent.getVelocity().y += (scaleY(JUMP_IMPULSE) * Time.DELTA_TIME);
	}

	private void dischargeFuel(Fuel fuel, long deltaTime)
	{
		Log.v("FUEL", "Current: " + fuel.getFuel() + " discharge amount: " +
				FUEL_DISCHARGE * deltaTime / Time.ONE_SECOND_NANOS);
		fuel.dischargeFuel(FUEL_DISCHARGE * deltaTime / Time.ONE_SECOND_NANOS);
	}


	/**
	 * Helper methods to log the last times at which various systems ran. Used for some debugging
	 */
	private void logSystemTimes()
	{
		debugSystemRunTimes.put(this.getClass(), lastProcessTime);
		for (UpdateSystem system : Systems.getSystemsInOrder())
		{
			debugSystemRunTimes.put(system.getClass(), system.getLastProcessTime());
		}

		List<Entry<Class, Long>> list =
				new LinkedList<Entry<Class, Long>>(debugSystemRunTimes.entrySet());

		Collections.sort(list, comparator());
		for (Entry<Class, Long> entry : list)
		{
			Log.v("SYSTEM", "System\t" + entry.getKey().getSimpleName() + "\t" + entry.getValue());
		}
	}

	private static Comparator<Map.Entry<Class, Long>> comparator()
	{
		return new Comparator<Map.Entry<Class, Long>>()
		{
			@Override
			public int compare(Map.Entry<Class, Long> o1, Map.Entry<Class, Long> o2)
			{
				return o1.getValue().compareTo(o2.getValue());
			}
		};
	}
}
