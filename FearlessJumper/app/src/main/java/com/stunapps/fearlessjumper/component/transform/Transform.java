package com.stunapps.fearlessjumper.component.transform;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;

/**
 * Created by sunny.s on 03/01/18.
 */

public class Transform extends Component
{
    public float[] position = new float[3];
    public float[] rotation = new float[3];
    public float[] scale = new float[3];

    public Transform(float[] position, float[] rotation, float[] scale)
    {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.componentType = Transform.class;
    }
}
