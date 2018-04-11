package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Time;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by sunny.s on 19/02/18.
 */

public class FuelSystem implements UpdateSystem
{
	private static final String TAG = "Fuel";
	private static long lastProcessTime = 0;
	private static float ONE_SECOND_REFUEL_AMOUNT = 40f;
	private static float MAX_FUEL = 100f;
	private static float MIN_FUEL = 0f;

	private final ComponentManager componentManager;

	@Inject
	public FuelSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> fueledEntities = componentManager.getEntities(Fuel.class);
		for (Entity entity : fueledEntities)
		{
			Fuel fuel = entity.getComponent(Fuel.class);
			if (fuel.getFuel() <= MIN_FUEL)
			{
				if (fuel.isWaitingToRefuel())
				{
					if (fuel.canExitWaiting(deltaTime))
					{
						fuel.exitWaiting();
						refuel(fuel, deltaTime);
					}
				}
				else
				{
					fuel.startWaitingToRefuel();
				}
			}
			else refuel(fuel, deltaTime);
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void reset()
	{
		lastProcessTime = 0;
	}

	private void refuel(Fuel fuel, long deltaTime)
	{
		fuel.refuel(ONE_SECOND_REFUEL_AMOUNT * deltaTime / Time.ONE_SECOND_NANOS);

		fuel.setFuel(Math.min(fuel.getFuel(), MAX_FUEL));
		fuel.setFuel(Math.max(fuel.getFuel(), MIN_FUEL));
	}
}
