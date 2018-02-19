package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.helper.Environment.Constants;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by sunny.s on 19/02/18.
 */

public class ResourceDegradeSystem implements UpdateSystem
{
	private final ComponentManager componentManager;

	private static long lastProcessTime = 0;
	private static long ONE_SECOND = 1;

	@Inject
	public ResourceDegradeSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> timedEntities = componentManager.getEntities(RemainingTime.class);

		for (Entity entity : timedEntities)
		{
			entity.getComponent(RemainingTime.class)
					.decreaseSeconds(deltaTime * ONE_SECOND / Constants.ONE_MILLION);
			Log.d("RESOURCE_DEGRADE", "Delta time: " + deltaTime + " Remaining time: " +
					entity.getComponent(RemainingTime.class).getRemainingSeconds());
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}
}
