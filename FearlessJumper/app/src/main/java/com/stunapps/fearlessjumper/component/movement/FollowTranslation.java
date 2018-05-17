package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

import java.util.Random;

/**
 * Created by anand.verma on 11/03/18.
 */

public class FollowTranslation extends ConsciousTranslation
{
	public FollowTranslation(Class<? extends Component> targetComponent, float speed)
	{
		super(targetComponent, speed);
	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new FollowTranslation(this.targetComponent,
									 (new Random().nextFloat() % 10) + this.speed);
	}
}
