package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.List;

/**
 * Created by anand.verma on 04/03/18.
 */

public class RotationalEmitter extends BaseEmitter
{
	public RotationalEmitter()
	{
		super(RotationalEmitter.class, 8, 6000l, 0);
	}

	@Override
	void setupParticleCluster(List<Particle> particles)
	{
		int size = particles.size();
		float angle = 0;
		float angleDelta = 360.0f / size;
		for (Particle particle : particles)
		{
			particle.setPosition(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2);
			particle.setVelocity(angle, 1f);
			particle.setAcceleration(90, 2f);
			angle += angleDelta;
			angle = angle % 360;
		}
	}
}
