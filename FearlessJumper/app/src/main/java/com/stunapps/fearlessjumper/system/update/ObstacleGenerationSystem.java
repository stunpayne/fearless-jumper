package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.DragonComponent;
import com.stunapps.fearlessjumper.component.specific.ObstacleComponent;
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
    private ArrayList<Entity> activeObstacles = new ArrayList<>();
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
        if (lastProcessTime == 0)
        {
            lastProcessTime = System.currentTimeMillis();
            initShuffler();
            initialisePlatformsList();

            platformPrefab = Prefabs.PLATFORM.get();
            platformWidth = calculator.getWidth(platformPrefab);
            platformHeight = calculator.getHeight(platformPrefab);
        }

        Entity player = componentManager.getEntity(PlayerComponent.class);
        Set<Entity> obstacles = componentManager.getEntities(ObstacleComponent.class);

        deleteObstaclesOutOfScreen(obstacles);
        createNewObstacleIfPossible(player.transform.position);
    }

    @Override
    public long getLastProcessTime()
    {
        return lastProcessTime;
    }

    private void deleteObstaclesOutOfScreen(Set<Entity> obstacles)
    {
        for (Entity obstacle : obstacles)
        {
            if (RenderSystem.getRenderRect(obstacle).top > Constants.SCREEN_HEIGHT)
                entityManager.deleteEntity(obstacle);
        }
    }

    private void createNewObstacleIfPossible(Position playerPosition)
    {
        Entity topObstacle = activeObstacles.get(activeObstacles.size() - 1);
        if ((Math.abs(
                topObstacle.transform.position.y - playerPosition.y) <= Constants.SCREEN_HEIGHT))
        {
            Log.d("NEW_OBSTACLE",
                    "Top Obstacle ID: " + topObstacle.getId() + " Position: " + "" + topObstacle
                            .transform.position + " Type: " + (topObstacle.hasComponent(
                            DragonComponent.class) ? "Dragon" : "Platform"));
            Prefab spawnPrefab = obstacleShuffler.shuffle();
            Position spawnPosition =
                    new Position((float) Math.random() * (Constants.SCREEN_WIDTH - platformWidth),
                            topObstacle.transform.position.y + NEW_OBSTACLE_OFFSET);
            try
            {
                Entity newObstacle =
                        entityManager.instantiate(spawnPrefab, new Transform(spawnPosition));
                activeObstacles.add(newObstacle);
                Log.i("NEW_OBSTACLE",
                        "Created new obstacle with id: " + newObstacle.getId() + " at: " +
                                spawnPosition + " type: " + (newObstacle.hasComponent(
                                DragonComponent.class) ? "Dragon" : "Platform"));
                Log.d("NEW_OBSTACLE", "Actual position: " + newObstacle.transform.position);
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void initShuffler()
    {
        if (obstacleShuffler == null)
        {
            obstacleShuffler =
                    new Shuffler.Builder<Prefab>()
                            .returnItem(Prefabs.PLATFORM.get()).withWeight(0.5f)
                            .returnItem(Prefabs.DRAGON.get()).withWeight(1f)
                            .build();
        }
    }

    private void initialisePlatformsList()
    {
        Set<Entity> entities = componentManager.getEntities(ObstacleComponent.class);
        activeObstacles.addAll(entities);

        Collections.sort(activeObstacles, new Comparator<Entity>()
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
}
