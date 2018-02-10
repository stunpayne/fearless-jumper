package com.stunapps.fearlessjumper.component.collider;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;

/**
 * Created by anand.verma on 12/01/18.
 */

public class RectCollider extends Collider
{
    public RectCollider(Delta delta, float width, float height)
    {
        super(delta, width, height);
    }

    @Override
    public RectCollider clone() throws CloneNotSupportedException
    {
        return new RectCollider(this.delta, this.width, this.height);
    }
}
