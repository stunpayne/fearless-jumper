package com.stunapps.fearlessjumper.component.spawnable;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 13/01/18.
 */

public class Obstacle extends Component
{
    public Obstacle()
    {
        super(Obstacle.class);
    }

    @Override
    public Obstacle cloneComponent() throws CloneNotSupportedException
    {
        return new Obstacle();
    }
}
