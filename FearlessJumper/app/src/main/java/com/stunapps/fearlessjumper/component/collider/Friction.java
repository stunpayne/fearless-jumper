package com.stunapps.fearlessjumper.component.collider;

import lombok.ToString;

/**
 * Created by sunny.s on 20/01/18.
 */

@ToString
public class Friction
{
    public float x;
    public float y;

    public Friction()
    {
        x = y = 0;
    }

    public Friction(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
