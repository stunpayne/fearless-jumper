package com.stunapps.fearlessjumper.system.eventonly;

import android.util.Log;

import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventInfo;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.CollisionEventInfo;
import com.stunapps.fearlessjumper.event.Event;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.system.System;

import javax.inject.Singleton;

/**
 * Created by sunny.s on 15/02/18.
 */

@Singleton
public class PickupSystem implements System, BaseEventListener
{
	@Override
	public void handleEvent(Event event, BaseEventInfo eventInfo) throws EventException
	{
		switch (event)
		{
			case COLLISION_DETECTED:
				CollisionEventInfo collision = (CollisionEventInfo) eventInfo;
				if (collision.entity1.hasComponent(PlayerComponent.class) &&
						collision.entity2.hasComponent(Pickup.class))
					pickupItem(collision.entity1, collision.entity2);
				else if (collision.entity2.hasComponent(PlayerComponent.class) &&
						collision.entity1.hasComponent(Pickup.class))
					pickupItem(collision.entity2, collision.entity1);
				break;
		}
	}

	void pickupItem(Entity player, Entity pickup)
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
	}
}
