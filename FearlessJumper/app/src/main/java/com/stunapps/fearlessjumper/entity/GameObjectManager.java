package com.stunapps.fearlessjumper.entity;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.GameComponentManager;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by sunny.s on 04/01/18.
 */

@Singleton
public class GameObjectManager implements EntityManager
{
    private final Random rand = new Random();
    private ComponentManager gameComponentManager;
    private Map<Integer, Entity> entityMap = new HashMap<>();

    @Inject
    public GameObjectManager(ComponentManager gameComponentManager)
    {
        this.gameComponentManager = gameComponentManager;
    }

    @Override
    public Entity createEntity()
    {
        //TODO: Some entities can be created with default transform. Need to check how to accommodate such design.
        Entity gameObject = new Entity(gameComponentManager, rand.nextInt());
        entityMap.put(gameObject.getId(), gameObject);
        return gameObject;
    }

    @Override
    public void deleteEntity(Entity gameObject)
    {
        int id = gameObject.getId();
        entityMap.remove(id);
    }

    @Override
    public Entity getEntity(int id)
    {
        return entityMap.get(id);
    }

    @Override
    public List<Entity> getEntities()
    {
        return new ArrayList<>(entityMap.values());
    }

    public static void main(String[] args)
    {
        ComponentManager cm = new GameComponentManager();

        EntityManager em = new GameObjectManager(cm);

        Component component = new PhysicsComponent(15, 10);
        Entity entity = em.createEntity();
        entity.addComponent(component);
        //entity.getComponent(componentType);
        //entity.getComponents();
        //entity.removeComponent(componentType)

        //cm.getEntities(componentType)
        //em.getEntities()
    }
}
