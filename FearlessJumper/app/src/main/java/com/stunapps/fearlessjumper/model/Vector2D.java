package com.stunapps.fearlessjumper.model;

/**
 * Created by sunny.s on 17/03/18.
 */

public class Vector2D
{
	private float x;
	private float y;

	public Vector2D()
	{
		this(0, 0);
	}

	public static Vector2D from(Vector2D other)
	{
		return new Vector2D(other.x, other.y);
	}

	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public static Vector2D add(Vector2D vector1, Vector2D vector2)
	{
		if (vector1 == null)
		{
			return vector2;
		}
		else if (vector2 == null)
		{
			return vector1;
		}
		return new Vector2D(vector1.x + vector2.x, vector1.y + vector2.y);
	}

	public static Vector2D minus(Vector2D original, Vector2D difference)
	{
		return new Vector2D(original.x - difference.x, original.y - difference.y);
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public Vector2D minus(Vector2D other)
	{
		return new Vector2D(this.x - other.getX(), this.y - other.getY());
	}

	public void add(Vector2D other)
	{
		x += other.x;
		y += other.y;
	}

	@Override
	protected Vector2D clone() throws CloneNotSupportedException
	{
		return new Vector2D(x, y);
	}

	@Override
	public String toString()
	{
		return "Vector2D{" + "x=" + x + ", y=" + y + '}';
	}
}
