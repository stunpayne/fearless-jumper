package com.stunapps.fearlessjumper.manager;

import android.os.Handler;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.collider.Collider;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.Set;


/**
 * Created by anand.verma on 01/03/18.
 */


/**
 * Collision manager can maintain groups of layer or layer independently.
 */
@Singleton
public class CollisionLayerManager
{
	private int matrixSize;

	private Boolean[][] collisionMatrix;

	private Handler handler = new Handler();

	public CollisionLayerManager()
	{
		CollisionLayer[] collisionLayers = CollisionLayer.values();
		matrixSize = collisionLayers.length;
		collisionMatrix = new Boolean[matrixSize][matrixSize];

		for (int i = 0; i < matrixSize; i++)
		{
			for (int j = 0; j < matrixSize; j++)
			{
				collisionMatrix[i][j] = false;
			}
		}

		/*
		 * SOLID - collides with ENEMY, PLAYER
		 * ENEMY - collides with PLAYER
		 * PLAYER - collides with SOLID, ENEMY, BONUS
		 * BONUS - collides with PLAYER, BONUS
		 */

		setCollisionLayerMask(CollisionLayer.ENEMY, new CollisionLayer[]{CollisionLayer.SOLID});


		setCollisionLayerMask(CollisionLayer.PLAYER,
				new CollisionLayer[]{CollisionLayer.SOLID, CollisionLayer.ENEMY, CollisionLayer
						.SUPER_ENEMY, CollisionLayer.BONUS, CollisionLayer.BULLET, CollisionLayer
						.LAND});
	}

	public void setCollisionLayerMask(CollisionLayer collisionLayer,
			CollisionLayer collidesWithLayer)
	{
		setCollisionLayerMask(collisionLayer, new CollisionLayer[]{collidesWithLayer});
	}

	public void setCollisionLayerMask(CollisionLayer collisionLayer,
			CollisionLayer[] collidesWithLayers)
	{
		for (CollisionLayer collidesWithLayer : collidesWithLayers)
		{
			Coordinate coordinate = getCoordinate(collisionLayer, collidesWithLayer);
			setMask(coordinate);
		}
	}

	public void unsetCollisionLayerMask(CollisionLayer collisionLayer,
			CollisionLayer collidesWithLayer)
	{
		unsetCollisionLayerMask(collisionLayer, new CollisionLayer[]{collidesWithLayer});
	}

	public void unsetCollisionLayerMask(CollisionLayer collisionLayer,
			CollisionLayer[] collidesWithLayers)
	{
		for (CollisionLayer collidesWithLayer : collidesWithLayers)
		{
			Coordinate coordinate = getCoordinate(collisionLayer, collidesWithLayer);
			unsetMask(coordinate);
		}
	}

	public void timedFlipCollisionLayerMask(CollisionLayer collisionLayer1,
			CollisionLayer collisionLayer2, long flipDuration)
	{
		final Coordinate coordinate = getCoordinate(collisionLayer1, collisionLayer2);
		flipMask(coordinate);
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				flipMask(coordinate);
			}
		}, flipDuration);
	}

	public boolean isCollisionMaskSet(Collider collider, Collider collidesWith)
	{
		Coordinate coordinate = getCoordinate(collider.collisionLayer, collidesWith
				.collisionLayer);
		return collisionMatrix[coordinate.x][coordinate.y];
	}

	private void setMask(Coordinate coordinate)
	{
		collisionMatrix[coordinate.x][coordinate.y] = true;
	}

	private void unsetMask(Coordinate coordinate)
	{
		collisionMatrix[coordinate.x][coordinate.y] = false;
	}

	private void flipMask(Coordinate coordinate)
	{
		collisionMatrix[coordinate.x][coordinate.y] = !collisionMatrix[coordinate.x][coordinate.y];
	}

	private Coordinate getCoordinate(CollisionLayer collisionLayer1, CollisionLayer
			collisionLayer2)
	{

		if (collisionLayer1.getIndex() < collisionLayer2.getIndex())
		{
			return new Coordinate(matrixSize - collisionLayer2.getIndex() - 1,
					collisionLayer1.getIndex());
		}
		else
		{
			return new Coordinate(matrixSize - collisionLayer1.getIndex() - 1,
					collisionLayer2.getIndex());
		}
	}

	private class Coordinate
	{
		public int x;
		public int y;

		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		CollisionLayer[] collisionLayers = CollisionLayer.values();
		int size = collisionLayers.length;

		sb.append("\t\t\t");
		for (int i = 0; i < size; i++)
		{
			sb.append(collisionLayers[i] + "\t\t");
		}
		sb.append("\n");

		for (int i = 0; i < size; i++)
		{
			sb.append(collisionLayers[size - i - 1] + "\t\t");
			for (int j = 0; j < size; j++)
			{
				sb.append(collisionMatrix[i][j] + "\t\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
