package com.stunapps.fearlessjumper.system.input.processor;

import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.Systems;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem.State;
import com.stunapps.fearlessjumper.system.update.RenderSystem;
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

	private static float JUMP_IMPULSE = 1 / 80f;
	private static float FUEL_DISCHARGE = 5f;
	private static long lastProcessTime = System.nanoTime();

	private static Map<Class, Long> debugSystemRunTimes = new HashMap<>();

	@Inject
	public PlayerInputProcessor()
	{
	}

	public void update(long deltaTime, Entity player, State screenTouchState)
	{
		long currentTime = System.currentTimeMillis();
		lastProcessTime = System.currentTimeMillis();

		if (screenTouchState == State.SCREEN_PRESSED)
		{
			//  To test is input is occurring between collision and transform update
			//			logSystemTimes();
			Rect entityCanvasRect = RenderSystem.getRenderRect(player);
			Fuel fuel = player.getComponent(Fuel.class);
			if (fuel.getFuel() > 0)
			{
				applyForceToPlayer(new Position(entityCanvasRect.left, entityCanvasRect.top),
						player.getComponent(PhysicsComponent.class));
				dischargeFuel(fuel, deltaTime);
				player.getComponent(Emitter.class).activate();
			}
		}
		else if (screenTouchState == State.SCREEN_RELEASED)
		{
			Log.d(TAG, "Screen released");
			player.getComponent(Emitter.class).deactivate();
		}
	}

	@Override
	public void handleSensorEvent(Entity entity, SensorEvent sensorEvent)
	{

	}

	private void applyForceToPlayer(Position position, PhysicsComponent physicsComponent)
	{
		float forceY = -Math.max((Device.SCREEN_HEIGHT - position.y), 40);
		physicsComponent.getVelocity().y += (forceY * JUMP_IMPULSE);
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
