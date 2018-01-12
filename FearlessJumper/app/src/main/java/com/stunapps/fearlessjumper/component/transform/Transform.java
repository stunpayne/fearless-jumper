package com.stunapps.fearlessjumper.component.transform;

import com.stunapps.fearlessjumper.component.Component;

import lombok.Data;

/**
 * Created by sunny.s on 03/01/18.
 */

public class Transform extends Component
{
    public Position position = new Position();
    public Rotation rotation = new Rotation();
    public Scale scale = new Scale();

    public Transform(Position position, Rotation rotation, Scale scale)
    {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.componentType = Transform.class;
    }

    public class Position {
        public float x;
        public float y;
    }

    public class Rotation {
        public float p;
        public float r;
    }

    public class Scale {
        public float xScale;
        public float yScale;
    }
}
