package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.Time;
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
	public interface ParticleInitializer
	{
		void init(Entity entity, Particle particle, EmitterConfig config);
	}

	private static final String TAG = EternalEmitter.class.getSimpleName();
	private static final int MIN_POOL_SIZE = 100;
	private static final int MAX_POOL_SIZE = 1000;

	protected int id;
	private Set<Particle> particles;
	private ParticlePool particlePool;
	private ParticleCreatorFactory factory;
	private boolean initialised;
	private boolean active = false;

	//	Time between emission of two particles
	private float emissionInterval;
	//	Leftover emission time from previous interval
	private float balanceEmissionTime = 0f;

	public EternalEmitter(EmitterConfig config)
	{
		super();
		this.config = config;
		id = new Random().nextInt(50);
		particlePool = new ParticlePool(MIN_POOL_SIZE, MAX_POOL_SIZE);
		particles = new HashSet<>();
		factory = new ParticleCreatorFactory();

		emissionInterval = ((float) Time.ONE_SECOND_NANOS / config.getEmissionRate());
		init();
		if (config.isStartAsActive())
		{
			activate();
		}
	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return new EternalEmitter(config.clone());
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
			if (active)
			{
				Particle particle = particlePool.getObject();
				factory.getInitializer(config).init(entity, particle, config);
				particles.add(particle);
			}
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
		return false;
	}

	@Override
	void destroyParticle(Particle particleToDestroy)
	{
		particleToDestroy.reset();
		particlePool.returnObject(particleToDestroy);
	}

	@Override
	public void activate()
	{
		active = true;
	}

	@Override
	public void deactivate()
	{
		active = false;
	}

	private boolean canEmitParticle()
	{
		return particles.size() < config.getMaxParticles() && particles.size() < MAX_POOL_SIZE;
	}

	int getLiveParticles()
	{
		return particles.size();
	}

	@Override
	public Bitmap getTexture()
	{
		return this.config.getTexture();
	}

	@Override
	public RenderMode getRenderMode()
	{
		return this.config.getRenderMode();
	}
}
