package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by anand.verma on 10/05/18.
 */

public class RevolvingTranslation extends Component
{
	private float radius;
	private float omega;
	private float theta;

	public RevolvingTranslation(float radius, float omega)
	{
		super(RevolvingTranslation.class);
		this.radius = radius;
		this.omega = omega;
	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new RevolvingTranslation(radius, omega);
	}

	public float getRadius()
	{
		return radius;
	}

	public float getOmega()
	{
		return omega;
	}

	public float getTheta()
	{
		return theta;
	}

	public void setTheta(float theta)
	{
		this.theta = theta;
	}
}
