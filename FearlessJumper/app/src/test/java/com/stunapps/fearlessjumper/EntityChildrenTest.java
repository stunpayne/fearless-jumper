package com.stunapps.fearlessjumper;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by sunny.s on 07/03/18.
 */

public class EntityChildrenTest
{
	private static ComponentManager componentManager;
	private static EntityManager entityManager;

	private static int numberOfChildren = 10;
	private static int height = 5;

	@BeforeClass
	public static void setUp()
	{
		componentManager = new GameComponentManager();
		entityManager = new EntityManager(componentManager);
	}

	@Test
	public void compareMethods()
	{
		List<Integer> heights = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
		List<Integer> childrens = new ArrayList<Integer>(Arrays.asList(1, 5, 10, 20, 30, 40));

		runAll(heights, childrens);
	}

	private void runAll(List<Integer> heights, List<Integer> childrens)
	{
		for (Integer h : heights)
		{
			for (Integer c : childrens)
			{
				height = h;
				numberOfChildren = c;
				testGetComponentsInChildrenRecursive();
				testGetComponentsInChildrenIterative();

				entityManager.deleteEntities();
			}
		}
	}


	@Test
	public void testGetComponentsInChildrenRecursive()
	{
		Entity root = entityManager.createEntity(null);
		root.addComponent(new PlayerComponent());
		addChildren(root, height, numberOfChildren);

		long startTime;
		long endTime;

		Set<Entity> entities = componentManager.getEntities(PlayerComponent.class);
		startTime = System.currentTimeMillis();
		List<PlayerComponent> componentsInChildrenRecursive =
				componentManager.getComponentsInChildrenRecursive(root, PlayerComponent.class);
		endTime = System.currentTimeMillis();

		System.out.println("Recursive Height: " + height + " Children: " + numberOfChildren + " " +
				"Duration: " +
				(endTime - startTime));
	}

	@Test
	public void testGetComponentsInChildrenIterative()
	{
		Entity root = entityManager.createEntity(null);
		root.addComponent(new PlayerComponent());
		addChildren(root, height, numberOfChildren);

		long startTime;
		long endTime;

		Set<Entity> entities = componentManager.getEntities(PlayerComponent.class);
		startTime = System.currentTimeMillis();
		List<PlayerComponent> componentsInChildrenIterative =
				componentManager.getComponentsInChildrenIterative(root, PlayerComponent.class);
		endTime = System.currentTimeMillis();

		System.out.println("Iterative Height: " + height + " Children: " + numberOfChildren + " " +
				"Duration: " +
				(endTime - startTime));
	}

	private void addChildren(Entity root, int height, int numberOfChildren)
	{
		if (height <= 0) return;
		for (int i = 0; i < numberOfChildren; i++)
		{
			Entity child = entityManager.createEntity(null);
			child.addComponent(new PlayerComponent());
			addChildren(child, height - 1, numberOfChildren);
			root.addChild(child);
		}
	}
}
