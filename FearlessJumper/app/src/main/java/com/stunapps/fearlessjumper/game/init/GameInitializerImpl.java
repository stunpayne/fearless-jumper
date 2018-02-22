package com.stunapps.fearlessjumper.game.init;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.OrientationData;
import com.stunapps.fearlessjumper.display.Cameras;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.Prefabs;

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
    public GameInitializerImpl(EntityManager entityManager, ComponentManager componentManager)
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
        final Entity player = entityManager.instantiate(Prefabs.PLAYER.get());

        try
        {
            initPlatforms();
            initBoundaries(player);
        }
        catch (CloneNotSupportedException e)
        {
            Log.e("INIT_ERROR", "Error while initialising game.");
            e.printStackTrace();
        }
        
        //  Initialise enemies
        entityManager.instantiate(Prefabs.DRAGON.get());

        Cameras.setMainCamera(Cameras.createFollowCamera(new Position(0, 0), player, true, false, 0,
                65 * Device.SCREEN_HEIGHT / 100));

        initialised = true;
    }

    private void initPlatforms() throws CloneNotSupportedException
    {
        Prefab platformPrefab = Prefabs.PLATFORM.get();

        int x = Device.SCREEN_WIDTH / 4;
        int y = Device.SCREEN_HEIGHT / 2 + 200;
        Transform transform1 = new Transform(new Position(x, y));
        Transform transform2 = new Transform(new Position(x, Device.SCREEN_HEIGHT / 2 - 500));
        Transform transform3 = new Transform(new Position(3 * Device.SCREEN_WIDTH / 4, 300));

        entityManager.instantiate(platformPrefab, transform1);
        entityManager.instantiate(platformPrefab, transform2);
        entityManager.instantiate(platformPrefab, transform3);
    }

    private void initBoundaries(Entity target) throws CloneNotSupportedException
    {
        Transform landTransform = new Transform(new Position(0, Device.SCREEN_HEIGHT), null,
												null);
        entityManager.instantiate(Prefabs.LAND.get(), landTransform);
    }
}
