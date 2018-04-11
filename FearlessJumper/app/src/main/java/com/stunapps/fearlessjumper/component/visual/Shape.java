package com.stunapps.fearlessjumper.component.visual;

import android.graphics.PathEffect;

import com.stunapps.fearlessjumper.model.Vector2D;

import lombok.Setter;

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

	public static class PaintProperties
	{
		private PathEffect pathEffect;
		private int color;

		private PathEffect currentPathEffect;
		private int currentColor;

		public PaintProperties(PathEffect pathEffect, int color)
		{
			this.pathEffect = pathEffect;
			this.currentPathEffect = pathEffect;

			this.color = color;
			this.currentColor = color;
		}

		public PathEffect getPathEffect()
		{
			return currentPathEffect;
		}

		public int getColor()
		{
			return currentColor;
		}
	}

	public enum ShapeType
	{
		RECT, CIRCLE, LINE;
	}
}
