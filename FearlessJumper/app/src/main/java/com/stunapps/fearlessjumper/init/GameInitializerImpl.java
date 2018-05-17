package com.stunapps.fearlessjumper.init;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.instantiation.prefab.Prefab;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;

import java.util.List;

/**
 * Created by sunny.s on 12/01/18.
 */

@Singleton
public class GameInitializerImpl implements GameInitializer
{
	private final EntityManager entityManager;

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
		Entity player = initPlayer();
		initCameras(player);
		initBoundaries(player);
		initEnemies();

		//			initPlatforms();

		initialised = true;
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

	private void initCameras(Entity player)
	{
		Cameras.setMainCamera(Cameras.createFollowCamera(new Position(0, 0), player, true,
				false, 0,
				65 * Device.SCREEN_HEIGHT / 100));
	}

	private Entity initPlayer()
	{
		int x = 4 * Device.SCREEN_WIDTH / 5;
		int y = Device.SCREEN_HEIGHT - 150;
		Transform transform = new Transform(new Position(x, y));
		List<Entity> instantiate = entityManager.instantiate(PrefabRef.PLAYER.get(), transform);
		return instantiate.get(0);
	}

	private void initPlatforms()
	{
		Prefab platformPrefab = PrefabRef.PLATFORM.get();

		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2 + 200;
		Transform transform1 = new Transform(new Position(x, y));

		entityManager.instantiate(platformPrefab, transform1);
	}

	private void initBoundaries(Entity target)
	{
		Transform landTransform = new Transform(new Position(0, Device.SCREEN_HEIGHT));
		entityManager.instantiate(PrefabRef.LAND.get(), landTransform);
	}

	private void initEnemies()
	{
		//	Rotating Wheel
		int x = Device.SCREEN_WIDTH / 4;
		int y = Device.SCREEN_HEIGHT / 2;
		Transform transform1 = new Transform(new Position(x, y));
		entityManager.instantiate(PrefabRef.ROTATING_WHEEL.get(), transform1);
	}
}
