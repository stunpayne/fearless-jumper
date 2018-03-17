package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.FollowTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.Set;

/**
 * Created by anand.verma on 11/03/18.
 */

public class ConsciousEnemySystem implements UpdateSystem
{
	private static final String TAG = ConsciousEnemySystem.class.getSimpleName();
	private static long lastProcessTime = System.nanoTime();

	private ComponentManager componentManager;

	@Inject
	public ConsciousEnemySystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{
		followTranslationUpdate(deltaTime);
	}

	private void followTranslationUpdate(long deltaTime)
	{
		Set<Entity> followerEntities = componentManager.getEntities(FollowTranslation.class);
		for (Entity followerEntity : followerEntities)
		{
			if (followerEntity.hasComponent(PhysicsComponent.class))
			{
				FollowTranslation followTranslation =
						followerEntity.getComponent(FollowTranslation.class);
				Set<Entity> followeeEntities =
						componentManager.getEntities(followTranslation.targetComponent);
				float minXDiff = Float.MAX_VALUE;
				float minYDiff = Float.MAX_VALUE;
				float xDirection = 1;
				float yDirection = 1;
				PhysicsComponent physicsComponent =
						followerEntity.getComponent(PhysicsComponent.class);
				Log.d(TAG, "process: x velocity = " + physicsComponent.getVelocity().x);
				Log.d(TAG, "process: y velocity = " + physicsComponent.getVelocity().y);
				Log.d(TAG, "process: followeeEntities.size() = " + followeeEntities.size());
				for (Entity followeeEntity : followeeEntities)
				{

					if (Math.abs(followerEntity.transform.position.x -
										 followeeEntity.transform.position.x) < minXDiff)
					{
						minXDiff = Math.abs(followerEntity.transform.position.x -
													followeeEntity.transform.position.x);
						if ((followerEntity.transform.position.x -
								followeeEntity.transform.position.x) > 0)
						{
							xDirection = -1;
						}
						else
						{
							xDirection = 1;
						}
					}

					if (Math.abs(followerEntity.transform.position.y -
										 followeeEntity.transform.position.y) < minYDiff)
					{
						minYDiff = Math.abs(followerEntity.transform.position.y -
													followeeEntity.transform.position.y);
						if ((followerEntity.transform.position.y -
								followeeEntity.transform.position.y) > 0)
						{
							yDirection = -1;
						}
						else
						{
							yDirection = 1;
						}
					}
				}
				Log.d(TAG, "process: x direction = " + xDirection);
				Log.d(TAG, "process: y direction = " + yDirection);
				Velocity velocity =
						followerEntity.getComponent(PhysicsComponent.class).getVelocity();
				velocity.x = updateDirection(velocity.x, xDirection);
				velocity.y = updateDirection(velocity.y, yDirection);

				if (followerEntity.hasComponent(Animator.class))
				{
					Animator animator = followerEntity.getComponent(Animator.class);
					if (velocity.x < 0)
					{
						animator.triggerTransition(AnimationTransition.TURN_LEFT);
					}
					else
					{
						animator.triggerTransition(AnimationTransition.TURN_RIGHT);
					}
				}

				Log.d(TAG,
					  "process: x velocity after update = " + physicsComponent.getVelocity().x);
				Log.d(TAG,
					  "process: y velocity after update = " + physicsComponent.getVelocity().y);
			}
		}
	}

	private float updateDirection(float axisVelocity, float direction)
	{
		if (axisVelocity > 0)
		{
			if (direction > 0)
			{
				return axisVelocity;
			}
			else
			{
				return (-1) * axisVelocity;
			}
		}
		else
		{
			if (direction > 0)
			{
				return (-1) * axisVelocity;
			}
			else
			{
				return axisVelocity;
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
