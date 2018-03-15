package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.List;

/**
 * Created by sunny.s on 15/03/18.
 */

public class TestEmitter extends BaseEmitter
{
	public TestEmitter(int particlesCount,
			long particleLife, long emissionInterval)
	{
		super(particlesCount, particleLife, emissionInterval);
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return null;
	}

	@Override
	void setupParticleCluster(List<Particle> particles)
	{

	}
}
