package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.FollowTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.Set;

/**
 * Created by sunny.s on 14/01/18.
 */

public class TransformUpdateSystem implements UpdateSystem
{
	private final ComponentManager componentManager;
	private static long lastProcessTime = System.nanoTime();

	@Inject
	public TransformUpdateSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();
		Set<Entity> movables = componentManager.getEntities(PhysicsComponent.class);

		for (Entity movable : movables)
		{
			PhysicsComponent physicsComponent = movable.getComponent(PhysicsComponent.class);
			Renderable renderable = movable.getComponent(Renderable.class);
			if (movable.hasComponent(PlayerComponent.class))
				movePlayerTransform(movable.transform, physicsComponent.getVelocity(), renderable);
			else moveTransform(movable.transform, physicsComponent.getVelocity());

			if (movable.hasComponent(FollowTranslation.class))
			{
				Log.d("ConsciousEnemySystem",
						"transform.position.x: " + movable.transform.position.x);
				Log.d("ConsciousEnemySystem",
						"transform.position.y: " + movable.transform.position.y);
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

	private void movePlayerTransform(Transform transform, Velocity velocity, Renderable renderable)
	{
		moveTransform(transform, velocity);

		if (transform.position.x >= Device.SCREEN_WIDTH - renderable.width)
		{
			transform.position.x = Device.SCREEN_WIDTH - renderable.width;
			velocity.x = 0;
		}
		else if (transform.position.x <= 0)
		{
			transform.position.x = 0;
			velocity.x = 0;
		}

		if (Float.isNaN(transform.position.x))
		{
			Log.d("BAD_CASE", "Position x is NaN");
		}
	}

	private void moveTransform(Transform transform, Velocity velocity)
	{
		transform.position.x += (velocity.x * Time.DELTA_TIME);
		transform.position.y += (velocity.y * Time.DELTA_TIME);
	}
}
