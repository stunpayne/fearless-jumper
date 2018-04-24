package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.model.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.system.EventSystem;
import com.stunapps.fearlessjumper.game.Time;

import javax.inject.Inject;

/**
 * Created by sunny.s on 19/02/18.
 */

public class ClockCountdownSystem implements UpdateSystem
{
	private final ComponentManager componentManager;
	private final EventSystem eventSystem;

	private static long lastProcessTime = 0;
	private static long ONE_SECOND = 1;

	@Inject
	public ClockCountdownSystem(ComponentManager componentManager, EventSystem eventSystem)
	{
		this.componentManager = componentManager;
		this.eventSystem = eventSystem;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Entity timedEntity = componentManager.getEntity(RemainingTime.class);
		RemainingTime remainingTime = timedEntity.getComponent(RemainingTime.class);
		if (remainingTime.isUp())
		{
			eventSystem.raiseEvent(new GameOverEvent());
		}
		else
		{
			remainingTime.decreaseSeconds(deltaTime * ONE_SECOND / Time.ONE_MILLION);
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
		ONE_SECOND = 1;
	}
}
