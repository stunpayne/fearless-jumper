package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Bitmaps;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.manager.CollisionLayerManager;
import com.stunapps.fearlessjumper.prefab.PrefabRef;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by anand.verma on 02/03/18.
 */

public class CollisionLayerTest
{
	EntityManager entityManager = new EntityManager(new GameComponentManager());

	@Before
	public void initialise(){
		Bitmaps.initialise();
	}

	@Test
	public void testCollisionLayer(){
		CollisionLayerManager collisionLayerManager = DI.di().getInstance(CollisionLayerManager
																				.class);

		collisionLayerManager.unsetCollisionLayerMask(CollisionLayer.PLAYER, CollisionLayer.ENEMY);


		Entity dragonEntity = entityManager.instantiate(PrefabRef.FLYING_DRAGON.get(), Transform.ORIGIN);
		Entity playerEntity = entityManager.instantiate(PrefabRef.PLAYER.get(), Transform.ORIGIN);
		Assert.assertTrue(collisionLayerManager
								  .isCollisionMaskSet(dragonEntity.getComponent(Collider.class),
													  playerEntity.getComponent(Collider.class)));
	}
}
