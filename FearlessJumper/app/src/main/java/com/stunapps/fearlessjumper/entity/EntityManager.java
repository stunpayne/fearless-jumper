package com.stunapps.fearlessjumper.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sunny.s on 04/01/18.
 */

@Singleton
public class EntityManager<E extends Entity>
{
    private final Random rand = new Random();
    private ComponentManager gameComponentManager;
    private Map<Integer, Entity> entityMap = new HashMap<>();

    @Inject
    public EntityManager(ComponentManager gameComponentManager)
    {
        this.gameComponentManager = gameComponentManager;
    }

    public Entity createEntity()
    {
        Entity gameObject = new Entity(gameComponentManager, this, rand.nextInt());
        entityMap.put(gameObject.getId(), gameObject);
        return gameObject;
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