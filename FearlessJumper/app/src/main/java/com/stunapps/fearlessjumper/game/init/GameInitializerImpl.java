package com.stunapps.fearlessjumper.game.init;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.prefab.Prefabs;

/**
 * Created by sunny.s on 12/01/18.
 */

@Singleton
public class GameInitializerImpl implements GameInitializer
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
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
        entityManager.instantiate(Prefabs.PLAYER.get());
        entityManager.instantiate(Prefabs.PLATFORM.get());

        initialised = true;
    }
}
