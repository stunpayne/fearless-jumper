package com.stunapps.fearlessjumper.component.movement;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.entity.Entity;

/**
 * This component can be used to provide automated periodic translation
 * to an entity. If an x movement is provided, the entity will move
 * periodically in the x axis between the bounds provided at the mentioned speed.
 * Should only be used on an entity that has a PhysicsComponent
 * <p>
 * Created by sunny.s on 21/01/18.
 */

public class PeriodicTranslation extends Component
{
	//  Speed at which the entity will move
	private float speedX = 0.0f;

	//  Type of movement in x axis
	private MovementType xMovementType = MovementType.NONE;

	//  Max deviation leftward from initial X position
	public float minX = 0.0f;

	//  Max deviation rightward from initial X position
	public float maxX = 0.0f;

	//  X Anchor of the entity, used only if movement type is ANCHORED
	//  The minX and maxX fields of this class will be used as distances from this position
	private float anchorX = 0.0f;

	//  Distance to travel in x direction from the anchor position
	private float deltaX = 0.0f;

	//  Denotes whether the entity has an x movement
	//  Set to true automatically upon providing an x movement
	private boolean movesInX = false;


	//  Speed at which the entity will move
	private float speedY = 0.0f;

	//  Type of movement in y axis
	private MovementType yMovementType = MovementType.NONE;

	//  Max deviation upward from initial X position
	public float minY = 0.0f;

	//  Max deviation downward from initial X position
	public float maxY = 0.0f;

	//  Y Anchor of the entity, used only if movement type is ANCHORED
	//  The minY and maxY fields of this class will be used as distances from this position
	private float anchorY = 0.0f;

	//  Distance to travel in y direction from the anchor position
	private float deltaY = 0.0f;

	//  Denotes whether the entity has a y movement
	//  Set to true automatically upon providing a y movement
	private boolean movesInY = false;

	public PeriodicTranslation()
	{
		super(PeriodicTranslation.class);
	}

	public PeriodicTranslation withAbsoluteXMovement(float minX, float maxX, float speedX)
	{
		this.minX = minX;
		this.maxX = maxX;
		this.speedX = speedX;
		setXMovementType(MovementType.ABSOLUTE);

		this.movesInX = true;

		return this;
	}

	public PeriodicTranslation withAbsoluteYMovement(float minY, float maxY, float speedY)
	{
		this.minY = minY;
		this.maxY = maxY;
		this.speedY = speedY;
		setYMovementType(MovementType.ABSOLUTE);

		this.movesInY = true;

		return this;
	}

	//  If the parent entity is set later, the anchorX will be overridden then
	public PeriodicTranslation withAnchoredXMovement(float speedX, float deltaX)
	{
		this.speedX = speedX;
		this.deltaX = deltaX;
		setXMovementType(MovementType.ANCHORED);

		this.movesInX = true;

		return this;
	}

	//  If the parent entity is set later, the anchorY will be overridden then
	public PeriodicTranslation withAnchoredYMovement(float speedY, float deltaY)
	{
		this.speedY = speedY;
		this.deltaY = deltaY;
		setYMovementType(MovementType.ANCHORED);

		this.movesInY = true;

		return this;
	}

	public float getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(float speedX)
	{
		this.speedX = speedX;
	}

	public float getSpeedY()
	{
		return speedY;
	}

	public void setSpeedY(float speedY)
	{
		this.speedY = speedY;
	}

	public boolean movesInX()
	{
		return movesInX;
	}

	public boolean movesInY()
	{
		return movesInY;
	}

	private void setXMovementType(MovementType xMovementType)
	{
		if (this.xMovementType == MovementType.NONE) this.xMovementType = xMovementType;
		else throw new IllegalArgumentException();
	}

	private void setYMovementType(MovementType yMovementType)
	{
		if (this.yMovementType == MovementType.NONE) this.yMovementType = yMovementType;
		else throw new IllegalArgumentException();
	}

	@Override
	public void setEntity(Entity entity)
	{
		this.entity = entity;

		if (xMovementType == MovementType.ANCHORED)
		{
			this.anchorX = entity.transform.position.x;

			this.minX = anchorX - deltaX;
			this.maxX = anchorX + deltaX;
		}

		if (yMovementType == MovementType.ANCHORED)
		{
			this.anchorY = entity.transform.position.y;

			this.minY = anchorY - deltaY;
			this.maxY = anchorY + deltaY;
		}
	}

	@Override
	public PeriodicTranslation cloneComponent() throws CloneNotSupportedException
	{
		PeriodicTranslation clone = new PeriodicTranslation();
		if (movesInX())
		{
			if (xMovementType == MovementType.ABSOLUTE)
				clone = clone.withAbsoluteXMovement(minX, maxX, speedX);
			if (xMovementType == MovementType.ANCHORED)
				clone = clone.withAnchoredXMovement(speedX, deltaX);
		}
		if (movesInY())
		{
			if (yMovementType == MovementType.ABSOLUTE)
				clone = clone.withAbsoluteYMovement(minY, maxY, speedY);
			if (yMovementType == MovementType.ANCHORED)
				clone = clone.withAnchoredYMovement(speedY, deltaY);
		}
		return clone;
	}

	public enum MovementType
	{
		NONE, ABSOLUTE, ANCHORED;
	}
}
