package com.stunapps.fearlessjumper.component.visual;

import android.graphics.PathEffect;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by anand.verma on 09/04/18 10:27 PM.
 */
abstract public class Shape
{
	private ShapeType shapeType;
	private PaintProperties paintProperties;
	private Vector2D delta;

	public Shape(ShapeType shapeType, PaintProperties paintProperties, Vector2D delta)
	{
		this.shapeType = shapeType;
		this.paintProperties = paintProperties;
		this.delta = delta;
	}

	public ShapeType shapeType()
	{
		return shapeType;
	}

	public Vector2D getDelta()
	{
		return delta;
	}

	public PaintProperties getPaintProperties()
	{
		return paintProperties;
	}

	abstract public float getLeft();

	abstract public float getRight();

	abstract public float getTop();

	abstract public float getBottom();

	public class PaintProperties
	{
		private PathEffect pathEffect;
		private int color;

		public PathEffect getPathEffect()
		{
			return pathEffect;
		}

		public int getColor()
		{
			return color;
		}
	}

	public enum ShapeType
	{
		RECT, CIRCLE, LINE, PATH;
	}
}
