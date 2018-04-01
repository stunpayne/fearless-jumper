package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Color;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.particle.Particle;
import com.stunapps.fearlessjumper.particle.ParticlePool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.EqualsAndHashCode;

/**
 * Created by anand.verma on 02/03/18.
 */

@EqualsAndHashCode(of = "id")
abstract public class BaseClusteredEmitter extends Emitter
{
	private static final String TAG = BaseClusteredEmitter.class.getSimpleName();

	private Random random = new Random();
	protected int id;
	protected Set<Particle> particles;
	protected boolean isInitialised = false;

	@Inject
	private ParticlePool particlePool;

	protected int particlesCount;
	protected long particleLife;
	protected long emissionInterval;
	protected long clusterSize;

	public BaseClusteredEmitter(int particlesCount, long particleLife, long emissionInterval)
	{
		super();
		id = random.nextInt(50);
		this.particlePool = DI.di().getInstance(ParticlePool.class);
		this.particlesCount = particlesCount;
		this.particleLife = particleLife;
		this.emissionInterval = emissionInterval;
		if (emissionInterval > 0)
		{
			this.clusterSize = particlesCount / (particleLife / emissionInterval);
		}
		else
		{
			this.clusterSize = particlesCount;
		}
		this.particles = new HashSet<>();

		this.config = EmitterConfig.builder()
				.colorLimits(Color.argb(200, 0, 200, 255), Color.argb(200, 255, 255, 255))
				.particleLife((int) particleLife).size(5).build();
	}

	private List<Particle> generateParticleCluster()
	{
		List<Particle> particleCluster = new LinkedList<>();
		for (int i = 0; i < clusterSize && (this.particles.size() < particlesCount); i++)
		{
			Particle particle = particlePool.getObject();
			particle.waitTime = emissionInterval * (this.particles.size() / clusterSize);
			particle.setLife(particleLife);
			particleCluster.add(particle);
		}
		return particleCluster;
	}

	@Override
	public void init()
	{
		List<Particle> particles = null;
		while ((particles = generateParticleCluster()) != null && particles.size() > 0)
		{
			setupParticleCluster(particles);
			this.particles.addAll(particles);
		}
		isInitialised = true;
	}

	@Override
	public boolean isInitialised()
	{
		return isInitialised;
	}

	@Override
	public long getId()
	{
		return id;
	}

	abstract void setupParticleCluster(List<Particle> particles);

	@Override
	public void update(long delta)
	{
		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext())
		{
			Particle particle = iterator.next();
			boolean isAliveAfterUpdate = particle.update(delta);
			if (!isAliveAfterUpdate)
			{
				iterator.remove();
				destroyParticle(particle);
			}
		}
	}

	@Override
	public Set<Particle> getParticles()
	{
		return Collections.unmodifiableSet(particles);
	}

	@Override
	public boolean isExhausted()
	{
		return particles.size() <= 0;
	}

	@Override
	protected void destroyParticle(Particle particle)
	{
		particle.reset();
		particlePool.returnObject(particle);
	}

	@Override
	public void activate()
	{
		isInitialised = true;
	}

	@Override
	public void deactivate()
	{
		isInitialised = false;
	}
}
