package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.movement.RevolvingTranslation;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;

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



				float omega = 3.0f; // theta/time
				float radius = 5.0f;
				float radialAcceleration = omega * omega * radius;

				int frames = 360;

				float XVelocity = 0.0f;
				float YVelocity = 5.0f;

				float theta = 0.0f;

				List<Float> xVelocityList = new LinkedList<>();
				List<Float> yVelocityList = new LinkedList<>();

				for (int i = 0; i < frames; i++)
				{
					xVelocityList.add(XVelocity);
					yVelocityList.add(YVelocity);
					YVelocity = YVelocity +
							radialAcceleration * (float) Math.sin(Math.toRadians(theta));
					XVelocity = XVelocity +
							radialAcceleration * (float) Math.cos(Math.toRadians(theta));
					theta += omega;
					if (i >= 120)
					{
						System.out.println("x compare = " + (Math.round(xVelocityList.get(i)) ==
								Math.round(xVelocityList.get(i - 120))));
						System.out.println("y compare = " + (Math.round(yVelocityList.get(i)) ==
								Math.round(yVelocityList.get(i - 120))));
					}
				}
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
