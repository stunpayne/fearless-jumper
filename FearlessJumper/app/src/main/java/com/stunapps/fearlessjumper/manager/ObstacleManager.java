package com.stunapps.fearlessjumper.manager;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlatformComponent;
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
    private float speed = 1.0f;

    @Inject
    public ObstacleManager(EntityManager entityManager, ComponentManager componentManager,
                           EntityTransformCalculator calculator)
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
                width), 0);
        /**
         * Should we use components or tags?
         * Using tags means that we will have to create another manager similar to ComponentManager
         */
        Set<Entity> players = componentManager.getEntities(PlayerComponent.class);
        /*for (Entity player : players)
        {
            if (player.transform.position.y < Constants.SCREEN_HEIGHT / 2)
            {
                Log.d("NEW_PLATFORM", "Player transform: " + player.transform.position);
                entityManager.instantiate(platformPrefab, Transform.builder().position
                        (spawnPosition).build());
            }
        }*/

        Set<Entity> platforms = componentManager.getEntities(PlatformComponent.class);
        for (Entity platform : platforms)
        {
            if (platform.transform.position.y > Constants.SCREEN_HEIGHT)
                entityManager.deleteEntity(platform);
            else
                ;//platform.transform.position.y += speed;
        }
    }
}
