package com.stunapps.fearlessjumper.component.visual;

import android.graphics.RectF;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 09/04/18.
 */

public class CircleShape extends Shape
{
	private float radius;

	public CircleShape(float radius, PaintProperties paintProperties, Vector2D delta)
	{
		super(ShapeType.CIRCLE, paintProperties, delta);
		this.radius = radius;
	}

	public Vector2D getCenter()
	{
		return Vector2D.add(getDelta(), new Vector2D(radius, radius));
	}

	public float getRadius()
	{
		return radius;
	}

	public float getDiameter()
	{
		return 2 * getRadius();
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
