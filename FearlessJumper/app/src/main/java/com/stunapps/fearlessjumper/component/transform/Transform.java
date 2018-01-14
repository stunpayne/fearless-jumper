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
        super(Transform.class);
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static class Position
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

    public static class Rotation
    {
        public float azimuth;

        public Rotation()
        {
            azimuth = 0;
        }

        public Rotation(float azimuth)
        {
            this.azimuth = azimuth;
        }
    }

    public static class Scale
    {
        public float x;
        public float y;

        public Scale()
        {
            x = y = 1;
        }

        public Scale(float x, float y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
