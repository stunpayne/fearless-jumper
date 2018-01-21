package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.MoveDownComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.BlockPlayerComponent;
import com.stunapps.fearlessjumper.component.specific.PlatformComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.helper.Constants.Game;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.Prefabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import static com.stunapps.fearlessjumper.system.update.CollisionSystem.isCollidingV2;

/**
 * Created by sunny.s on 13/01/18.
 */

public class ObstacleGenerationSystem implements UpdateSystem
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EntityTransformCalculator calculator;

    private Position spawnPosition;
    private float speed = 1.0f;
    private static long lastProcessTime = 0;
    private ArrayList<Entity> activePlatforms = new ArrayList<>();

    @Inject
    public ObstacleGenerationSystem(EntityManager entityManager, ComponentManager componentManager,
                                    EntityTransformCalculator calculator)
    {
        this.entityManager = entityManager;
        this.componentManager = componentManager;
        this.calculator = calculator;
    }

    @Override
    public void process(long deltaTime)
    {
        if (lastProcessTime == 0)
        {
            lastProcessTime = System.currentTimeMillis();
            initialisePlatformsList();
        }
        Prefab platformPrefab = Prefabs.PLATFORM.get();
        float width = calculator.getWidth(platformPrefab);
        spawnPosition = new Position((float) Math.random() * (Constants.SCREEN_WIDTH -
                width), 0);
        /**
         * Should we use components or tags?
         * Using tags means that we will have to create another manager similar to ComponentManager
         */
        Set<Entity> players = componentManager.getEntities(PlayerComponent.class);
        Set<Entity> playerBlockers = componentManager.getEntities(BlockPlayerComponent.class);
        Set<Entity> movables = componentManager.getEntities(MoveDownComponent.class);

        for (Entity player : players)
        {
            float playerVelocityY = yVelocity(player);
            for (Entity playerBlocker : playerBlockers)
            {
                if (isCollidingV2(player, playerBlocker))
                {
                    Log.d("OBSTACLES", "Moving activePlatforms down");
                    //  TODO: Check if there's a better way to do this. Sometimes the player
                    // moves below the collider
                    player.transform.position.y -= playerVelocityY;
                    translateAllPlatformsDown(movables, playerVelocityY);
                }
            }
        }

        deletePlatformsOutOfScreen(movables);
        createNewPlatformIfPossible(platformPrefab);
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }

    private float yVelocity(Entity entity)
    {
        PhysicsComponent physicsComponent = (PhysicsComponent) entity.getComponent(
                PhysicsComponent.class);
        if (physicsComponent == null)
            return 0;
        else
            return physicsComponent.velocity.y;
    }

    private void initialisePlatformsList()
    {
        Set<Entity> entities = componentManager.getEntities(PlatformComponent.class);
        activePlatforms.addAll(entities);

        Collections.sort(activePlatforms, new Comparator<Entity>()
        {
            @Override
            public int compare(Entity o1, Entity o2)
            {
                //  Comparator is reverse because we want the top most platform at the last of the
                //  list. Top most platform will have the least value of y.
                return (int) (o2.transform.position.y - o1.transform.position.y);
            }
        });
    }

    private void deletePlatformsOutOfScreen(Set<Entity> platforms)
    {
        //  TODO: Delete all platforms out of screen, not just the first one
        for (Entity platform : platforms)
        {
            if (platform.transform.position.y > Constants.SCREEN_HEIGHT)
                entityManager.deleteEntity(platform);
        }
    }

    private void createNewPlatformIfPossible(Prefab platformPrefab)
    {
        if (activePlatforms.get(activePlatforms.size() - 1).transform.position.y >= Constants
                .SCREEN_HEIGHT / 2)
        {
            activePlatforms.add(entityManager.instantiate(platformPrefab, new Transform
                    (spawnPosition)));
        }
    }

    private void translateAllPlatformsDown(Set<Entity> platforms, float playerVelocityY)
    {
        for (Entity platform : platforms)
        {
            platform.transform.position.y -= playerVelocityY;
        }
    }
}
