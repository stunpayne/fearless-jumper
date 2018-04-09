package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 09/04/18.
 */

public class RectShape extends Shape
{
	private float width;
	private float height;

	public RectShape(float width, float height, PaintProperties paintProperties, Vector2D delta)
	{
		super(ShapeType.RECT, paintProperties, delta);
		this.width = width;
		this.height = height;
	}

	@Override
	public float getLeft()
	{
		return getDelta().getX();
	}

	@Override
	public float getRight()
	{
		return getLeft() + width;
	}

	@Override
	public float getTop()
	{
		return getDelta().getY();
	}

	@Override
	public float getBottom()
	{
		return getTop() + height;
	}
}
