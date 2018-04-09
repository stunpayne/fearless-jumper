package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 09/04/18.
 */

public class CircleShape extends Shape
{
	private float radius;

	public CircleShape(PaintProperties paintProperties, Vector2D delta)
	{
		super(ShapeType.CIRCLE, paintProperties, delta);
	}

	public Vector2D getCenter()
	{
		return getDelta();
	}

	@Override
	public float getLeft()
	{
		return getDelta().getX();
	}

	@Override
	public float getRight()
	{
		return getLeft() + 2 * radius;
	}

	@Override
	public float getTop()
	{
		return getDelta().getY();
	}

	@Override
	public float getBottom()
	{
		return getTop() + 2 * radius;
	}
}
