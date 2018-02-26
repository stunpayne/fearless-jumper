package com.stunapps.fearlessjumper.component;

import android.util.Log;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.entity.Entity;


import org.roboguice.shaded.goole.common.collect.Sets;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sunny.s on 04/01/18.
 */

@Singleton
public class GameComponentManager implements ComponentManager
{
    private static final String TAG = "GameComponentManager";
    private Map<Class<? extends Component>, Set<Entity>> componentToTypeEntityMap;
    private Map<Entity, List<Component>> entityToComponentMap;

    public GameComponentManager()
    {
        componentToTypeEntityMap = new ConcurrentHashMap<>();
        entityToComponentMap = new ConcurrentHashMap<>();
    }

    @Override
    public <C extends Component> void addComponent(Entity entity, C component)
    {
        Set<Entity> entities = componentToTypeEntityMap.get(component.componentType);
        if (entities == null)
        {
            componentToTypeEntityMap.put(component.componentType, new HashSet<Entity>());
        }
        componentToTypeEntityMap.get(component.componentType).add(entity);

        List<Component> components = entityToComponentMap.get(entity);
        if (components == null)
        {
            entityToComponentMap.put(entity, new LinkedList<Component>());
        }
        entityToComponentMap.get(entity).add(component);
    }

    @Override
    public void deleteComponent(Entity entity, Class<? extends Component> componentType)
    {
        componentToTypeEntityMap.get(componentType).remove(entity);
        List<Component> components = entityToComponentMap.get(entity);
        Iterator<Component> it = components.iterator();
        while (it.hasNext())
        {
            if (it.next().componentType == componentType)
            {
                it.remove();
                break;
            }
        }
    }

    @Override
    public <C extends Component> C getComponent(Entity entity, Class<C> componentType)
    {
        Component component = null;
        List<Component> components = entityToComponentMap.get(entity);
        if (components == null)
        {
            Log.d(TAG, "getComponent: entity = " + entity);
            return null;
        }
        Iterator<Component> it = components.iterator();
        while (it.hasNext())
        {
            Component componentFromList = it.next();
            if (componentFromList.componentType.equals(componentType))
            {
                component = componentFromList;
                break;
            }
        }
        return componentType.cast(component);
    }

    @Override
    public List<Component> getComponents(Entity entity)
    {
        return entityToComponentMap.get(entity);
    }

    @Override
    public void removeComponent(Entity entity, Class<? extends Component> componentType)
    {
        Set<Entity> entities = componentToTypeEntityMap.get(componentType);
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext())
        {
            Entity entityFromList = it.next();
            if (entityFromList.equals(entity))
            {
                it.remove();
                break;
            }
        }
    }

    @Override
    public Set<Entity> getEntities(Class<? extends Component> componentType)
    {
        Set<Entity> entities = componentToTypeEntityMap.get(componentType);
        if (null == entities)
            return new HashSet<>();
        return Sets.newHashSet(entities);
    }

    @Override
    public Entity getEntity(Class<? extends Component> componentType)
    {
        Set<Entity> entities = componentToTypeEntityMap.get(componentType);
        if (null == entities)
            return null;
        return entities.iterator().next();
    }

    @Override
    public void deleteEntity(Entity entity)
    {
        List<Component> components = entityToComponentMap.get(entity);
        Iterator<Component> componentIterator = components.iterator();
        while (componentIterator.hasNext())
        {
            Component component = componentIterator.next();
            removeComponent(entity, component.componentType);
        }
        entityToComponentMap.remove(entity);
    }

    @Override
    public void deleteEntities()
    {
        componentToTypeEntityMap.clear();
        entityToComponentMap.clear();
    }

    @Override
    public boolean hasComponent(Entity entity, Class<? extends Component> componentType)
    {
        return getComponent(entity, componentType) != null;
    }
}
