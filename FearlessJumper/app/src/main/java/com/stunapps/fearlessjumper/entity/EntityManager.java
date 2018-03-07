package com.stunapps.fearlessjumper.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rits.cloning.Cloner;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.prefab.Prefab;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sunny.s on 04/01/18.
 */

@Singleton
public class EntityManager
{
	private final Random rand = new Random();
	private final ComponentManager componentManager;
	private final Map<Integer, Entity> entityMap = new ConcurrentHashMap<>();

	@Inject
	public EntityManager(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	public Entity createEntity(Transform transform)
	{
		Entity entity = new Entity(componentManager, this, transform, rand.nextInt());
		entityMap.put(entity.getId(), entity);
		return entity;
	}

	public Entity createEntity(Transform transform, int id)
	{
		Entity entity = new Entity(componentManager, this, transform, id);
		entityMap.put(entity.getId(), entity);
		return entity;
	}

	public Entity instantiate(Prefab prefab)
	{
		Entity entity = null;
		try
		{
			entity = createEntity(prefab.transform.clone());
			for (Component component : prefab.components)
			{
				Component clone = component.clone();
				entity.addComponent(clone);
				clone.setEntity(entity);
			}
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return entity;
	}

	public Entity instantiate(Prefab prefab, Transform transform)
	{
		Entity entity = instantiate(prefab);
		entity.transform = transform;
		return entity;
	}

	public void deleteEntity(Entity entity)
	{
		int id = entity.getId();
		componentManager.deleteEntity(entity);
		entityMap.remove(id);
	}

	public void deleteEntities()
	{
		componentManager.deleteEntities();
		entityMap.clear();
	}

	public Entity getEntity(int id)
	{
		return entityMap.get(id);
	}

	public Collection<Entity> getEntities()
	{
		return entityMap.values();
	}
}