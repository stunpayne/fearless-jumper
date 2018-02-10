package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 10/02/18.
 */

public class DragonComponent extends Component
{
    public DragonComponent()
    {
        super(DragonComponent.class);
    }

    @Override
    public DragonComponent clone() throws CloneNotSupportedException
    {
        return new DragonComponent();
    }
}
