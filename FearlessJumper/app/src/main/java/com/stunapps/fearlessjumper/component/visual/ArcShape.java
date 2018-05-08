package com.stunapps.fearlessjumper.component.visual;

import android.graphics.RectF;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by anand.verma on 26/04/18 10:06 PM.
 */

public class ArcShape extends Shape
{
	private float width;
	private float height;
	private float startAngle;
	private float sweepAngle;
	private boolean useCenter;

	public ArcShape(Vector2D topLeft, float width, float height, float startAngle, float
			sweepAngle,
			boolean useCenter, PaintProperties paintProperties)
	{
		super(ShapeType.ARC, paintProperties, topLeft);
		//oval = new RectF(topLeft.getX(), topLeft.getY(), topLeft.getX() + width, topLeft.getY() +
		//	height);
		this.width = width;
		this.height = height;
		this.startAngle = startAngle;
		this.sweepAngle = sweepAngle;
		this.useCenter = useCenter;
	}

	@Override
	public float getLeft()
	{
		return getDelta().getX();
	}

	@Override
	public float getRight()
	{
		return getDelta().getX() + width;
	}

	@Override
	public float getTop()
	{
		return getDelta().getY();
	}

	@Override
	public float getBottom()
	{
		return getDelta().getY() + height;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public float getStartAngle()
	{
		return startAngle;
	}

	public float getSweepAngle()
	{
		return sweepAngle;
	}

	public boolean isUseCenter()
	{
		return useCenter;
	}
}
