package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Path;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by anand.verma on 08/05/18.
 */

public class PathShape extends Shape
{
	private Vector2D moveTo;
	private Vector2D quadTo1;
	private Vector2D quadTo2;

	public PathShape(Vector2D moveTo, Vector2D quadTo1, Vector2D quadTo2,
			PaintProperties paintProperties, Vector2D delta)
	{
		super(ShapeType.PATH, paintProperties, delta);
		this.moveTo = moveTo;
		this.quadTo1 = quadTo1;
		this.quadTo2 = quadTo2;
	}

	@Override
	public float getLeft()
	{
		return moveTo.getX();
	}

	@Override
	public float getRight()
	{
		return quadTo2.getX();
	}

	@Override
	public float getTop()
	{
		return moveTo.getY();
	}

	@Override
	public float getBottom()
	{
		return quadTo2.getY();
	}

	public Vector2D getMoveTo()
	{
		return moveTo;
	}

	public Vector2D getQuadTo1()
	{
		return quadTo1;
	}

	public Vector2D getQuadTo2()
	{
		return quadTo2;
	}
}
