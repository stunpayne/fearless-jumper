package com.stunapps.fearlessjumper.component.physics;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 03/01/18.
 */

public class PhysicsComponent extends Component
{
    public float mass;
    public Velocity velocity;
    public boolean applyGravity;
    public Friction friction;

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
        this(mass, velocity, true);
    }

    public PhysicsComponent(float mass, Velocity velocity, boolean applyGravity)
    {
        this(mass, velocity, applyGravity, Friction.none);
    }

    public PhysicsComponent(float mass, Velocity velocity, boolean applyGravity, Friction friction)
    {
        super(PhysicsComponent.class);
        this.mass = mass;
        this.velocity = velocity;
        this.applyGravity = applyGravity;
        this.friction = friction;
    }

    public static class Velocity
    {
        public float x;
        public float y;

        public Velocity()
        {
            x = y = 0;
        }

        public Velocity(float x, float y)
        {
            this.x = x;
            this.y = y;
        }
    }

    public static class Friction
    {
        public float topFriction;
        public float bottomFriction;
        public float leftFriction;
        public float rightFriction;

        public static Friction none = new Friction();

        public Friction()
        {
            this.topFriction = 0.0f;
            this.bottomFriction = 0.0f;
            this.leftFriction = 0.0f;
            this.rightFriction = 0.0f;
        }

        public Friction(float topFriction, float bottomFriction, float leftFriction, float rightFriction)
        {
            this.topFriction = topFriction;
            this.bottomFriction = bottomFriction;
            this.leftFriction = leftFriction;
            this.rightFriction = rightFriction;
        }

        public float getTopFriction()
        {
            return topFriction;
        }

        public float getBottomFriction()
        {
            return bottomFriction;
        }

        public float getLeftFriction()
        {
            return leftFriction;
        }

        public float getRightFriction()
        {
            return rightFriction;
        }
    }

    @Override
    public PhysicsComponent clone() throws CloneNotSupportedException
    {
        return new PhysicsComponent(mass, velocity, applyGravity, friction);
    }
}
