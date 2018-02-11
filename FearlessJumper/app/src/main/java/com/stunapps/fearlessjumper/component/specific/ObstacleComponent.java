package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 13/01/18.
 */

public class ObstacleComponent extends Component
{
    public ObstacleComponent()
    {
        super(ObstacleComponent.class);
    }

    @Override
    public ObstacleComponent clone() throws CloneNotSupportedException
    {
        return new ObstacleComponent();
    }
}
