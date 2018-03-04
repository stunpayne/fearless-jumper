package com.stunapps.fearlessjumper.model;

/**
 * Created by anand.verma on 04/03/18.
 */

//Maybe we should have Tuple class instead for anything like Position, Velocity
//and acceleration. For now, keeping it separate for readability.
public class Acceleration implements Cloneable
{
	public float x;
	public float y;

	public Acceleration()
	{
		x = y = 0;
	}

	public Acceleration(float x, float y)
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
}
