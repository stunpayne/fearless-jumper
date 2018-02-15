package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.impls.CollisionEvent;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.EventType;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import java.util.Set;

import static com.stunapps.fearlessjumper.helper.Environment.ONE_SECOND_NANOS;
import static com.stunapps.fearlessjumper.helper.Environment.scale;

/**
 * Created by sunny.s on 19/01/18.
 */

@Singleton
public class PhysicsSystem implements UpdateSystem, BaseEventListener
{
	private final ComponentManager componentManager;
	private static long lastProcessTime = System.nanoTime();

	private static final float GRAVITY = -9.8f;

	@Inject
	public PhysicsSystem(ComponentManager componentManager, EventSystem eventSystem)
	{
		this.componentManager = componentManager;
		eventSystem.registerEventListener(EventType.COLLISION_DETECTED, this);
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> physicalEntities = componentManager.getEntities(PhysicsComponent.class);

		for (Entity entity : physicalEntities)
		{
			PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
			if (physicsComponent.mass > 0 && physicsComponent.applyGravity)
			{
				applyGravity(entity, deltaTime);
			}
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void handleEvent(BaseEvent event) throws EventException
	{
		switch (event.eventType)
		{
			case COLLISION_DETECTED:
				CollisionEvent collisionEventInfo = (CollisionEvent) event;
				if (collisionEventInfo.entity1.hasComponent(PhysicsComponent.class) &&
						collisionEventInfo.entity2.hasComponent(PhysicsComponent.class))
					handleFriction(collisionEventInfo.entity1, collisionEventInfo.entity2,
								   collisionEventInfo.collisionFace, collisionEventInfo.deltaTime);
				else if (collisionEventInfo.entity2.hasComponent(PhysicsComponent.class) &&
						collisionEventInfo.entity1.hasComponent(PhysicsComponent.class))
					handleFriction(collisionEventInfo.entity2, collisionEventInfo.entity1,
								   collisionEventInfo.collisionFace, collisionEventInfo.deltaTime);
				break;
		}
	}


	private void applyGravity(Entity entity, long deltaTime)
	{
		PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
		physicsComponent.velocity.y -= (GRAVITY * scale() * deltaTime / ONE_SECOND_NANOS);
	}

	private void handleFriction(Entity entity1, Entity entity2, CollisionFace collisionFace,
			long deltaTime)
	{
		switch ((collisionFace))
		{
			case HORIZONTAL:
				resolveHorizontalFriction(entity1, entity2, deltaTime);
				break;
			case HORIZONTAL_REVERSE:
				break;
			case VERTICAL:
				resolveVerticalFriction(entity1, entity2, deltaTime);
				break;
			case VERTICAL_REVERSE:
				break;
		}
	}

	private void resolveHorizontalFriction(Entity entity1, Entity entity2, long deltaTime)
	{
		PhysicsComponent physicsComponent1 = entity1.getComponent(PhysicsComponent.class);
		PhysicsComponent physicsComponent2 = entity2.getComponent(PhysicsComponent.class);
		float friction =
				physicsComponent1.friction.bottomFriction + physicsComponent2.friction.topFriction;
		float entity1XVel = Math.abs(physicsComponent1.velocity.x);
		if (entity1XVel > 0)
		{
			entity1XVel -= (friction * scale() * deltaTime / ONE_SECOND_NANOS);
			physicsComponent1.velocity.x = entity1XVel * sign(physicsComponent1.velocity.x);
			if (entity1XVel < 0)
			{
				physicsComponent1.velocity.x = 0;
			}
		}

		float entity2XVel = Math.abs(physicsComponent2.velocity.x);
		if (entity2XVel > 0)
		{
			entity2XVel -= (friction * scale() * deltaTime / ONE_SECOND_NANOS);
			physicsComponent2.velocity.x -= entity2XVel * sign(physicsComponent2.velocity.x);
			if (entity2XVel < 0)
			{
				physicsComponent2.velocity.x = 0;
			}
		}
	}

	private void resolveVerticalFriction(Entity entity1, Entity entity2, long deltaTime)
	{
		PhysicsComponent physicsComponent1 = entity1.getComponent(PhysicsComponent.class);
		PhysicsComponent physicsComponent2 = entity2.getComponent(PhysicsComponent.class);
		float friction =
				physicsComponent1.friction.rightFriction + physicsComponent2.friction.leftFriction;
		float entity1YVel = Math.abs(physicsComponent1.velocity.y);
		if (entity1YVel > 0)
		{
			entity1YVel -= (friction * scale() * deltaTime / ONE_SECOND_NANOS);
			physicsComponent1.velocity.y = entity1YVel * sign(physicsComponent1.velocity.y);
			if (entity1YVel < 0)
			{
				physicsComponent1.velocity.y = 0;
			}
		}

		float entity2YVel = Math.abs(physicsComponent2.velocity.y);
		if (entity2YVel > 0)
		{
			entity2YVel -= (friction * scale() * deltaTime / ONE_SECOND_NANOS);
			physicsComponent2.velocity.y -= entity2YVel * sign(physicsComponent2.velocity.y);
			if (entity2YVel < 0)
			{
				physicsComponent2.velocity.y = 0;
			}
		}
	}

	private float sign(float number)
	{
		if (number == 0.0f)
		{
			return 0.0f;
		}
		return number / Math.abs(number);
	}
}
