package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Velocity;
import com.stunapps.fearlessjumper.particle.AlphaCalculator;
import com.stunapps.fearlessjumper.particle.Particle;
import com.stunapps.fearlessjumper.particle.VelocityScaler;

import java.util.List;

/**
 * Created by anand.verma on 02/03/18.
 */

public class CircularEmitter extends BaseClusteredEmitter
{
	public CircularEmitter()
	{
		super(20, 1000l, 0);
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
			particle.setPosition(entity.transform.position.x, entity.transform.position.y);
			particle.setVelocity(angle, 20f);
			particle.setAlpha(new AlphaCalculator()
			{
				@Override
				public float calculate(float life, float lifeTimer)
				{
					if (lifeTimer < 2 * life / 5)
					{
						return 2 * lifeTimer / life;
					}
					return 1;
				}
			});
			particle.setVelocityScaler(new VelocityScaler()
			{
				@Override
				public Velocity scale(Velocity velocity, float life, float lifeTimer)
				{
					if (lifeTimer < 97 * life / 100)
					{
						velocity.x *= 0.8f;
						velocity.y *= 0.8f;
					}
					return velocity;
				}
			});
			angle += angleDelta;
			angle = angle % 360;
		}
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new CircularEmitter();
	}
}
