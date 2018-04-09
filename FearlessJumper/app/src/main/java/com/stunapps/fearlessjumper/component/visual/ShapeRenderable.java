package com.stunapps.fearlessjumper.component.visual;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Vector2D;

import java.util.List;

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

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new ShapeRenderable(shapes, delta);
	}
}

