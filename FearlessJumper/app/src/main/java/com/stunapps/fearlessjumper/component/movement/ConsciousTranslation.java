package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by anand.verma on 16/03/18.
 */

abstract public class ConsciousTranslation extends Component
{
	public Class<? extends Component> targetComponent;
	public float speed;

	public ConsciousTranslation(Class<? extends Component> targetComponent, float speed)
	{
		super(ConsciousTranslation.class);
		this.targetComponent = targetComponent;
		this.speed = speed;
	}

	@Override
	abstract public Component clone() throws CloneNotSupportedException;
}
