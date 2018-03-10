package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Set;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;
import static com.stunapps.fearlessjumper.system.update.MovementUpdateSystem.MovementUpdater
		.updatePeriodicMotion;

/**
 * Created by sunny.s on 21/01/18.
 */

public class MovementUpdateSystem implements UpdateSystem
{
	private static final String TAG = MovementUpdateSystem.class.getSimpleName();

	private final ComponentManager componentManager;

	private static long lastProcessTime = 0;

	@Inject
	public MovementUpdateSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> periodicEntities = componentManager.getEntities(PeriodicTranslation.class);

		for (Entity entity : periodicEntities)
		{
			PeriodicTranslation periodicTranslation =
					entity.getComponent(PeriodicTranslation.class);
			updatePeriodicMotion(entity, periodicTranslation);
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

	static class MovementUpdater
	{
		static void updatePeriodicMotion(Entity entity, PeriodicTranslation periodicTranslation)
		{
			if (periodicTranslation.movesInX())
			{
				moveEntityHorizontally(entity, periodicTranslation);
			}

			if (periodicTranslation.movesInY())
			{
				moveEntityVertically(entity, periodicTranslation);
			}
		}

		static void moveEntityHorizontally(Entity entity, PeriodicTranslation periodicTranslation)
		{
			float speedX = periodicTranslation.getSpeedX();
			PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
			float deltaX = physicsComponent.getVelocity().x;
			if (deltaX + entity.transform.position.x >= periodicTranslation.maxX)
			{
				periodicTranslation.setSpeedX(-1 * speedX);

				//TODO: need to write it in more cleaner way.
				if (entity.hasComponent(Animator.class))
				{
					entity.getComponent(Animator.class)
							.triggerTransition(AnimationTransition.TURN_LEFT);
				}
			}
			if (deltaX + entity.transform.position.x <= periodicTranslation.minX)
			{
				periodicTranslation.setSpeedX(-1 * speedX);
				if (entity.hasComponent(Animator.class))
				{
					entity.getComponent(Animator.class)
							.triggerTransition(AnimationTransition.TURN_RIGHT);
				}
			}

			entity.getComponent(PhysicsComponent.class).getVelocity().x =
					periodicTranslation.getSpeedX() * scaleX();
		}

		static void moveEntityVertically(Entity entity, PeriodicTranslation periodicTranslation)
		{
			float speedY = periodicTranslation.getSpeedY();

			PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
			float deltaY = physicsComponent.getVelocity().y;
			if (deltaY + entity.transform.position.y >= periodicTranslation.maxY)
			{
				periodicTranslation.setSpeedY(-1 * speedY);
			}
			if (deltaY + entity.transform.position.y <= periodicTranslation.minY)
			{
				periodicTranslation.setSpeedY(-1 * speedY);
			}

			entity.getComponent(PhysicsComponent.class).getVelocity().y =
					periodicTranslation.getSpeedY() * scaleY();
			Log.v(TAG, "Updated velocity to : " +
					entity.getComponent(PhysicsComponent.class).getVelocity().y);
		}
	}
}
