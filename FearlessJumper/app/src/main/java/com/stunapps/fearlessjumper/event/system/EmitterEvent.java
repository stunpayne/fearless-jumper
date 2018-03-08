package com.stunapps.fearlessjumper.event.system;

import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.HashMap;
import java.util.Map;

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
