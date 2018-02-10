package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.MoveDownComponent;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.PlatformComponent;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.Constants;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.Prefabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by sunny.s on 13/01/18.
 */

@Singleton
public class ObstacleGenerationSystem implements UpdateSystem
{
    private final EntityManager entityManager;
    private final ComponentManager componentManager;
    private final EntityTransformCalculator calculator;

    private float speed = 1.0f;
    private static long lastProcessTime = 0;
    private ArrayList<Entity> activePlatforms = new ArrayList<>();
    private Shuffler<Prefab> obstacleShuffler;

    private static Prefab platformPrefab;
    private static float platformWidth;
    private static float platformHeight;
    private static float NEW_OBSTACLE_OFFSET = -600f;

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
        initShuffler();
        if (lastProcessTime == 0)
        {
            lastProcessTime = System.currentTimeMillis();
            initialisePlatformsList();

            platformPrefab = Prefabs.PLATFORM.get();
            platformWidth = calculator.getWidth(platformPrefab);
            platformHeight = calculator.getHeight(platformPrefab);
        }

        Entity player = componentManager.getEntity(PlayerComponent.class);
        Set<Entity> movables = componentManager.getEntities(MoveDownComponent.class);

        deletePlatformsOutOfScreen(movables);
        createNewObstacleIfPossible(player.transform.position);
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
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
        for (Entity platform : platforms)
        {
            if (RenderSystem.getRenderRect(platform).top > Constants.SCREEN_HEIGHT)
                entityManager.deleteEntity(platform);
        }
    }

    private void createNewObstacleIfPossible(Position playerPosition)
    {
        Entity topPlatform = activePlatforms.get(activePlatforms.size() - 1);
        if ((Math.abs(
                topPlatform.transform.position.y - playerPosition.y) <= Constants.SCREEN_HEIGHT))
        {
            Prefab spawnPrefab = obstacleShuffler.shuffle();
            Position spawnPosition =
                    new Position((float) Math.random() * (Constants.SCREEN_WIDTH - platformWidth),
                            topPlatform.transform.position.y + NEW_OBSTACLE_OFFSET);
            try
            {
                activePlatforms.add(
                        entityManager.instantiate(spawnPrefab, new Transform(spawnPosition)));
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            Log.i("NEW_OBSTACLE", "Created new platform at: " + spawnPosition);
        }
    }

    private float yVelocity(Entity entity)
    {
        PhysicsComponent physicsComponent =
                (PhysicsComponent) entity.getComponent(PhysicsComponent.class);
        if (physicsComponent == null) return 0;
        else return physicsComponent.velocity.y;
    }

    private void initShuffler()
    {
        if (obstacleShuffler == null)
        {
            obstacleShuffler =
                    new Shuffler.Builder<Prefab>()
                            .returnItem(Prefabs.PLATFORM.get()).atLessThan(0.5f)
                            .returnItem(Prefabs.DRAGON.get()).atLessThan(1f)
                            .build();
        }
    }
}
