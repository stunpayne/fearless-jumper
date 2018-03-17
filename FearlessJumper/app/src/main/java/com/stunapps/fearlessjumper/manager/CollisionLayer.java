package com.stunapps.fearlessjumper.manager;

/**
 * Created by anand.verma on 02/03/18.
 */

public enum CollisionLayer
{
	SOLID(0),
	PLAYER(1),
	ENEMY(2),
	SUPER_ENEMY(3),
	BULLET(4),
	BONUS(5),
	LAND(6);

	private int index;

	CollisionLayer(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return index;
	}

	public static CollisionLayer getCollisionLayer(int i)
	{
		if (SOLID.getIndex() == i)
		{
			return SOLID;
		}
		else if (ENEMY.getIndex() == i)
		{
			return ENEMY;
		}
		else if (PLAYER.getIndex() == i)
		{
			return PLAYER;
		}
		else if (BONUS.getIndex() == i)
		{
			return BONUS;
		}
		else
		{
			//TODO: throw exception.
			throw new RuntimeException("Invalid collision layer index.");
		}
	}
}
