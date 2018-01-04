package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunny.s on 04/01/18.
 */

public class GameComponentManager implements ComponentManager<GameObject>
{

    //TODO: TO make this thread safe.
    private Map<Class<? extends Component>, List<GameObject>> componentTypeEntityMap;
    private Map<GameObject, List<Component>> entityComponentMap;

    public GameComponentManager()
    {
        componentTypeEntityMap = new HashMap<>();
        entityComponentMap = new HashMap<>();
    }

    @Override
    public <C extends Component> void addComponent(C component, GameObject gameObject)
    {
        componentTypeEntityMap.get(component.componentType).add(gameObject);
        entityComponentMap.get(gameObject.getClass()).add(component);
    }

    @Override
    public <C extends Component> void deleteComponent(C componentType, Entity gameObject)
    {

    }

    @Override
    public <C extends Component> Component getComponent(C componentType, Entity gameObject)
    {
        return null;
    }

/*    @Override
    public <C extends Component> void deleteComponent(C componentType, GameObject gameObject)
    {
        componentTypeEntityMap.get(componentType).remove(gameObject);
        entityComponentMap.get(gameObject).remove(componentType);
    }

    @Override
    public <C extends Component> Component getComponent(C componentType, GameObject gameObject)
    {
        return entityComponentMap.get(gameObject).get(0);
    }*/


}
