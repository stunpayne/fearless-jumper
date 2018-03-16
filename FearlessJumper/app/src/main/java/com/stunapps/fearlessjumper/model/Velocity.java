package com.stunapps.fearlessjumper.model;

import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by anand.verma on 02/03/18.
 */
@Getter
@Setter
public class Velocity implements Cloneable
{
	public static final Velocity ZERO = new Velocity(0, 0);

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

	@Override
	public Velocity clone() throws CloneNotSupportedException
	{
		super.clone();
		return new Velocity(x, y);
	}

	@Override
	public String toString()
	{
		return "Velocity{" + "x=" + x + ", y=" + y + '}';
	}
}
