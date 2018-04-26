package com.stunapps.fearlessjumper.component.collider;

import com.stunapps.fearlessjumper.manager.CollisionLayer;
import com.stunapps.fearlessjumper.model.Vector2D;

/**
 * Created by anand.verma on 12/01/18.
 */

public class RectCollider extends Collider
{
	public RectCollider(Vector2D delta, float width, float height, CollisionLayer collisionLayer)
	{
		super(delta, width, height, collisionLayer);
	}

	public RectCollider(Vector2D delta, float width, float height, boolean trigger,
			CollisionLayer collisionLayer)
	{
		super(delta, width, height, trigger, collisionLayer);
	}

	@Override
	public RectCollider clone() throws CloneNotSupportedException
	{
		return new RectCollider(this.delta, this.width, this.height, this.trigger,
				this.collisionLayer);
	}
}
