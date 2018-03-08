package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.emitter.CircularEmitter;

/**
 * Created by anand.verma on 08/03/18.
 */

public class ClockParticlePrefab extends Prefab
{
	public ClockParticlePrefab()
	{
		components.add(new CircularEmitter());
	}
}
