package com.stunapps.fearlessjumper.component.collider;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;

/**
 * Created by anand.verma on 12/01/18.
 */

public abstract class Collider extends Component
{
    public Delta delta;

    public Collider(Delta delta)
    {
        super(Collider.class);
        this.delta = delta;
    }
}
