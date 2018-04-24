package com.stunapps.fearlessjumper.helper;

import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by sunny.s on 25/04/18.
 */

public class Triangle
{
	public static Vector2D centroid(Vector2D[] vertices)
	{
		if (vertices.length == 3)
		{
			float x = (vertices[0].getX() + vertices[1].getX() + vertices[2].getX()) / 3;
			float y = (vertices[0].getY() + vertices[1].getY() + vertices[2].getY()) / 3;
			return new Vector2D(x, y);
		}
		return null;
	}
}
