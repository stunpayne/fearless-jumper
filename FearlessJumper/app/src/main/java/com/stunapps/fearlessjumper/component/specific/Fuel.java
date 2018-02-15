package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 15/02/18.
 */

public class Fuel extends Component
{
	private float fuel;

	public Fuel(float fuel)
	{
		super(Fuel.class);
		this.fuel = fuel;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new Fuel(fuel);
	}

	public float getFuel()
	{
		return fuel;
	}

	public void refuel(float fuel)
	{
		this.fuel += fuel;
	}
}
