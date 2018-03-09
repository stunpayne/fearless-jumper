package com.stunapps.fearlessjumper.game.init;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.prefab.GroundedDragonPrefabSet;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabSet;
import com.stunapps.fearlessjumper.prefab.Prefabs;

/**
 * Created by sunny.s on 12/01/18.
 */

@Singleton
public class GameInitializerImpl implements GameInitializer
{
	private final EntityManager entityManager;

	private OrientationData orientationData;
	private boolean initialised = false;

	@Inject
	public GameInitializerImpl(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

	@Override
	public boolean isInitialized()
	{
		return initialised;
	}

	@Override
	public void initialize()
	{
		Log.d("INIT", "Initialising game");
		int x = 4 * Device.SCREEN_WIDTH / 5;
		int y = Device.SCREEN_HEIGHT - 150;
		Transform transform =
				new Transform(new Position(x, y), new Transform.Rotation(), new Transform.Scale());
		final Entity player = entityManager.instantiate(Prefabs.PLAYER.get(), transform);

		try
		{
			initPlatforms();
			initBoundaries(player);
			initEnemies();
		}
		catch (CloneNotSupportedException e)
		{
			Log.e("INIT_ERROR", "Error while initialising game.");
			e.printStackTrace();
		}

		Cameras.setMainCamera(Cameras.createFollowCamera(new Position(0, 0), player, true,
				false, 0,
				65 * Device.SCREEN_HEIGHT / 100));

		initialised = true;
	}

	private void initPlatforms() throws CloneNotSupportedException
	{
		Prefab platformPrefab = Prefabs.PLATFORM.get();

		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 200;
		Transform transform1 = new Transform(new Position(x, y));
		Transform transform3 = new Transform(new Position(5 * Device.SCREEN_WIDTH / 8, 300));

		entityManager.instantiate(platformPrefab, transform1);
	}

	private void initBoundaries(Entity target) throws CloneNotSupportedException
	{
		Transform landTransform = new Transform(new Position(0, Device.SCREEN_HEIGHT), null, null);
		entityManager.instantiate(Prefabs.LAND.get(), landTransform);
	}

	private void initEnemies()
	{
		//  Initialise enemies
		entityManager.instantiate(Prefabs.DRAGON.get());

		PrefabSet prefabSet = new GroundedDragonPrefabSet();
		Transform transform2 = new Transform(
				new Position(Device.SCREEN_WIDTH / 8, Device.SCREEN_HEIGHT / 2 - 200));
		entityManager.instantiate(prefabSet, transform2);
	}

	@Override
	public void destroy()
	{
		if (entityManager != null)
		{
			entityManager.deleteEntities();
			initialised = false;
		}
	}
}
