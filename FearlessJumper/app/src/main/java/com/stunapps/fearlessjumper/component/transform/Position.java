package com.stunapps.fearlessjumper.component.transform;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by sunny.s on 19/01/18.
 */

@ToString
@Getter
@Setter
public class Position
{
    public float x;
    public float y;

    public Position()
    {
        x = y = 0;
    }

    public Position(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
