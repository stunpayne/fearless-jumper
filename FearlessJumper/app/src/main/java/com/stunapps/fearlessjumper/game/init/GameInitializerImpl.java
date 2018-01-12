package com.stunapps.fearlessjumper.game.init;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.prefab.Prefabs;

/**
 * Created by sunny.s on 12/01/18.
 */

public class GameInitializerImpl implements GameInitializer
{
    private final EntityManager entityManager;
    private final GameComponentManager gameComponentManager;

    @Inject
    public GameInitializerImpl(EntityManager entityManager, GameComponentManager gameComponentManager)
    {
        this.entityManager = entityManager;
        this.gameComponentManager = gameComponentManager;
    }

    @Override
    public void initialise()
    {
        Entity player = entityManager.instantiate(Prefabs.PLAYER.get(), , , , );
    }
}
