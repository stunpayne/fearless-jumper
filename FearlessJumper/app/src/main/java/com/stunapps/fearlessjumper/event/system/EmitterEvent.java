package com.stunapps.fearlessjumper.event.system;

import com.stunapps.fearlessjumper.entity.Entity;

/**
 * Created by anand.verma on 08/03/18.
 */

public class EmitterEvent extends SystemEvent
{
	public Entity entity;

	public EmitterEvent(Entity entity)
	{
		super(EmitterEvent.class);
		this.entity = entity;
	}
}
