package com.stunapps.fearlessjumper.component;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.component.collider.RectCollider;
import com.stunapps.fearlessjumper.component.health.Health;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.entity.Entity;

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
    public Component getComponent(Entity entity, Class<? extends Component> componentType)
    {
        Component component = null;
        List<Component> components = entityToComponentMap.get(entity);
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
        return component;
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

        Iterator<List<Component>> iterator = entityToComponentMap.values().iterator();
        while (iterator.hasNext())
        {
            List<Component> components = iterator.next();
            Iterator<Component> componentIterator = components.iterator();
            while (componentIterator.hasNext())
            {
                Component component = componentIterator.next();
                if (component.componentType == componentType)
                {
                    componentIterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public Set<Entity> getEntities(Class<? extends Component> componentType)
    {
        return componentToTypeEntityMap.get(componentType);
    }

    @Override
    public void deleteEntity(Entity entity)
    {
        List<Component> components = entityToComponentMap.get(entity);
        entityToComponentMap.remove(entity);
        Iterator<Component> componentIterator = components.iterator();
        while (componentIterator.hasNext())
        {
            Component component = componentIterator.next();
            removeComponent(entity, component.componentType);
        }
    }

    @Override
    public boolean hasComponent(Entity entity, Class<? extends Component> componentType)
    {
        return getComponent(entity, componentType) != null;
    }
}
