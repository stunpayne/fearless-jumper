package com.stunapps.fearlessjumper.system.eventonly;

import android.util.Log;

import com.stunapps.fearlessjumper.audio.Sound;
import com.stunapps.fearlessjumper.audio.Sound.Effect;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.system.CollisionEvent;
import com.stunapps.fearlessjumper.event.system.EmitterEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.prefab.Prefabs;
import com.stunapps.fearlessjumper.system.System;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sunny.s on 15/02/18.
 */

@Singleton
public class PickupSystem implements System
{
	private final EntityManager entityManager;
	private final SoundSystem soundSystem;
	private final EventSystem eventSystem;

	BaseEventListener<CollisionEvent> collisionEventListener =
			new BaseEventListener<CollisionEvent>()
			{
				@Override
				public void handleEvent(CollisionEvent collisionEvent) throws EventException
				{
					if (collisionEvent.entity1.hasComponent(PlayerComponent.class) &&
							collisionEvent.entity2.hasComponent(Pickup.class))
					{
						pickupItem(collisionEvent.entity1, collisionEvent.entity2);
					}
					else if (collisionEvent.entity2.hasComponent(PlayerComponent.class) &&
							collisionEvent.entity1.hasComponent(Pickup.class))
					{
						pickupItem(collisionEvent.entity2, collisionEvent.entity1);
					}
				}
			};


	@Inject
	public PickupSystem(EntityManager entityManager, EventSystem eventSystem,
			SoundSystem soundSystem)
	{
		this.entityManager = entityManager;
		this.soundSystem = soundSystem;
		this.eventSystem = eventSystem;
		eventSystem.registerEventListener(CollisionEvent.class, collisionEventListener);
	}

	private void pickupItem(Entity player, Entity pickup)
	{
		Pickup pickupComponent = pickup.getComponent(Pickup.class);
		switch (pickupComponent.getType())
		{
			case CLOCK:
				player.getComponent(RemainingTime.class)
						.addSeconds((long) pickupComponent.getPickupValue());
				Entity entity = entityManager.instantiate(Prefabs.CLOCK_PARTICLE.prefab, pickup
						.transform);
				eventSystem.raiseEvent(new EmitterEvent(entity));
				break;
			case FUEL:
				player.getComponent(Fuel.class).refuel(pickupComponent.getPickupValue());
				break;
			default:
				Log.e("PICKUP", "Invalid pickup type: " + pickupComponent.getType());
				break;
		}
		entityManager.deleteEntity(pickup);
		soundSystem.playSoundEffect(Effect.TIME_PICKUP.getSoundResId());
	}
}
