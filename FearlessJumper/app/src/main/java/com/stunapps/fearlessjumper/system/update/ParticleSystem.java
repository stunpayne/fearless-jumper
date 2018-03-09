package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.system.EmitterEvent;
import com.stunapps.fearlessjumper.event.EventException;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by anand.verma on 06/03/18.
 */

public class ParticleSystem implements UpdateSystem
{
	private static final String TAG = ParticleSystem.class.getSimpleName();

	private final ComponentManager componentManager;
	private static long lastProcessTime = System.nanoTime();

	@Inject
	public ParticleSystem(EventSystem eventSystem, ComponentManager componentManager)
	{
		this.componentManager = componentManager;
		eventSystem.registerEventListener(EmitterEvent.class, emitterEventListener);
	}

	private BaseEventListener<EmitterEvent> emitterEventListener =
			new BaseEventListener<EmitterEvent>()
			{
				@Override
				public void handleEvent(EmitterEvent event) throws EventException
				{
					Entity entity = event.entity;
					if (entity.hasComponent(Emitter.class))
					{
						entity.getComponent(Emitter.class).init();
					}
				}
			};

	@Override
	public void process(long deltaTime)
	{
		Set<Entity> entities = componentManager.getEntities(Emitter.class);
		if (entities != null)
		{
			Iterator<Entity> iterator = entities.iterator();
			while (iterator.hasNext())
			{
				Entity entity = iterator.next();
				Emitter emitter = entity.getComponent(Emitter.class);
				if (emitter.isInitialised())
				{
					emitter.update(deltaTime);
					if (emitter.isExhausted())
					{
						componentManager.deleteEntity(entity);
					}
				}
			}
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
		lastProcessTime = System.nanoTime();
	}
}
