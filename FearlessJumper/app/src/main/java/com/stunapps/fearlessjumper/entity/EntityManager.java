package com.stunapps.fearlessjumper.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.prefab.Prefab;

import java.util.Collection;
import java.util.HashMap;
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
    private final ComponentManager gameComponentManager;
    private final Map<Integer, Entity> entityMap = new ConcurrentHashMap<>();

    @Inject
    public EntityManager(ComponentManager gameComponentManager)
    {
        this.gameComponentManager = gameComponentManager;
    }

    public Entity createEntity()
    {
        Entity entity = new Entity(gameComponentManager, this, rand.nextInt());
        entityMap.put(entity.getId(), entity);
        return entity;
    }

    public Entity instantiate(Prefab prefab)
    {
        Entity entity = new Entity(gameComponentManager, this, rand.nextInt());
        entityMap.put(entity.getId(), entity);
        for (Component component : prefab.components)
        {
            entity.addComponent(component);
        }
        return entity;
    }

    public void deleteEntity(Entity entity)
    {
        int id = entity.getId();
        entityMap.remove(id);
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