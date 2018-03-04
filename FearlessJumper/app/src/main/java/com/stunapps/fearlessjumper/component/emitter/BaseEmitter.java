package com.stunapps.fearlessjumper.component.emitter;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.particle.Particle;
import com.stunapps.fearlessjumper.particle.ParticlePool;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class BaseEmitter extends Emitter
{
	private static final String TAG = BaseEmitter.class.getSimpleName();

	@Inject
	private ParticlePool particlePool;
	private long totalParticleCount;
	private long particleLife;
	private long emissionInterval;

	private long batchSize; //Number of particles in single emission.

	protected List<Particle> particles;

	public BaseEmitter(Class<? extends Component> componentType, int totalParticleCount,
			long particleLife, long emissionInterval)
	{
		super(componentType);
		this.totalParticleCount = totalParticleCount;
		this.particleLife = particleLife;
		this.emissionInterval = emissionInterval;
		if (emissionInterval > 0)
		{
			this.batchSize = totalParticleCount / (particleLife / emissionInterval);
		}
		else
		{
			this.batchSize = totalParticleCount;
		}
		this.particles = new LinkedList<>();
	}

	private List<Particle> getParticleCluster()
	{
		List<Particle> particlesCluster = new LinkedList<>();
		for (int i = 0; i < batchSize && (this.particles.size() < totalParticleCount); i++)
		{
			Particle particle = particlePool.getObject();
			particle.waitTime =
					emissionInterval * (this.particles.size() / batchSize);
			particle.setLife(particleLife);
			particlesCluster.add(particle);
		}
		return particlesCluster;
	}

	private void addParticles(List<Particle> particles)
	{
		this.particles.addAll(particles);
	}

	@Override
	public void init()
	{
		List<Particle> particles = getParticleCluster();
		while(particles != null && particles.size() > 0)
		{
			setupParticleCluster(particles);
			this.particles.addAll(particles);
			particles = getParticleCluster();
		}
	}

	private void releaseDeadParticles()
	{
		Iterator<Particle> iterator = this.particles.iterator();
		while (iterator.hasNext())
		{
			Particle particle = iterator.next();
			if (particle.life <= 0)
			{
				particle.isActive = false;
				iterator.remove();
				particlePool.returnObject(particle);
			}
		}
	}

	abstract void setupParticleCluster(List<Particle> particles);

	@Override
	public void update(long delta)
	{
		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext())
		{
			Particle particle = iterator.next();
			if (!particle.update(delta))
			{
				iterator.remove();
				destroyParticle(particle);
			}
		}
	}

	@Override
	public void getBitmap()
	{

	}

	@Override
	public void destroyParticle(Particle particleToDestroy)
	{
		particlePool.returnObject(particleToDestroy);
	}

	protected void releaseParticle(){

	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return null;
	}
}
