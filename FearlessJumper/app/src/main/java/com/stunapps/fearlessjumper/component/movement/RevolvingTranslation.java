package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by anand.verma on 10/05/18.
 */

public class RevolvingTranslation extends Component
{
	private float radius;

	public RevolvingTranslation(float radius)
	{
		super(RevolvingTranslation.class);
		this.radius = radius;
	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new RevolvingTranslation(radius);
	}
}
