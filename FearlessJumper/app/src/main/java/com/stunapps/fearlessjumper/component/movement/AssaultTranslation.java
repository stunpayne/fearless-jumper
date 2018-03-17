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
	public Float assaultFactor;
	public Float assaultActivationDistance;

	public AssaultTranslation(Class<? extends Component> targetComponent, float speed,
			long waitToAssault, float assaultFactor, float assaultActivationDistance)
	{
		super(targetComponent, speed);
		this.waitStarted = false;
		this.waitToAssault = waitToAssault;
		this.assaultFactor = assaultFactor;
		this.assaultActivationDistance = assaultActivationDistance;
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new AssaultTranslation(this.targetComponent,
									  (new Random().nextFloat() % 10) + this.speed,
									  new Long(this.waitToAssault), new Float(this.assaultFactor),
									  new Float(this.assaultActivationDistance));
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
		if (activationTime == null || waitToAssault == null)
		{
			return false;
		}
		return System.currentTimeMillis() > (activationTime + waitToAssault);
	}
}
