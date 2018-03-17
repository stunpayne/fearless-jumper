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
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment.Device;
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
				Log.d(TAG, "process: x velocity = " + followerPhysics.getVelocity().x);
				Log.d(TAG, "process: y velocity = " + followerPhysics.getVelocity().y);
				Log.d(TAG, "process: targetEntities.size() = " + targetEntities.size());

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

				if (followerEntity.hasComponent(Animator.class))
				{
					Animator animator = followerEntity.getComponent(Animator.class);
					if (followerPhysics.getVelocity().x < 0)
					{
						animator.triggerTransition(AnimationTransition.TURN_LEFT);
					}
					else
					{
						animator.triggerTransition(AnimationTransition.TURN_RIGHT);
					}
				}

				Log.d(TAG, "process: x velocity after update = " + followerPhysics.getVelocity()
						.x);
				Log.d(TAG, "process: y velocity after update = " + followerPhysics.getVelocity()
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
			followerVelocity.x = assaultTranslation.speed * (float) Math.cos(angleInRadian);
			followerVelocity.y = -assaultTranslation.speed * (float) Math.sin(angleInRadian);
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
				Log.d(TAG, "process: x velocity = " + followerPhysics.getVelocity().x);
				Log.d(TAG, "process: y velocity = " + followerPhysics.getVelocity().y);
				Log.d(TAG, "process: targetEntities.size() = " + targetEntities.size());

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

				if (followerEntity.hasComponent(Animator.class))
				{
					Animator animator = followerEntity.getComponent(Animator.class);
					if (followerPhysics.getVelocity().x < 0)
					{
						if (assaultTranslation.waitStarted &&
								assaultTranslation.isWaitToAssaultOver())
						{
							animator.triggerTransition(AnimationTransition.ASSAULT_LEFT);
						}
						else if (assaultTranslation.waitStarted)
						{
							animator.triggerTransition(AnimationTransition.INVOKE_ASSUALT_LEFT);
						}
						else
						{
							animator.triggerTransition(AnimationTransition.TURN_LEFT);
						}
					}
					else
					{
						if (assaultTranslation.waitStarted &&
								assaultTranslation.isWaitToAssaultOver())
						{
							animator.triggerTransition(AnimationTransition.ASSAULT_RIGHT);
						}
						else if (assaultTranslation.waitStarted)
						{
							animator.triggerTransition(AnimationTransition.INVOKE_ASSUALT_RIGHT);
						}
						else
						{
							animator.triggerTransition(AnimationTransition.TURN_RIGHT);
						}
					}
				}

				Log.d(TAG, "process: x velocity after update = " + followerPhysics.getVelocity()
						.x);
				Log.d(TAG, "process: y velocity after update = " + followerPhysics.getVelocity()
						.y);
			}
		}

		private static int getSign(float number)
		{
			if (number == 0)
			{
				return 1;
			}

			return (int) (number / number);
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
									(float) Math.sin(assaultTranslation.assaultAngle);

					followerVelocity.x =
							assaultTranslation.assaultFactor * assaultTranslation.speed *
									(float) Math.cos(assaultTranslation.assaultAngle);
				}
			}
			else
			{
				followerVelocity.x =
						assaultTranslation.speed * (float) Math.cos(angleInRadian);
			}
		}

		private static int getDirection(float axisVelocity)
		{
			if (axisVelocity == 0)
			{
				return 0;
			}
			float direction = axisVelocity / axisVelocity;
			return direction > 0 ? 1 : -1;
		}

		private static float updateDirection(float axisVelocity, float direction)
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

		static void debugLog()
		{
			if (Settings.DEBUG_MODE)
			{
				long currentTime = System.currentTimeMillis();
				Log.d(TAG, "Frames since last reverse: " + frames);
				frames = 0;
				Log.d(TAG, "Time since last reverse: " + (currentTime - lastReverseTime));
				lastReverseTime = currentTime;
				Log.d(TAG, "Delta Time: " + Time.DELTA_TIME);
			}
		}
	}
}
