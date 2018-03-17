package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

import java.util.Random;

/**
 * Created by anand.verma on 16/03/18.
 */

public class AssaultTranslation extends ConsciousTranslation
{
	public boolean waitStarted;
	public Long waitToAssault;
	public Long activationTime;
	public Float assaultAngle;

	public AssaultTranslation(Class<? extends Component> targetComponent, float speed,
			long waitToAssault)
	{
		super(targetComponent, speed);
		this.waitStarted = false;
		this.waitToAssault = waitToAssault;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new AssaultTranslation(this.targetComponent,
									  (new Random().nextFloat() % 10) + this.speed,
									  this.waitToAssault);
	}

	public void setActivationTime(Long activationTime)
	{
		this.activationTime = activationTime;
	}

	public void setAssaultAngle(float assaultAngle)
	{
		this.assaultAngle = assaultAngle;
	}

	public boolean isWaitToAssaultOver()
	{
		return System.currentTimeMillis() > (activationTime + waitToAssault);
	}
}
