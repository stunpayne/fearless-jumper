package com.stunapps.fearlessjumper.component.collider;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by anand.verma on 12/01/18.
 */

public abstract class Collider extends Component
{
	public boolean isActive;
	public CollisionLayer collisionLayer;
	public Vector2D delta;
	public float width;
	public float height;

	/**
	 * Denotes whether the collider is meant to be ignored for collision resolution or not
	 * If true, the collider is ignored for collision resolution
	 */
	protected boolean trigger;

	public Collider(Vector2D delta, float width, float height, CollisionLayer collisionLayer)
	{
		this(delta, width, height, false, collisionLayer);
	}

	public Collider(Vector2D delta, float width, float height, boolean trigger,
			CollisionLayer collisionLayer)
	{
		super(Collider.class);
		this.delta = delta;
		this.width = width;
		this.height = height;
		this.trigger = trigger;
		this.isActive = true;
		this.collisionLayer = collisionLayer;
	}

	public Position getCenter(Position position)
	{
		//Position position = entity.transform.position;
		return new Position(position.x + delta.getX() + width / 2,
				position.y + delta.getY() + height / 2);
	}

	public boolean isTrigger()
	{
		return trigger;
	}

	public void setTrigger(boolean trigger)
	{
		this.trigger = trigger;
	}
}
