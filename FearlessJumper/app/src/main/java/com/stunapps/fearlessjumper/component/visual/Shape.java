package com.stunapps.fearlessjumper.component.visual;

/**
 * Created by anand.verma on 09/04/18 10:27 PM.
 */
abstract class Shape
{
	private ShapeType shapeType;

	public ShapeType shapeType()
	{
		return shapeType;
	}

	public enum ShapeType
	{
		RECT,
		CIRCLE,
		LINE,
		PATH;
	}
}
