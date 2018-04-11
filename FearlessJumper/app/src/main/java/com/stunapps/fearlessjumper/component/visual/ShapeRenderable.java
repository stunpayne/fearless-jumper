package com.stunapps.fearlessjumper.component.visual;

import android.graphics.Color;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.visual.Shape.PaintProperties;
import com.stunapps.fearlessjumper.model.Vector2D;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by anand.verma on 09/04/18 10:25 PM.
 */

public class ShapeRenderable extends Component
{
	private List<Shape> shapes;
	private Vector2D delta;

	private Float width;
	private Float height;

	public ShapeRenderable(List<Shape> shapes, Vector2D delta)
	{
		super(ShapeRenderable.class);
		this.shapes = shapes;
		this.delta = delta;
	}

	public List<Shape> getRenderables()
	{
		return shapes;
	}

	public float getWidth()
	{
		if (null != width)
		{
			return width;
		}

		float leftMost = Float.MAX_VALUE;
		float rightMost = Float.MIN_VALUE;

		for (Shape shape : shapes)
		{
			leftMost = Math.min(leftMost, shape.getLeft());
			rightMost = Math.max(rightMost, shape.getRight());
		}

		width = rightMost - leftMost;
		return width;
	}

	public float getHeight()
	{
		if (null != height)
		{
			return height;
		}

		float topMost = Float.MAX_VALUE;
		float bottomMost = Float.MIN_VALUE;

		for (Shape shape : shapes)
		{
			topMost = Math.min(topMost, shape.getTop());
			bottomMost = Math.max(bottomMost, shape.getBottom());
		}

		height = bottomMost - topMost;
		return height;
	}

	public Vector2D getDelta()
	{
		return delta;
	}

	public void increaseDelta(Vector2D increment)
	{
		delta.add(increment);
	}

	@Override
	public ShapeRenderable clone() throws CloneNotSupportedException
	{
		return new ShapeRenderable(new LinkedList<>(shapes), Vector2D.from(delta));
	}
}

