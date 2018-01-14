package com.stunapps.fearlessjumper.component;

/**
 * Created by sunny.s on 03/01/18.
 */

public abstract class Component
{
    public Class<? extends Component> componentType;

    public Component(Class<? extends Component> componentType)
    {
        this.componentType = componentType;
    }

}
