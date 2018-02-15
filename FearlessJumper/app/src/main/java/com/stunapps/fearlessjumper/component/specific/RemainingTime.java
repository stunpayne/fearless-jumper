package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 15/02/18.
 */

public class RemainingTime extends Component
{
	private float remainingSeconds;

	public RemainingTime(float remainingSeconds)
	{
		super(RemainingTime.class);
		this.remainingSeconds = remainingSeconds;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new RemainingTime(remainingSeconds);
	}

	public float getRemainingSeconds()
	{
		return remainingSeconds;
	}

	public void addSeconds(float time)
	{
		remainingSeconds += time;
	}
}
