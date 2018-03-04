package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.stunapps.fearlessjumper.helper.Environment.Device;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.Iterator;
import java.util.List;

/**
 * Created by anand.verma on 02/03/18.
 */

public class CircularEmitter extends BaseEmitter
{
	public CircularEmitter()
	{
		super(CircularEmitter.class, 20, 1000l, 0);
	}

	@Override
	public void init()
	{
		super.init();
	}

	@Override
	protected void setupParticleCluster(List<Particle> particles)
	{
		int size = particles.size();
		float angle = 0;
		float angleDelta = 360.0f / size;
		for (Particle particle : particles)
		{
			particle.setPosition(Device.SCREEN_WIDTH / 2, Device.SCREEN_HEIGHT / 2);

			particle.setVelocity(angle, 20f);
			angle += angleDelta;
			angle = angle % 360;
		}
	}

	@Override
	public void update(long delta)
	{
		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext())
		{
			Particle particle = iterator.next();
			boolean aliveAfterUpdate = particle.update(delta);
			if (aliveAfterUpdate)
			{
				if (particle.lifeTimer < 97 * particle.life / 100)
				{
					particle.scaleVelocity(0.8f);
				}
			}
			else
			{
				iterator.remove();
				destroyParticle(particle);
			}
		}
	}
}
