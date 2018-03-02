package com.stunapps.fearlessjumper.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by sunny.s on 19/01/18.
 */

@ToString
@Getter
@Setter
public class Position
{
	public float x;
	public float y;

	public static Position ORIGIN = new Position(0, 0);

	public Position()
	{
		x = y = 0;
	}

	public Position(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Position(Position other)
	{
		this.x = other.x;
		this.y = other.y;
	}

	public void copyTo(Position other)
	{
		other.x = this.x;
		other.y = this.y;
	}

	public Position distanceFrom(Position other)
	{
		return new Position(this.x - other.x, this.y - other.y);
	}

	public boolean isBelow(Position other)
	{
		return this.y > other.y;
	}

	public boolean isAbove(Position other)
	{
		return this.y < other.y;
	}
}
