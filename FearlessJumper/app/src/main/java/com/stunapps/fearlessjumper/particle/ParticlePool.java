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
		this(100, 1000);
	}

	public ParticlePool(int startPoolSize, int maxPoolSize)
	{
		super(startPoolSize, maxPoolSize);
	}

	@Override
	protected Particle createObject()
	{
		long particleId = random.nextLong();
		return new Particle(particleId);
	}
}
