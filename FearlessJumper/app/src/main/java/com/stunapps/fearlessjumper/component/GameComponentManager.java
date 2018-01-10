package com.stunapps.fearlessjumper.component;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sunny.s on 04/01/18.
 */

@Singleton
public class GameComponentManager implements ComponentManager<Entity>
{
    private Map<Class<? extends Component>, List<Entity>> componentTypeEntityMap;
    private Map<Entity, List<Component>> entityComponentMap;

    public GameComponentManager()
    {
        componentTypeEntityMap = new ConcurrentHashMap<>();
        entityComponentMap = new ConcurrentHashMap<>();
    }

    @Override
    public <C extends Component> void addComponent(Entity entity, C component)
    {
        List<Entity> entities = componentTypeEntityMap.get(component.componentType);
        if (entities == null)
        {
            entities = new LinkedList<>();
        }
        entities.add(entity);

        List<Component> components = entityComponentMap.get(entity);
        if (components == null)
        {
            components = new LinkedList<>();
        }
        components.add(component);
    }

    @Override
    public void deleteComponent(Entity entity, Class<? extends Component> componentType)
    {
        componentTypeEntityMap.get(componentType).remove(entity);
        List<Component> components = entityComponentMap.get(entity);
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
        List<Component> components = entityComponentMap.get(entity);
        Iterator<Component> it = components.iterator();
        while (it.hasNext())
        {
            Component componentFromList = it.next();
            if (componentFromList.componentType == componentType)
            {
                component = componentFromList;
                break;
            }
        }
        return component;
    }
}
