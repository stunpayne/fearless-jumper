package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 13/01/18.
 */

public class PlayerComponent extends Component
{
    public String name;

    public PlayerComponent(String name)
    {
        super(PlayerComponent.class);
        this.name = name;
    }

    @Override
    public PlayerComponent clone() throws CloneNotSupportedException
    {
        return new PlayerComponent(this.name);
    }
}
