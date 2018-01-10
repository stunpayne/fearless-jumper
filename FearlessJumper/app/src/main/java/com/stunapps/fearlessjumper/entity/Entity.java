package com.stunapps.fearlessjumper.entity;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.transform.Transform;

/**
 * Created by sunny.s on 02/01/18.
 */

public class Entity
{
    public final int id;
    public ComponentManager componentManager;

    public Transform transform;

    public Entity(ComponentManager componentManager, Transform transform, int id)
    {
        this.id = id;
        this.componentManager = componentManager;
        componentManager.addComponent(this, transform);
    }

    public Entity(ComponentManager componentManager, int id)
    {
        this.id = id;
        this.componentManager = componentManager;
    }


    public void addComponent(Component component)
    {
        componentManager.addComponent(this, component);
    }

    public int getId()
    {
        return id;
    }
}
