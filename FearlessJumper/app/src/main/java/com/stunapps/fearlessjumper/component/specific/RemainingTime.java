package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 15/02/18.
 */

public class RemainingTime extends Component
{
	private long remainingMilliseconds;

	public RemainingTime(long remainingMilliseconds)
	{
		super(RemainingTime.class);
		this.remainingMilliseconds = remainingMilliseconds;
	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new RemainingTime(remainingMilliseconds);
	}

	public float getRemainingSeconds()
	{
		return remainingMilliseconds / 1000;
	}

	public boolean isUp()
	{
		return remainingMilliseconds <= 0;
	}

	public void addSeconds(long time)
	{
		remainingMilliseconds += time;
	}

	public void decreaseSeconds(long time)
	{
		remainingMilliseconds -= time;
	}
}
