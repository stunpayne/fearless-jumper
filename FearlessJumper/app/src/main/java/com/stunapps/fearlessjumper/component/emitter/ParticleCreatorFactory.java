package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterShape;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.ParticleInitializer;

/**
 * Created by sunny.s on 17/03/18.
 */

public class ParticleCreatorFactory
{
	private ConeDivergeParticleInitializer coneDivergeParticleInitializer =
			new ConeDivergeParticleInitializer();

	public ParticleInitializer getInitializer(EmitterConfig emitterConfig)
	{
		EmitterShape emitterShape = emitterConfig.getEmitterShape();
		switch (emitterShape)
		{
			case CONE_DIVERGE:
				return coneDivergeParticleInitializer;
			default:
				return coneDivergeParticleInitializer;
		}
	}
}
