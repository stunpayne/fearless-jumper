package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.animation.AnimationTransition;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.AssaultTranslation;
import com.stunapps.fearlessjumper.component.movement.ConsciousTranslation;
import com.stunapps.fearlessjumper.component.movement.FollowTranslation;
import com.stunapps.fearlessjumper.component.movement.PeriodicTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.visual.BitmapAnimator;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment.Settings;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Velocity;
import com.stunapps.fearlessjumper.particle.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;
import static com.stunapps.fearlessjumper.game.Environment.scaleY;
import static com.stunapps.fearlessjumper.system.update.MovementUpdateSystem.MovementUpdater
		.updateAssaultMotion;
import static com.stunapps.fearlessjumper.system.update.MovementUpdateSystem.MovementUpdater
		.updateFollowMotion;
import static com.stunapps.fearlessjumper.system.update.MovementUpdateSystem.MovementUpdater
		.updatePeriodicMotion;

/**
 * Created by sunny.s on 21/01/18.
 */

public class MovementUpdateSystem implements UpdateSystem
{
	private static final String TAG = MovementUpdateSystem.class.getSimpleName();

	private static ComponentManager componentManager;

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

		//TODO: Independently update in separate threads and join threads before return.

		Set<Entity> periodicEntities = componentManager.getEntities(PeriodicTranslation.class);

		for (Entity entity : periodicEntities)
		{
			PeriodicTranslation periodicTranslation =
					entity.getComponent(PeriodicTranslation.class);
			updatePeriodicMotion(entity, periodicTranslation);
		}

