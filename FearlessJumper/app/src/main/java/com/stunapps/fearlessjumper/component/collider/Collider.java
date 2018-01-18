package com.stunapps.fearlessjumper.component.collider;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Center;

/**
 * Created by anand.verma on 12/01/18.
 */

public abstract class Collider extends Component
{
    public Delta delta;
    public float width;
    public float height;

    public Collider(Delta delta, float width, float height)
    {
        super(Collider.class);
        this.delta = delta;
        this.width = width;
        this.height = height;
    }

    public Center getCenter(Transform.Position position)
    {
        return new Center(position.x + delta.x + width / 2, position.y + delta.y + height / 2);
    }
}
