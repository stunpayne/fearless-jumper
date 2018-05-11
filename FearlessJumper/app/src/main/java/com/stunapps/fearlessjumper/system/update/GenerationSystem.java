package com.stunapps.fearlessjumper.system.update;

import android.graphics.Rect;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.spawnable.Dragon;
import com.stunapps.fearlessjumper.component.spawnable.Enemy;
import com.stunapps.fearlessjumper.component.spawnable.Obstacle;
import com.stunapps.fearlessjumper.component.spawnable.Pickup;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.instantiation.prefab.Prefab;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.RuleEngine;
import com.stunapps.fearlessjumper.rules.execution.generation.GenerationRuleResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import static com.stunapps.fearlessjumper.game.Environment.scaleY;

/**
 * Created by sunny.s on 13/01/18.
 */

@Singleton
public class GenerationSystem implements UpdateSystem
{
	private static final String TAG = GenerationSystem.class.getSimpleName();

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
			NEW_OBSTACLE_OFFSET *= scaleY();
			initShuffler();
			initActiveObstacles();

			platformWidth = calculator.getWidth(PrefabRef.PLATFORM.get());
		}

		Entity player = componentManager.getEntity(PlayerComponent.class);
		Set<Entity> spawnables = componentManager.getEntities(Obstacle.class);
		spawnables.addAll(componentManager.getEntities(Pickup.class));
		spawnables.addAll(componentManager.getEntities(Enemy.class));

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
		NEW_OBSTACLE_OFFSET = -600f;
	}

	private void deleteSpawnablesOutOfScreen(Set<Entity> obstacles)
	{
		for (Entity obstacle : obstacles)
		{
			Rect renderRect = RenderSystem.getRenderRect(obstacle);
			if (renderRect.top > Device.SCREEN_HEIGHT || renderRect.left > Device.SCREEN_WIDTH ||
					renderRect.right < 0)
			{
				Log.d(TAG, "Deleting spawnable: " + obstacle.getId() + " of type: " +
						getSpawnableName(obstacle));
				entityManager.deleteEntity(obstacle);
			}
		}
	}

	private void createNewSpawnableIfPossible(Position playerPosition)
	{
		Entity topObstacle = activeObstacles.get(activeObstacles.size() - 1);

		//	Generate new
		if ((Math.abs(topObstacle.transform.position.y - playerPosition.y) <=
				MIN_ALLOWED_DISTANCE_FROM_PLAYER))
		{
			Log.d("OBSTACLE_RULES", "Generating new obstacle");
			GenerationRuleResponse ruleResponse =
					RuleEngine.execute(componentManager, entityManager);
			Prefab spawnPrefab = ruleResponse.getNextPrefab().get();
			Transform spawnTransform = ruleResponse.getTransform();
			//			Prefab spawnPrefab = obstacleShuffler.shuffle();
			Entity newObstacle = entityManager.instantiate(spawnPrefab, spawnTransform).get(0);
			activeObstacles.add(newObstacle);

			Log.i("NEW_OBSTACLE",
					"Created new obstacle with id: " + newObstacle.getId() + " at:" + " " +
							spawnTransform + " of type: " +
							(newObstacle.hasComponent(Dragon.class) ? "Dragon" : "Platform"));
			Log.v("NEW_OBSTACLE", "Actual position: " + newObstacle.transform.position);
		}
	}

	private void initShuffler()
	{
		if (obstacleShuffler == null)
		{
			obstacleShuffler =
					new WeightedShuffler.Builder<Prefab>()
							.returnItem(PrefabRef.PLATFORM.get()).withWeight(5f)
							.returnItem(PrefabRef.FLYING_DRAGON.get()).withWeight(1f)
							.returnItem(PrefabRef.CLOCK.get()).withWeight(3f)
							.returnItem(PrefabRef.SHOOTER_DRAGON.get()).withWeight(1f)
							.returnItem(PrefabRef.FOLLOWING_DRAGON.get()).withWeight(1f)
							.returnItem(PrefabRef.ASSAULT_SMILEY.get()).withWeight(20f)
							.returnItem(PrefabRef.UNFRIENDLY_PLATFORM.get()).withWeight(5f)
							.build();
		}
	}

	private void initActiveObstacles()
	{
		Set<Entity> spawnables = componentManager.getEntities(Obstacle.class);
		spawnables.addAll(componentManager.getEntities(Pickup.class));
		spawnables.addAll(componentManager.getEntities(Enemy.class));
		activeObstacles.addAll(spawnables);

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

	private String getSpawnableName(Entity entity)
	{
		String spawnableType;

		if (entity.hasComponent(Obstacle.class)) spawnableType = Obstacle.class.getSimpleName();
		else if (entity.hasComponent(Enemy.class))
			spawnableType = entity.getComponent(Enemy.class).getEnemyType().name();
		else if (entity.hasComponent(Pickup.class))
			spawnableType = entity.getComponent(Pickup.class).getType().name();
		else spawnableType = "ABETUKAUNHAI";

		return spawnableType;
	}
}
