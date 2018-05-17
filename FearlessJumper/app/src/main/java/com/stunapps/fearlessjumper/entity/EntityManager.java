package com.stunapps.fearlessjumper.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.instantiation.Instantiable;
import com.stunapps.fearlessjumper.instantiation.prefab.Prefab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

	public Entity createEntity(Transform transform) throws CloneNotSupportedException
	{
		Entity entity = new Entity(componentManager, this, transform.cloneComponent(), rand.nextInt());
		entityMap.put(entity.getId(), entity);
		return entity;
	}

	public List<Entity> instantiate(Prefab prefab, Transform spawnTransform)
	{
		Iterator<Instantiable> iterator = prefab.getInstantiables().iterator();
		List<Entity> createdEntities = new ArrayList<>();

		try
		{
			while (iterator.hasNext())
			{
				Instantiable instantiable = iterator.next();
				Entity entity = createEntity(
						instantiable.getRelativeTransform().translateOrigin(spawnTransform));
				populateComponentsFromInstantiable(entity, instantiable);
				createdEntities.add(entity);
			}
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return createdEntities;
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

	private void populateComponentsFromInstantiable(Entity entity, Instantiable instantiable)
			throws CloneNotSupportedException
	{
		for (Component component : instantiable.getComponents())
		{
			Component clone = component.clone(entity);
			entity.addComponent(clone);
		}
	}
}