package com.stunapps.fearlessjumper.component.collider;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.transform.Position;

/**
 * Created by anand.verma on 12/01/18.
 */

public abstract class Collider extends Component
{
	public Delta delta;
	public float width;
	public float height;
	protected boolean trigger;

	public Collider(Delta delta, float width, float height)
	{
		super(Collider.class);
		this.delta = delta;
		this.width = width;
		this.height = height;
		this.trigger = false;
	}

	public Collider(Delta delta, float width, float height, boolean trigger)
	{
		super(Collider.class);
		this.delta = delta;
		this.width = width;
		this.height = height;
		this.trigger = trigger;
	}

	public Position getCenter(Position position)
	{
		//Position position = entity.transform.position;
		return new Position(position.x + delta.x + width / 2, position.y + delta.y + height / 2);
	}

	public boolean isTrigger()
	{
		return trigger;
	}
}
