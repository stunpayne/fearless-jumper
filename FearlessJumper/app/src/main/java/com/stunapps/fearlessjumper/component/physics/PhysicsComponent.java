package com.stunapps.fearlessjumper.component.physics;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 03/01/18.
 */

public class PhysicsComponent extends Component
{
    public float mass;
    public Velocity velocity;

    public PhysicsComponent()
    {
        this(0);
    }

    public PhysicsComponent(float mass)
    {
        this(mass, new Velocity());
    }

    public PhysicsComponent(float mass, Velocity velocity)
    {
        this.mass = mass;
        this.velocity = velocity;
        this.componentType = PhysicsComponent.class;
    }

    public static class Velocity
    {
        public float x;
        public float y;
    }
}
