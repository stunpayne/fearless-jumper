package com.stunapps.fearlessjumper.component;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by sunny.s on 03/01/18.
 */

public abstract class Component
{
    public Class<? extends Component> componentType;

    protected Entity entity;

    public Component(Class<? extends Component> componentType)
    {
        this.componentType = componentType;
    }

    public final Entity getEntity()
    {
        return entity;
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    public Component clone(Entity entity) throws CloneNotSupportedException
    {
        setEntity(entity);
        return cloneComponent();
    }

    public abstract Component cloneComponent() throws CloneNotSupportedException;
}
