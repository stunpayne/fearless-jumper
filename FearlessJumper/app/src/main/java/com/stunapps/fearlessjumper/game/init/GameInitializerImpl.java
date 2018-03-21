package com.stunapps.fearlessjumper.game.init;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.transform.Transform.Rotation;
import com.stunapps.fearlessjumper.component.transform.Transform.Scale;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.prefab.GroundedDragonPrefabSet;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;
import com.stunapps.fearlessjumper.prefab.PrefabSet;

import java.util.List;

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
		Transform transform = new Transform(new Position(x, y));
		List<Entity> instantiate = entityManager.instantiate(PrefabRef.PLAYER.get(), transform);
		Entity player = instantiate.get(0);

		try
		{
			//initPlatforms();
			initBoundaries(player);
			//initEnemies();
			Prefab unfriendlyPlatform = PrefabRef.UNFRIENDLY_PLATFORM.get();

			x = Device.SCREEN_WIDTH / 4;
			y = Device.SCREEN_HEIGHT / 4 + 200;
			Transform transform1 = new Transform(new Position(x, y));
			Transform transform3 = new Transform(new Position(x - 100, y));

			entityManager.instantiate(unfriendlyPlatform, transform1);
		}

		catch (CloneNotSupportedException e)
		{
			Log.e("INIT_ERROR", "Error while initialising game.");
			e.printStackTrace();
		} finally{

		}

		Cameras.setMainCamera(Cameras.createFollowCamera(new Position(0, 0), player, true,
				false, 0,
				65 * Device.SCREEN_HEIGHT / 100));

		initialised = true;
	}

	private void initPlatforms() throws CloneNotSupportedException
	{
		Prefab platformPrefab = PrefabRef.PLATFORM.get();

		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 200;
		Transform transform1 = new Transform(new Position(x, y));
		Transform transform3 = new Transform(new Position(x - 100, y));

		entityManager.instantiate(platformPrefab, transform1);
	}

	private void initBoundaries(Entity target) throws CloneNotSupportedException
	{
		Transform landTransform = new Transform(new Position(0, Device.SCREEN_HEIGHT));
		entityManager.instantiate(PrefabRef.LAND.get(), landTransform);
	}

	private void initEnemies()
	{
		entityManager.instantiate(PrefabRef.FOLLOWING_DRAGON.get(), new Transform(
				new Position(Device.SCREEN_WIDTH / 8, Device.SCREEN_HEIGHT / 2 - 150)));
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
