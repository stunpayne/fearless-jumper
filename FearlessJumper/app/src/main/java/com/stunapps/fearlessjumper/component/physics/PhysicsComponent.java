package com.stunapps.fearlessjumper.component.physics;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Velocity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunny.s on 03/01/18.
 */

@Getter
@Setter
public class PhysicsComponent extends Component
{
	public float mass;
	public Velocity velocity;
	public boolean applyGravity;
	public float gravityFactor;
	public Friction friction;
	public float angularVelocity;

	public PhysicsComponent()
	{
		this(0);
	}

	public PhysicsComponent(boolean applyGravity)
	{
		this(0, Velocity.ZERO, applyGravity);
	}

	public PhysicsComponent(float mass)
	{
		this(mass, Velocity.ZERO, 1);
	}

	public PhysicsComponent(float mass, Velocity velocity, float gravityFactor)
	{
		this(mass, velocity, 0.0f, gravityFactor, true, Friction.none);
	}

	public PhysicsComponent(float mass, Velocity velocity, boolean applyGravity)
	{
		this(mass, velocity, applyGravity, Friction.none);
	}

	public PhysicsComponent(float mass, Velocity velocity, float angularVelocity,
			boolean applyGravity)
	{
		this(mass, velocity, angularVelocity, applyGravity, Friction.none);
	}

	public PhysicsComponent(float mass, Velocity velocity, boolean applyGravity, Friction friction)
	{
		this(mass, velocity, 0.0f, applyGravity, friction);
	}

	public PhysicsComponent(float mass, Velocity velocity, float angularVelocity,
			boolean applyGravity, Friction friction)
	{
		this(mass, velocity, angularVelocity, 1, applyGravity, friction);
	}

	public PhysicsComponent(float mass, Velocity velocity, float angularVelocity,
			float gravityFactor, boolean applyGravity, Friction friction)
	{
		super(PhysicsComponent.class);
		this.mass = mass;
		this.velocity = velocity;
		this.gravityFactor = gravityFactor;
		this.angularVelocity = angularVelocity;
		this.applyGravity = applyGravity;
		this.friction = friction;
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

		public Friction(float topFriction, float bottomFriction, float leftFriction,
				float rightFriction)
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
	public PhysicsComponent cloneComponent() throws CloneNotSupportedException
	{
		return new PhysicsComponent(mass, velocity.clone(), angularVelocity, gravityFactor,
				applyGravity, friction);
	}
}
