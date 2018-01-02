package com.stunapps.fearlessjumper.component.physics;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 03/01/18.
 */

public class PhysicsComponent extends Component
{
    private float mass;
    private float velocity;

    public PhysicsComponent(float mass, float velocity)
    {
        this.mass = mass;
        this.velocity = velocity;
    }

    public PhysicsComponent(float mass)
    {
        this.mass = mass;
        this.velocity = 0;
    }

    public float getMass()
    {
        return mass;
    }

    public float getVelocity()
    {
        return velocity;
    }
}
