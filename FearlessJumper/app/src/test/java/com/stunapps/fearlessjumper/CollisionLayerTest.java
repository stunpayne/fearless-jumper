package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.manager.CollisionLayerManager;
import com.stunapps.fearlessjumper.prefab.PlatformPrefab;

import org.junit.Test;

/**
 * Created by anand.verma on 02/03/18.
 */

public class CollisionLayerTest
{
	@Test
	public void testCollisionLayer(){
		CollisionLayerManager collisionLayerManager = DI.di().getInstance(CollisionLayerManager
																				.class);
		//System.out.println(collisionLayerManager);
	}
}
