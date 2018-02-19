package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 15/02/18.
 */

public class Fuel extends Component
{
	private float fuel;

	/**
	 * Fields to enable waiting before refuelling
	 */
	private Long waitStartTime = null;
	private Long refuelWaitMillis;

	public Fuel(float fuel)
	{
		this(fuel, 400);
	}

	public Fuel(float fuel, long refuelWaitMillis)
	{
		super(Fuel.class);
		this.fuel = fuel;
		this.refuelWaitMillis = refuelWaitMillis;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new Fuel(fuel, refuelWaitMillis);
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

	public void startWaitingToRefuel()
	{
		waitStartTime = System.currentTimeMillis();
	}

	public void exitWaiting()
	{
		waitStartTime = null;
	}

	public boolean isWaitingToRefuel()
	{
		return waitStartTime != null;
	}

	public boolean canExitWaiting()
	{
		return System.currentTimeMillis() > waitStartTime + refuelWaitMillis;
	}
}
