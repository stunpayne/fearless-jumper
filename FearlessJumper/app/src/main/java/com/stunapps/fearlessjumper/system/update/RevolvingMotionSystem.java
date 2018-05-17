package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.RevolvingTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Velocity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by anand.verma on 17/05/18 7:26 PM.
 */

public class RevolvingMotionSystem implements UpdateSystem
{
	private final ComponentManager componentManager;

	@Inject
	public RevolvingMotionSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{

		Set<Entity> entitySet = componentManager.getEntities(RevolvingTranslation.class);
		for (Entity entity : entitySet)
		{
			if (entity.hasComponent(PhysicsComponent.class))
			{
				PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
				RevolvingTranslation revolvingTranslation =
						entity.getComponent(RevolvingTranslation.class);

				float omega = revolvingTranslation.getOmega(); // theta/time
				float radius = revolvingTranslation.getRadius();

				float radialAcceleration = omega * omega * radius;

				Velocity velocity = physicsComponent.getVelocity();
				float XVelocity = velocity.getX();
				float YVelocity = velocity.getY();

				float theta = revolvingTranslation.getTheta();

//				YVelocity = radialAcceleration * (float) Math.sin(Math.toRadians(theta)) ;
				XVelocity = radialAcceleration * (float) Math.cos(Math.toRadians(theta)) ;

				Log.d("RevolvingMotionSystem",
					  "process: " + Math.sqrt(YVelocity * YVelocity + XVelocity * XVelocity));

				theta += omega;

				velocity.setX(XVelocity);
				velocity.setY(YVelocity);
				revolvingTranslation.setTheta(theta);
			}
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return 0;
	}

	@Override
	public void reset()
	{

	}
}
