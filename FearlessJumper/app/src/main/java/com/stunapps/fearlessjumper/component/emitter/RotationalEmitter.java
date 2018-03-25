package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.List;
import java.util.Random;

/**
 * Created by anand.verma on 04/03/18.
 */

public class RotationalEmitter extends BaseClusteredEmitter
{
	public RotationalEmitter()
	{
		super(8, 6000l, 0);
	}

	@Override
	public Bitmap getTexture()
	{
		return null;
	}

	@Override
	void setupParticleCluster(List<Particle> particles)
	{
		int size = particles.size();
		float angle = 0;
		float angleDelta = 360.0f / size;
		Random random = new Random();
		for (Particle particle : particles)
		{
			particle.setPosition(Device.SCREEN_WIDTH / 2 + (40 - (random.nextInt() % 81)), Device
					.SCREEN_HEIGHT
					/ 2);
			particle.setVelocity(angle, 0f);
			particle.setAcceleration(90, 1f);
			angle += angleDelta;
			angle = angle % 360;
		}
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new RotationalEmitter();
	}
}
