package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Time;
import com.stunapps.fearlessjumper.particle.Particle;
import com.stunapps.fearlessjumper.particle.ParticlePool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import lombok.Getter;

/**
 * Created by sunny.s on 15/03/18.
 */

public class EternalEmitter extends Emitter
{
	@Getter
	public static class EmitterConfig
	{
		//	Shape in which particles have to be emitted
		protected EmitterShape emitterShape;
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

		public EmitterConfig(EmitterShape emitterShape, int maxParticles, int particleLife,
				int emissionRate, float positionVar, float maxSpeed, float direction,
				float directionVar)
		{
			this.emitterShape = emitterShape;
			this.maxParticles = maxParticles;
			this.particleLife = particleLife;
			this.emissionRate = emissionRate;
			this.positionVar = positionVar;
			this.maxSpeed = maxSpeed;
			this.direction = direction;
			this.directionVar = directionVar;
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

			public EmitterConfig build()
			{
				return new EmitterConfig(emitterShape, maxParticles, particleLife, emissionRate,
						positionVar, maxSpeed, direction, directionVar);
			}
		}
	}

	public enum EmitterShape
	{
		CONE_DIVERGE;
	}

	public interface ParticleInitializer
	{
		void init(Entity entity, Particle particle, EmitterConfig config);
	}

	private static final String TAG = EternalEmitter.class.getSimpleName();
	private static final int MIN_POOL_SIZE = 100;
	private static final int MAX_POOL_SIZE = 1000;

	protected EmitterConfig config;
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

	protected int id;
	private Set<Particle> particles;
	private ParticlePool particlePool;
	private boolean initialised;
	private ParticleCreatorFactory factory;

	//	Time between emission of two particles
	private float emissionInterval;
	//	Leftover emission time from previous interval
	private float balanceEmissionTime = 0f;

	public EternalEmitter(EmitterConfig config)
	{
		super();
		this.config = config;
		//		this();
		id = new Random().nextInt(50);
		particlePool = new ParticlePool(MIN_POOL_SIZE, MAX_POOL_SIZE);
		particles = new HashSet<>();
		factory = new ParticleCreatorFactory();

		emissionInterval = ((float) Time.ONE_SECOND_NANOS / config.getEmissionRate());
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
		particlePool = new ParticlePool(MIN_POOL_SIZE, MAX_POOL_SIZE);
		particles = new HashSet<>();
		factory = new ParticleCreatorFactory();

		this.emitterShape = emitterShape;
		this.maxParticles = maxParticles;
		this.particleLife = particleLife;
		this.emissionRate = emissionRate;
		this.positionVar = 0;
		this.maxSpeed = 0f;
		this.direction = 0f;
		this.directionVar = 5f;

		emissionInterval = ((float) Time.ONE_SECOND_NANOS / emissionRate);
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new EternalEmitter(config);
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

		//	Use balance emission time from previous frame for creation of new particle
		dt += balanceEmissionTime;
		balanceEmissionTime = 0;

		while (canEmitParticle() && dt > emissionInterval)
		{
			//			Log.d(TAG, "Adding particle");
			Particle particle = particlePool.getObject();
			factory.getInitializer(config).init(entity, particle, config);
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
		return particles.size() == config.getMaxParticles();
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
		return particles.size() < config.getMaxParticles() && particles.size() < MAX_POOL_SIZE;
	}

	int getLiveParticles()
	{
		return particles.size();
	}
}
