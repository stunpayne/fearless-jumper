package com.stunapps.fearlessjumper.event.system;

import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.transform.Transform;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anand.verma on 08/03/18.
 */

public class EmitterEvent extends SystemEvent
{
	HashMap<Emitter, Transform> emitters;

	public EmitterEvent(HashMap<Emitter, Transform> emitters)
	{
		super(EmitterEvent.class);
		this.emitters = emitters;
	}

	public Map<Emitter, Transform> getEmitters()
	{
		return emitters;
	}
}
