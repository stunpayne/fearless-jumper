package com.stunapps.fearlessjumper.game.init;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.Prefabs;
import com.stunapps.fearlessjumper.system.update.ObstacleGenerationSystem;

/**
 * Created by sunny.s on 12/01/18.
 */

@Singleton
public class GameInitializerImpl implements GameInitializer
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;

    private OrientationData orientationData;
    private boolean initialised = false;

    @Inject
    public GameInitializerImpl(EntityManager entityManager, ComponentManager componentManager,
                               ObstacleGenerationSystem obstacleSystem)
    {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
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
        entityManager.instantiate(Prefabs.PLAYER.get());

        initPlatforms();
        initBoundaries();

        entityManager.instantiate(Prefabs.MIDSCREEN_COLLIDER.get());

        //  Initialise enemies

        initialised = true;
    }

    private void initPlatforms()
    {
        Prefab platformPrefab = Prefabs.PLATFORM.get();

        int x = Constants.SCREEN_WIDTH / 4;
        int y = Constants.SCREEN_HEIGHT / 2+ 200;
        Transform transform1 = new Transform(new Position(x, y));
        Transform transform2 = new Transform(new Position(x, Constants.SCREEN_HEIGHT/2 - 500));
        Transform transform3 = new Transform(new Position(3 * Constants.SCREEN_WIDTH/4,
                400));

        entityManager.instantiate(platformPrefab, transform1);
        entityManager.instantiate(platformPrefab, transform2);
        entityManager.instantiate(platformPrefab, transform3);
    }

    private void initBoundaries()
    {
        Transform leftBoundaryTransform = new Transform(new Position(-10, Constants
                .SCREEN_HEIGHT / 2), null, null);
        entityManager.instantiate(Prefabs.BOUNDARY.get(), leftBoundaryTransform);

        Transform rightBoundaryTransform = new Transform(
                new Position(Constants.SCREEN_WIDTH + 10, Constants
                        .SCREEN_HEIGHT / 2), null, null);
        entityManager.instantiate(Prefabs.BOUNDARY.get(), rightBoundaryTransform);

        Transform landTransform = new Transform(
                new Position(0, Constants
                        .SCREEN_HEIGHT), null, null);
        entityManager.instantiate(Prefabs.LAND.get());
        entityManager.instantiate(Prefabs.LAND.get(), landTransform);
    }
}
