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

    public final void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    public abstract Component clone() throws CloneNotSupportedException;
}
