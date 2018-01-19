package com.stunapps.fearlessjumper.component.transform;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent.Velocity;
import com.stunapps.fearlessjumper.helper.Constants;

import lombok.Builder;
import lombok.ToString;

/**
 * Created by sunny.s on 03/01/18.
 */

@Builder
@ToString
public class Transform extends Component
{
    public Position position = new Position();
    public Rotation rotation = new Rotation();
    public Scale scale = new Scale();

    public Transform(Position position)
    {
        super(Transform.class);
        this.position = position;
        this.rotation = Constants.Game.NO_ROTATION;
        this.scale = Constants.Game.UNIT_SCALE;
    }

    public Transform(Position position, Rotation rotation, Scale scale)
    {
        super(Transform.class);
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @ToString
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

    @ToString
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

    public void move(Velocity velocity)
    {
        this.position.x += velocity.x;
        this.position.y += velocity.y;
    }
}
