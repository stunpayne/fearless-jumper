package com.stunapps.fearlessjumper.system.eventonly;

import android.util.Log;

import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.impls.CollisionEvent;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.EventType;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.system.System;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sunny.s on 15/02/18.
 */

@Singleton
public class PickupSystem implements System, BaseEventListener
{
	private final EntityManager entityManager;

	@Inject
	public PickupSystem(EntityManager entityManager, EventSystem eventSystem)
	{
		this.entityManager = entityManager;
		eventSystem.registerEventListener(EventType.COLLISION_DETECTED, this);
	}

	@Override
	public void handleEvent(BaseEvent event) throws EventException
	{
		switch (event.eventType)
		{
			case COLLISION_DETECTED:
				CollisionEvent collision = (CollisionEvent) event;
				if (collision.entity1.hasComponent(PlayerComponent.class) &&
						collision.entity2.hasComponent(Pickup.class))
					pickupItem(collision.entity1, collision.entity2);
				else if (collision.entity2.hasComponent(PlayerComponent.class) &&
						collision.entity1.hasComponent(Pickup.class))
					pickupItem(collision.entity2, collision.entity1);
				break;
		}
	}

	private void pickupItem(Entity player, Entity pickup)
	{
		Pickup pickupComponent = pickup.getComponent(Pickup.class);
		switch (pickupComponent.getType())
		{
			case CLOCK:
				player.getComponent(RemainingTime.class)
						.addSeconds(pickupComponent.getPickupValue());
				break;
			case FUEL:
				player.getComponent(Fuel.class).refuel(pickupComponent.getPickupValue());
				break;
			default:
				Log.e("PICKUP", "Invalid pickup type: " + pickupComponent.getType());
				break;
		}
		entityManager.deleteEntity(pickup);
	}
}
