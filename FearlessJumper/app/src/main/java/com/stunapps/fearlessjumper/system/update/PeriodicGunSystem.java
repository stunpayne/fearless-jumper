package com.stunapps.fearlessjumper.system.update;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PeriodicGun;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.instantiation.prefab.Prefab;

import java.util.Set;

/**
 * Created by sunny.s on 10/03/18.
 */

@Singleton
public class PeriodicGunSystem implements UpdateSystem
{
	private final ComponentManager componentManager;
	private final EntityManager entityManager;

	private static long lastProcessTime = 0;

	@Inject
	public PeriodicGunSystem(ComponentManager componentManager, EntityManager entityManager)
	{
		this.componentManager = componentManager;
		this.entityManager = entityManager;
	}

	@Override
	public void process(long deltaTime)
	{
		Set<Entity> gunners = componentManager.getEntities(PeriodicGun.class);

		for (Entity gunner : gunners)
		{
			PeriodicGun gun = gunner.getComponent(PeriodicGun.class);
			if (gun.canShoot())
			{
				int xOffset = 0;
				int yOffset = 0;
				if (gunner.hasComponent(ShapeRenderable.class))
				{
					ShapeRenderable renderable = gunner.getComponent(ShapeRenderable.class);
					xOffset = (int)renderable.getWidth();
					yOffset = (int)renderable.getHeight() / 2;
				}

				int spawnX = (int) gunner.transform.position.x + xOffset;
				int spawnY = (int) gunner.transform.position.y + yOffset;

				Prefab bulletPrefab = gun.shoot();
				entityManager
						.instantiate(bulletPrefab, new Transform(new Position(spawnX, spawnY)));
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

	}
}
