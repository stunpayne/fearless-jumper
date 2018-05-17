package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 26/01/18.
 */

public class LowerBoundaryComponent extends Component
{
    public LowerBoundaryComponent()
    {
        super(LowerBoundaryComponent.class);
    }

    @Override
    public LowerBoundaryComponent cloneComponent() throws CloneNotSupportedException
    {
        return new LowerBoundaryComponent();
    }
}
