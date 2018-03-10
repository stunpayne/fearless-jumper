package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.system.CollisionEvent;
import com.stunapps.fearlessjumper.event.EventException;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.system.model.CollisionResponse.CollisionFace;

import java.util.Set;

import static com.stunapps.fearlessjumper.game.Time.ONE_SECOND_NANOS;
import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by sunny.s on 19/01/18.
 */

@Singleton
public class PhysicsSystem implements UpdateSystem
{
	private final ComponentManager componentManager;
	private static long lastProcessTime = System.nanoTime();

	private static final float GRAVITY = -9.8f;

	BaseEventListener<CollisionEvent> collisionEventListener = new BaseEventListener<CollisionEvent>()
	{
		@Override
		public void handleEvent(CollisionEvent collisionEvent) throws EventException
		{
			if (collisionEvent.entity1.hasComponent(PhysicsComponent.class) &&
					collisionEvent.entity2.hasComponent(PhysicsComponent.class))
			{
				handleFriction(collisionEvent.entity1, collisionEvent.entity2,
						collisionEvent.collisionFace, collisionEvent.deltaTime);
			}
		}
	};

	@Inject
	public PhysicsSystem(ComponentManager componentManager, EventSystem eventSystem)
	{
		this.componentManager = componentManager;
		eventSystem.registerEventListener(CollisionEvent.class, collisionEventListener);
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
				applyGravity(entity);
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

	private void applyGravity(Entity entity)
	{
		PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
		physicsComponent.getVelocity().y -= (GRAVITY * scaleY() * Time.DELTA_TIME);
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
		float entity1XVel = Math.abs(physicsComponent1.getVelocity().x);

		if (friction > 0)
		{
			if (entity1XVel > 0)
			{
				entity1XVel -= (friction * scaleX() * deltaTime / ONE_SECOND_NANOS);
				physicsComponent1.getVelocity().x =
						entity1XVel * sign(physicsComponent1.getVelocity().x);
				if (entity1XVel < 0)
				{
					physicsComponent1.getVelocity().x = 0;
				}
			}

			float entity2XVel = Math.abs(physicsComponent2.getVelocity().x);
			if (entity2XVel > 0)
			{
				entity2XVel -= (friction * scaleX() * deltaTime / ONE_SECOND_NANOS);
				physicsComponent2.getVelocity().x =
						entity2XVel * sign(physicsComponent2.getVelocity().x);
				if (entity2XVel < 0)
				{
					physicsComponent2.getVelocity().x = 0;
				}
			}
		}
	}

	private void resolveVerticalFriction(Entity entity1, Entity entity2, long deltaTime)
	{
		PhysicsComponent physicsComponent1 = entity1.getComponent(PhysicsComponent.class);
		PhysicsComponent physicsComponent2 = entity2.getComponent(PhysicsComponent.class);
		float friction =
				physicsComponent1.friction.rightFriction + physicsComponent2.friction.leftFriction;
		float entity1YVel = Math.abs(physicsComponent1.getVelocity().y);

		if (friction > 0)
		{
			if (entity1YVel > 0)
			{
				entity1YVel -= (friction * scaleY() * deltaTime / ONE_SECOND_NANOS);
				physicsComponent1.getVelocity().y =
						entity1YVel * sign(physicsComponent1.getVelocity().y);
				if (entity1YVel < 0)
				{
					physicsComponent1.getVelocity().y = 0;
				}
			}

			float entity2YVel = Math.abs(physicsComponent2.getVelocity().y);
			if (entity2YVel > 0)
			{
				entity2YVel -= (friction * scaleY() * deltaTime / ONE_SECOND_NANOS);
				physicsComponent2.getVelocity().y =
						entity2YVel * sign(physicsComponent2.getVelocity().y);
				if (entity2YVel < 0)
				{
					physicsComponent2.getVelocity().y = 0;
				}
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
