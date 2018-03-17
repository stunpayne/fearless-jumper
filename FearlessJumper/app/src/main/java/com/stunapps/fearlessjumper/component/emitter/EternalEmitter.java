package com.stunapps.fearlessjumper.component.emitter;

import android.util.Log;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.particle.AlphaCalculator;
import com.stunapps.fearlessjumper.particle.Particle;
import com.stunapps.fearlessjumper.particle.ParticlePool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by sunny.s on 15/03/18.
 */

public class EternalEmitter extends Emitter
{
	private static final String TAG = EternalEmitter.class.getSimpleName();

	protected int id;

	//	Max number of particles that can be alive at once
	protected int maxParticles;
	//	Duration for which each particle is alive
	protected long particleLife;
	//	Particles per second
	protected int emissionRate;
	//	Max speed of every particle
	protected float maxSpeed;
	//	Max variation in particle generation position, on either side, from the entity position
	protected float positionVar;
	//	Direction, in degrees, in which the particles should move
	protected float direction;
	//	Max variation in angle, on either side, from the direction property
	protected float directionVar;
	//	Shape in which particles have to be emitted
	protected EmitterShape emitterShape;

	private Set<Particle> particles;
	private ParticlePool particlePool;

	private boolean initialised;
	//	Time between emission of two particles
	private float emissionInterval;
	//	Leftover emission time from previous interval
	private float balanceEmissionTime = 0f;

	private Random random = new Random();

	public enum EmitterShape
	{
		CONE_DIVERGE, CONE_CONVERGE;
	}

	EternalEmitter(EmitterShape emitterShape, int maxParticles, long particleLife, int
			emissionRate,
			float positionVar, float maxSpeed, float direction, float directionVar)
	{
		this(emitterShape, positionVar, maxParticles, particleLife, emissionRate);
		this.maxSpeed = maxSpeed;
		this.direction = direction;
		this.directionVar = directionVar;
	}

	EternalEmitter(EmitterShape emitterShape, float positionVar, int maxParticles,
			long particleLife, int emissionRate)
	{
		this(emitterShape, maxParticles, particleLife, emissionRate);
		this.positionVar = positionVar;
	}

	EternalEmitter(EmitterShape emitterShape, int maxParticles, long particleLife, int
			emissionRate)
	{
		super();
		id = new Random().nextInt(50);
		particlePool = new ParticlePool();
		particles = new HashSet<>();

		this.maxParticles = maxParticles;
		this.particleLife = particleLife;
		this.emissionRate = emissionRate;
		this.maxSpeed = 0;
		this.direction = 90f;
		this.directionVar = 5f;
		this.emitterShape = emitterShape;

		emissionInterval = ((float) Time.ONE_SECOND_NANOS / emissionRate);
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new EternalEmitter(emitterShape, maxParticles, particleLife, emissionRate,
				positionVar, maxSpeed, direction, directionVar);
	}

	@Override
	public boolean isInitialised()
	{
		return initialised;
	}

	@Override
	public long getId()
	{
		return id;
	}

	@Override
	public void update(long dt)
	{
		//	Use balance emission time from previous frame
		dt += balanceEmissionTime;
		balanceEmissionTime = 0;

		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext())
		{
			Particle particle = iterator.next();
			boolean aliveAfterUpdate = particle.update(dt);
			if (!aliveAfterUpdate)
			{
				iterator.remove();
				destroyParticle(particle);
			}
		}

		while (canEmitParticle() && dt > emissionInterval)
		{
			Log.d(TAG, "Adding particle");
			Particle particle = createNewParticle();
			particles.add(particle);
			dt -= emissionInterval;
		}

		//	Save unused balance time from this frame to be used in the next frame
		balanceEmissionTime += dt;
	}

	@Override
	public Set<Particle> getParticles()
	{
		return particles;
	}

	@Override
	public void init()
	{
		particles.clear();
		initialised = true;
	}

	@Override
	public boolean isExhausted()
	{
		return particles.size() == maxParticles;
	}

	@Override
	void destroyParticle(Particle particleToDestroy)
	{
		//		Log.d(TAG, "Destroying particle: " + particleToDestroy);
		particleToDestroy.reset();
		particlePool.returnObject(particleToDestroy);
	}

	private boolean canEmitParticle()
	{
		return particles.size() < maxParticles;
	}

	private Particle createNewParticle()
	{
		Particle particle = particlePool.getObject();
		particle.setLife(particleLife);
		Position position = newParticlePosition();
		particle.setPosition(position);
		particle.setVelocity(newParticleDirection(), newParticleSpeed());
		particle.setAlpha(new AlphaCalculator()
		{
			@Override
			public float calculate(float life, float lifeTimer)
			{
				return lifeTimer / life;
			}
		});

		//		Log.d(TAG, "New particle: " + particle);

		return particle;
	}


	private Position newParticlePosition()
	{
		Position entityPosition = entity.getTransform().getPosition();
		return new Position(entityPosition.getX() + positionVar * twoWayRandom(),
				entityPosition.getY());
	}

	/**
	 * nextFloat returns a value between 0 and 1.
	 * We modify this value to get a value between -1 and 1, and then multiply that
	 * by the direction variation property. The result is added to the direction property
	 * to get the direction in which the particle has to be projected.
	 *
	 * @return
	 */
	private float newParticleDirection()
	{
		return directionVar * twoWayRandom() + direction;
	}

	//	TODO:	Introduce and use speedVarBias
	private float newParticleSpeed()
	{
		return maxSpeed * random.nextFloat();
	}

	int getLiveParticles()
	{
		return particles.size();
	}

	private float twoWayRandom()
	{
		return 2 * (0.5f - random.nextFloat());
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		EmitterShape emitterShape;
		int maxParticles;
		int particleLife;
		int emissionRate;
		float positionVar;
		float maxSpeed;
		float direction;
		float directionVar;

		public Builder emitterShape(EmitterShape emitterShape)
		{
			this.emitterShape = emitterShape;
			return this;
		}

		public Builder maxParticles(int maxParticles)
		{
			this.maxParticles = maxParticles;
			return this;
		}

		public Builder particleLife(int particleLife)
		{
			this.particleLife = particleLife;
			return this;
		}

		public Builder emissionRate(int emissionRate)
		{
			this.emissionRate = emissionRate;
			return this;
		}

		public Builder positionVar(float positionVar)
		{
			this.positionVar = positionVar;
			return this;
		}

		public Builder maxSpeed(float maxSpeed)
		{
			this.maxSpeed = maxSpeed;
			return this;
		}

		public Builder direction(float direction)
		{
			this.direction = direction;
			return this;
		}

		public Builder directionVar(float directionVar)
		{
			this.directionVar = directionVar;
			return this;
		}

		public EternalEmitter build()
		{
			return new EternalEmitter(emitterShape, maxParticles, particleLife, emissionRate,
					positionVar, maxSpeed, direction, directionVar);
		}
	}
}
