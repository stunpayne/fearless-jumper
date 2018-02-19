package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 15/02/18.
 */

public class Fuel extends Component
{
	private float fuel;
	private boolean discharging;

	public Fuel(float fuel)
	{
		super(Fuel.class);
		this.fuel = fuel;
		this.discharging = false;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new Fuel(fuel);
	}

	public void refuel(float refuelAmount)
	{
		this.fuel += refuelAmount;
	}

	public float getFuel()
	{
		return fuel;
	}

	public void setFuel(float fuel)
	{
		this.fuel = fuel;
	}

	public void dischargeFuel(float amountUsed)
	{
		this.fuel -= amountUsed;
	}

	public boolean isDischarging()
	{
		return discharging;
	}

	public void setDischarging(boolean discharging)
	{
		this.discharging = discharging;
	}
}
