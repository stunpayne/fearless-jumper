package com.stunapps.fearlessjumper.system.update;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.Dragon;
import com.stunapps.fearlessjumper.component.specific.Obstacle;
import com.stunapps.fearlessjumper.component.specific.Pickup;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.game.Environment.Device;
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
public class GenerationSystem implements UpdateSystem
{
	private final EntityManager entityManager;
	private final ComponentManager componentManager;
	private final EntityTransformCalculator calculator;
	private Shuffler<Prefab> obstacleShuffler;

	private ArrayList<Entity> activeObstacles = new ArrayList<>();

	private static float platformWidth;

	private static long lastProcessTime = 0;
	private static float NEW_OBSTACLE_OFFSET = -600f;
	private static float MIN_ALLOWED_DISTANCE_FROM_PLAYER;

	@Inject
	public GenerationSystem(EntityManager entityManager, ComponentManager componentManager,
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
			MIN_ALLOWED_DISTANCE_FROM_PLAYER = Device.SCREEN_HEIGHT;
			initShuffler();
			initActiveObstacles();

			platformWidth = calculator.getWidth(Prefabs.PLATFORM.get());
		}

		Entity player = componentManager.getEntity(PlayerComponent.class);
		Set<Entity> spawnables = componentManager.getEntities(Obstacle.class);
		spawnables.addAll((componentManager.getEntities(Pickup.class)));

		deleteSpawnablesOutOfScreen(spawnables);
		createNewSpawnableIfPossible(player.transform.position);
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void reset()
	{
		lastProcessTime = 0;
		activeObstacles = new ArrayList<>();
		lastProcessTime = 0;
		NEW_OBSTACLE_OFFSET = -600f;
	}

	private void deleteSpawnablesOutOfScreen(Set<Entity> obstacles)
	{
		for (Entity obstacle : obstacles)
		{
			if (RenderSystem.getRenderRect(obstacle).top > Device.SCREEN_HEIGHT)
				entityManager.deleteEntity(obstacle);
		}
	}

	private void createNewSpawnableIfPossible(Position playerPosition)
	{
		Entity topObstacle = activeObstacles.get(activeObstacles.size() - 1);

		//	Generate new
		if ((Math.abs(topObstacle.transform.position.y - playerPosition.y) <=
				MIN_ALLOWED_DISTANCE_FROM_PLAYER))
		{
			Log.v("NEW_OBSTACLE", "Top Obstacle ID: " + topObstacle.getId() + " Position: " + "" +
					topObstacle.transform.position + " Type: " +
					(topObstacle.hasComponent(Dragon.class) ? "Dragon" : "Platform"));

			Prefab spawnPrefab = obstacleShuffler.shuffle();
			//	TODO: Change platformWidth to spawnObstacle width
			Position spawnPosition =
					new Position((float) Math.random() * (Device.SCREEN_WIDTH - platformWidth),
								 topObstacle.transform.position.y + NEW_OBSTACLE_OFFSET);
			Entity newObstacle =
					entityManager.instantiate(spawnPrefab, new Transform(spawnPosition));
			activeObstacles.add(newObstacle);

			Log.i("NEW_OBSTACLE",
				  "Created new obstacle with id: " + newObstacle.getId() + " at: " +
						  spawnPosition +
						  " of type: " +
						  (newObstacle.hasComponent(Dragon.class) ? "Dragon" : "Platform"));
			Log.v("NEW_OBSTACLE", "Actual position: " + newObstacle.transform.position);
		}
	}

	private void initShuffler()
	{
		if (obstacleShuffler == null)
		{
			obstacleShuffler =
					new WeightedShuffler.Builder<Prefab>().returnItem(Prefabs.PLATFORM.get())
							.withWeight(5f).returnItem(Prefabs.DRAGON.get()).withWeight(5f)
							.returnItem(Prefabs.CLOCK.get()).withWeight(5f).build();
		}
	}

	private void initActiveObstacles()
	{
		Set<Entity> entities = componentManager.getEntities(Obstacle.class);
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
