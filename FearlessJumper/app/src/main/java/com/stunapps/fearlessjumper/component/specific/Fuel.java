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

	private Long elapsedWaitingTime;

	public Fuel(float fuel)
	{
		this(fuel, 900);
	}

	public Fuel(float fuel, long refuelWaitMillis)
	{
		super(Fuel.class);
		this.fuel = fuel;
		this.refuelWaitMillis = refuelWaitMillis;
		this.elapsedWaitingTime = 0L;
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
		elapsedWaitingTime = 0L;
	}

	public boolean isWaitingToRefuel()
	{
		return waitStartTime != null;
	}

	public boolean canExitWaiting(long deltaTime)
	{
		elapsedWaitingTime += deltaTime;
		return elapsedWaitingTime > refuelWaitMillis;
	}
}
