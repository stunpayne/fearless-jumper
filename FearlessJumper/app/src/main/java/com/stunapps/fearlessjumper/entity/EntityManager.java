package com.stunapps.fearlessjumper.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rits.cloning.Cloner;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabSet;
import com.stunapps.fearlessjumper.prefab.PrefabSet.PrefabSetEntry;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
		Entity entity = new Entity(componentManager, this, transform.clone(), rand.nextInt());
		entityMap.put(entity.getId(), entity);
		return entity;
	}

	public void instantiate(PrefabSet prefabSet, Transform spawnTransform)
	{
		Iterator<PrefabSetEntry> iterator = prefabSet.getEntries().iterator();
		while (iterator.hasNext())
		{
			PrefabSetEntry entry = iterator.next();
			instantiate(entry.getPrefab(),
					entry.getRelativeTransform().translateOrigin(spawnTransform));
		}
	}

	public Entity instantiate(Prefab prefab)
	{
		Entity entity = null;
		try
		{
			entity = createEntity(prefab.transform);
			populateComponentsFromPrefab(entity, prefab);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return entity;
	}

	public Entity instantiate(Prefab prefab, Transform transform)
	{
		Entity entity = null;
		try
		{
			entity = createEntity(transform);
			populateComponentsFromPrefab(entity, prefab);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
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

	private void populateComponentsFromPrefab(Entity entity, Prefab prefab)
			throws CloneNotSupportedException
	{
		for (Component component : prefab.components)
		{
			Component clone = component.clone();
			entity.addComponent(clone);
			clone.setEntity(entity);
		}
	}
}