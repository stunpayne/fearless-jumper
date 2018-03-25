package com.stunapps.fearlessjumper.component.emitter;

import android.util.Log;

import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.EmitterConfig;
import com.stunapps.fearlessjumper.component.emitter.Emitter.RenderMode;
import com.stunapps.fearlessjumper.component.emitter.EternalEmitter.ParticleInitializer;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.particle.AlphaCalculator;
import com.stunapps.fearlessjumper.particle.ColorTransitioner;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.Random;

/**
 * Created by sunny.s on 17/03/18.
 */

public class ConeDivergeParticleInitializer implements ParticleInitializer
{
	private static final String TAG = ConeDivergeParticleInitializer.class.getSimpleName();

	private Random random = new Random();

	@Override
	public void init(Entity entity, Particle particle, EmitterConfig config)
	{
		particle.setLife(config.getParticleLife());
		Position position = newParticlePosition(entity, config);
		particle.setPosition(position);
		particle.setVelocity(newParticleDirection(config), newParticleSpeed(config));
		particle.setAlpha(new AlphaCalculator()
		{
			@Override
			public float calculate(float life, float lifeTimer)
			{
				return lifeTimer / life;
			}
		});
		particle.setColorTransitioner(new ColorTransitioner()
		{
			@Override
			public int transition(int startColor, int endColor, int currentColor, float life,
					float lifeTimer)
			{
				return (int) (startColor + ((1 - (lifeTimer / life)) * (endColor - startColor)));
			}
		});
		if (config.getRenderMode() == RenderMode.SHAPE)
		{
			particle.setStartColor(config.getStartColor());
			particle.setEndColor(config.getEndColor());
		}
	}

	private Position newParticlePosition(Entity entity, EmitterConfig config)
	{
		Position entityPosition = entity.getTransform().getPosition();
		//		return new Position(entityPosition.getX() + config.getPositionVar().getX() *
		//				twoWayRandom(),
		//				entityPosition.getY() + 15 * twoWayRandom());
		return new Position(entityPosition.getX() + config.getOffset().getX() +
				twoWayRandom(config.getPositionVar().getX()),
				entityPosition.getY() + config.getOffset().getY() +
						twoWayRandom(config.getPositionVar().getY()));
	}

	/**
	 * nextFloat returns a value between 0 and 1.
	 * We modify this value to get a value between -1 and 1, and then multiply that
	 * by the direction variation property. The result is added to the direction property
	 * to get the direction in which the particle has to be projected.
	 *
	 * @return
	 */
	private float newParticleDirection(EmitterConfig emitterConfig)
	{
		return emitterConfig.getDirectionVar() * twoWayRandom() + emitterConfig.getDirection();
	}

	//	TODO:	Introduce and use speedVarBias
	private float newParticleSpeed(EmitterConfig config)
	{
		return config.getMaxSpeed() * random.nextFloat();
	}

	private float twoWayRandom(float value)
	{
		return value * twoWayRandom();
	}

	private float twoWayRandom()
	{
		return 2 * (0.5f - random.nextFloat());
	}
}
