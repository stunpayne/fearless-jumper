package com.stunapps.fearlessjumper.system.eventonly;

import android.util.Log;

import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.impls.CollisionEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.system.System;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sunny.s on 15/02/18.
 */

@Singleton
public class PickupSystem implements System
{
    private final EntityManager entityManager;

    BaseEventListener<CollisionEvent> collisionEventListener = new BaseEventListener<CollisionEvent>()
    {
        @Override
        public void handleEvent(CollisionEvent collisionEvent) throws EventException
        {
            if (collisionEvent.entity1.hasComponent(PlayerComponent.class) &&
                    collisionEvent.entity2.hasComponent(Pickup.class))
            {
                pickupItem(collisionEvent.entity1, collisionEvent.entity2);
            } else if (collisionEvent.entity2.hasComponent(PlayerComponent.class) &&
                    collisionEvent.entity1.hasComponent(Pickup.class))
            {
                pickupItem(collisionEvent.entity2, collisionEvent.entity1);
            }
        }
    };


    @Inject
    public PickupSystem(EntityManager entityManager, EventSystem eventSystem)
    {
        this.entityManager = entityManager;
        eventSystem.registerEventListener(CollisionEvent.class, collisionEventListener);
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
