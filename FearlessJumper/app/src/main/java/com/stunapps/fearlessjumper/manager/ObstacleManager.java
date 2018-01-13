package com.stunapps.fearlessjumper.manager;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.Prefabs;

import java.util.Set;

/**
 * Created by sunny.s on 13/01/18.
 */

public class ObstacleManager implements Manager
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EntityTransformCalculator calculator;

    private Transform.Position spawnPosition;

    @Inject
    public ObstacleManager(EntityManager entityManager, ComponentManager componentManager, EntityTransformCalculator calculator)
    {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.calculator = calculator;
    }

    @Override
    public void manage()
    {
        Prefab platformPrefab = Prefabs.PLATFORM.get();
        int width = calculator.getWidth(platformPrefab);
        spawnPosition = new Transform.Position((float) Math.random() * (Constants.SCREEN_WIDTH -
                width), -400);
        /**
         * Should we use components or tags?
         * Using tags means that we will have to create another manager similar to ComponentManager
         */
        Set<Entity> players = componentManager.getEntities(PlayerComponent.class);
        for (Entity player : players)
        {
            if (player.transform.position.y < Constants.SCREEN_HEIGHT / 2)
            {
                entityManager.instantiate(platformPrefab, Transform.builder().position
                        (spawnPosition).build());
            }
        }
    }
}
