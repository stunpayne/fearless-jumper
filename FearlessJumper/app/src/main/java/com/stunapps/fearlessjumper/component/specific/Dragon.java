package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 10/02/18.
 */

public class Dragon extends Component
{
    public Dragon()
    {
        super(Dragon.class);
    }

    @Override
    public Dragon clone() throws CloneNotSupportedException
    {
        return new Dragon();
    }
}