		Set<Entity> consciousEntities = componentManager.getEntities(ConsciousTranslation.class);
		for (Entity consciousEntity : consciousEntities)
		{
			ConsciousTranslation consciousTranslation =
					consciousEntity.getComponent(ConsciousTranslation.class);
			if (consciousTranslation instanceof AssaultTranslation)
			{
				updateAssaultMotion(consciousEntity, (AssaultTranslation) consciousTranslation,
									deltaTime);
			}
			else if (consciousTranslation instanceof FollowTranslation)
			{
				updateFollowMotion(consciousEntity, (FollowTranslation) consciousTranslation,
								   deltaTime);
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
		lastProcessTime = 0;
	}

	static class MovementUpdater
	{
		private static int frames = 0;
		private static long lastReverseTime = 0;

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
			float deltaX = physicsComponent.getVelocity().x * Time.DELTA_TIME;
			if (deltaX + entity.transform.position.x >= periodicTranslation.maxX)
			{
				periodicTranslation.setSpeedX(-1 * speedX);

				//TODO: need to write it in more cleaner way.
				if (entity.hasComponent(BitmapAnimator.class))
				{
					entity.getComponent(BitmapAnimator.class)
							.triggerTransition(AnimationTransition.TURN_LEFT);
				}
			}
			if (deltaX + entity.transform.position.x <= periodicTranslation.minX)
			{
				periodicTranslation.setSpeedX(-1 * speedX);
				if (entity.hasComponent(BitmapAnimator.class))
				{
					entity.getComponent(BitmapAnimator.class)
							.triggerTransition(AnimationTransition.TURN_RIGHT);
				}
			}

			entity.getComponent(PhysicsComponent.class).getVelocity().x =
					(float) (periodicTranslation.getSpeedX() * scaleX());
		}

		static void moveEntityVertically(Entity entity, PeriodicTranslation periodicTranslation)
		{
			frames++;
			float speedY = periodicTranslation.getSpeedY();

			PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
			float deltaY = (float) (physicsComponent.getVelocity().y * Time.DELTA_TIME);
			if (deltaY + entity.transform.position.y >= periodicTranslation.maxY)
			{
				//				debugLog();
				periodicTranslation.setSpeedY(-1 * speedY);
			}
			if (deltaY + entity.transform.position.y <= periodicTranslation.minY)
			{
				//				debugLog();
				periodicTranslation.setSpeedY(-1 * speedY);
			}

			entity.getComponent(PhysicsComponent.class).getVelocity().y =
					periodicTranslation.getSpeedY() * scaleY();
		}

		static float calculateDistance(Entity followerEntity, Entity targetEntity)
		{
			float xDistance = Math.abs(
					followerEntity.transform.position.x - targetEntity.transform.position.x);
			float yDistance = Math.abs(
					followerEntity.transform.position.y - targetEntity.transform.position.y);
			float squaredSum = xDistance * xDistance + yDistance * yDistance;
			return (float) Math.sqrt(squaredSum);
		}

		static void updateFollowMotion(Entity followerEntity, FollowTranslation followTranslation,
				long deltaTime)
		{
			if (followerEntity.hasComponent(PhysicsComponent.class))
			{
				//Need any ordering in list of target entities.
				List<Entity> targetEntities = new LinkedList(
						componentManager.getEntities(followTranslation.targetComponent));

				PhysicsComponent followerPhysics =
						followerEntity.getComponent(PhysicsComponent.class);
				Log.v(TAG, "process: x velocity = " + followerPhysics.getVelocity().x);
				Log.v(TAG, "process: y velocity = " + followerPhysics.getVelocity().y);
				Log.v(TAG, "process: targetEntities.size() = " + targetEntities.size());

				float[] targetLiveDistances = new float[targetEntities.size()];
				for (int i = 0; i < targetLiveDistances.length; i++)
				{
					targetLiveDistances[i] = Float.MAX_VALUE;
				}

				float closestTargetDistance = Float.MAX_VALUE;
				int closestTargetIndex = 0;
				int targetIndex = 0;
				for (Entity targetEntity : targetEntities)
				{
					float targetDistance = calculateDistance(followerEntity, targetEntity);
					targetLiveDistances[targetIndex] = targetDistance;

					if (targetDistance < closestTargetDistance)
					{
						closestTargetDistance = targetDistance;
						closestTargetIndex = targetIndex;
					}
					targetIndex++;
				}

				Entity closestTargetEntity = targetEntities.get(closestTargetIndex);
				updateFollowVelocity(followerPhysics.getVelocity(),
									 followerEntity.transform.position,
									 closestTargetEntity.transform.position, followTranslation);

				if (followerEntity.hasComponent(BitmapAnimator.class))
				{
					BitmapAnimator bitmapAnimator = followerEntity.getComponent(BitmapAnimator.class);
					if (followerPhysics.getVelocity().x < 0)
					{
						bitmapAnimator.triggerTransition(AnimationTransition.TURN_LEFT);
					}
					else
					{
						bitmapAnimator.triggerTransition(AnimationTransition.TURN_RIGHT);
					}
				}

				Log.v(TAG, "process: x velocity after update = " + followerPhysics.getVelocity()
						.x);
				Log.v(TAG, "process: y velocity after update = " + followerPhysics.getVelocity()
						.y);
			}
		}

		private static void updateFollowVelocity(Velocity followerVelocity,
				Position followerPosition, Position targetPosition,
				FollowTranslation assaultTranslation)
		{
			float xDistance = targetPosition.x - followerPosition.x;
			float yDistance = targetPosition.y - followerPosition.y;

			float angleInRadian = Utils.getAngleInRadian(xDistance, yDistance);
			followerVelocity.x =
					assaultTranslation.speed * (float) Math.cos(angleInRadian) * scaleX();
			followerVelocity.y =
					-assaultTranslation.speed * (float) Math.sin(angleInRadian) * scaleY();
		}

		static void updateAssaultMotion(Entity followerEntity,
				AssaultTranslation assaultTranslation, long deltaTime)
		{
			if (followerEntity.hasComponent(PhysicsComponent.class))
			{
				//Need any ordering in list of target entities.
				List<Entity> targetEntities = new LinkedList(
						componentManager.getEntities(assaultTranslation.targetComponent));

				PhysicsComponent followerPhysics =
						followerEntity.getComponent(PhysicsComponent.class);
				Log.v(TAG, "process: x velocity = " + followerPhysics.getVelocity().x);
				Log.v(TAG, "process: y velocity = " + followerPhysics.getVelocity().y);
				Log.v(TAG, "process: targetEntities.size() = " + targetEntities.size());

				float[] targetLiveDistances = new float[targetEntities.size()];
				for (int i = 0; i < targetLiveDistances.length; i++)
				{
					targetLiveDistances[i] = Float.MAX_VALUE;
				}

				float closestTargetDistance = Float.MAX_VALUE;
				int closestTargetIndex = 0;
				int targetIndex = 0;
				for (Entity targetEntity : targetEntities)
				{
					float targetDistance = calculateDistance(followerEntity, targetEntity);
					targetLiveDistances[targetIndex] = targetDistance;

					if (targetDistance < closestTargetDistance)
					{
						closestTargetDistance = targetDistance;
						closestTargetIndex = targetIndex;
					}
					targetIndex++;
				}

				Entity closestTargetEntity = targetEntities.get(closestTargetIndex);

				if (Math.abs((followerEntity.transform.position.y -
						closestTargetEntity.transform.position.y)) <
						assaultTranslation.assaultActivationDistance)
				{
					if (!assaultTranslation.waitStarted)
					{
						assaultTranslation.activationTime = java.lang.System.currentTimeMillis();
						assaultTranslation.waitStarted = true;
					}
				}

				updateAssaultVelocity(followerPhysics.getVelocity(),
									  followerEntity.transform.position,
									  closestTargetEntity.transform.position, assaultTranslation);

				if (followerEntity.hasComponent(BitmapAnimator.class))
				{
					BitmapAnimator bitmapAnimator = followerEntity.getComponent(BitmapAnimator.class);
					if (followerPhysics.getVelocity().x < 0)
					{
						if (assaultTranslation.waitStarted &&
								assaultTranslation.isWaitToAssaultOver())
						{
							bitmapAnimator.triggerTransition(AnimationTransition.ASSAULT_LEFT);
						}
						else if (assaultTranslation.waitStarted)
						{
							bitmapAnimator.triggerTransition(AnimationTransition.INVOKE_ASSUALT_LEFT);
						}
						else
						{
							bitmapAnimator.triggerTransition(AnimationTransition.TURN_LEFT);
						}
					}
					else
					{
						if (assaultTranslation.waitStarted &&
								assaultTranslation.isWaitToAssaultOver())
						{
							bitmapAnimator.triggerTransition(AnimationTransition.ASSAULT_RIGHT);
						}
						else if (assaultTranslation.waitStarted)
						{
							bitmapAnimator.triggerTransition(AnimationTransition.INVOKE_ASSUALT_RIGHT);
						}
						else
						{
							bitmapAnimator.triggerTransition(AnimationTransition.TURN_RIGHT);
						}
					}
				}

				Log.v(TAG, "process: x velocity after update = " + followerPhysics.getVelocity()
						.x);
				Log.v(TAG, "process: y velocity after update = " + followerPhysics.getVelocity()
						.y);
			}
		}

		private static void updateAssaultVelocity(Velocity followerVelocity,
				Position followerPosition, Position targetPosition,
				AssaultTranslation assaultTranslation)
		{
			float xDistance = targetPosition.x - followerPosition.x;
			float yDistance = targetPosition.y - followerPosition.y;
			float angleInRadian = Utils.getAngleInRadian(xDistance, yDistance);

			if (assaultTranslation.isWaitToAssaultOver())
			{
				if (followerVelocity.y == 0)
				{
					if (assaultTranslation.assaultAngle == null)
					{
						assaultTranslation.assaultAngle = angleInRadian;
					}

					followerVelocity.y =
							-assaultTranslation.assaultFactor * assaultTranslation.speed *
									(float) Math.sin(assaultTranslation.assaultAngle) * scaleY();

					followerVelocity.x =
							assaultTranslation.assaultFactor * assaultTranslation.speed *
									(float) Math.cos(assaultTranslation.assaultAngle) * scaleX();
				}
			}
			else
			{
				followerVelocity.x =
						assaultTranslation.speed * (float) Math.cos(angleInRadian) * scaleX();
			}
		}

		static void debugLog()
		{
			if (Settings.DEBUG_MODE)
			{
				long currentTime = System.currentTimeMillis();
				Log.v(TAG, "Frames since last reverse: " + frames);
				frames = 0;
				Log.v(TAG, "Time since last reverse: " + (currentTime - lastReverseTime));
				lastReverseTime = currentTime;
				Log.v(TAG, "Delta Time: " + Time.DELTA_TIME);
			}
		}
	}
}
