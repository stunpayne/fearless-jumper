package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 09/04/18.
 */

public class LineShape extends Shape
{
	private Vector2D start;
	private Vector2D end;

	public LineShape(float startX, float startY, float endX, float endY,
			PaintProperties paintProperties)
	{
		this(new Vector2D(startX, startY), new Vector2D(endX, endY), paintProperties);
	}


	public LineShape(Vector2D start, Vector2D end, PaintProperties paintProperties)
	{
		super(ShapeType.LINE, paintProperties, new Vector2D(0, 0));
		this.start = start;
		this.end = end;
	}

	@Override
	public float getLeft()
	{
		return Math.min(start.getX(), end.getX());
	}

	@Override
	public float getRight()
	{
		return Math.max(start.getX(), end.getX());
	}

	@Override
	public float getTop()
	{
		return Math.min(start.getX(), end.getX());
	}

	@Override
	public float getBottom()
	{
		return Math.max(start.getX(), end.getX());
	}

	@Override
	public Vector2D getDelta()
	{
		return end.minus(start);
	}

	public Vector2D getStart()
	{
		return start;
	}

	public Vector2D getEnd()
	{
		return end;
	}
}
