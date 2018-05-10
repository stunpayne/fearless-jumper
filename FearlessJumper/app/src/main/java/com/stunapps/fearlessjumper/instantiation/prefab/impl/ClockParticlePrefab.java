package com.stunapps.fearlessjumper.instantiation.prefab.impl;

import com.stunapps.fearlessjumper.component.emitter.CircularEmitter;

/**
 * Created by anand.verma on 08/03/18.
 */

public class ClockParticlePrefab extends ComponentPrefab
{
	public ClockParticlePrefab()
	{
		addComponent(new CircularEmitter());
	}
}
