package com.stunapps.fearlessjumper.particle;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.core.ObjectPool;

/**
 * Created by anand.verma on 03/03/18.
 */

@Singleton
public class ParticlePool extends ObjectPool<Particle>
{
	public ParticlePool()
	{
		super(100, 1000);
	}

	@Override
	protected Particle createObject()
	{
		return new Particle();
	}
}