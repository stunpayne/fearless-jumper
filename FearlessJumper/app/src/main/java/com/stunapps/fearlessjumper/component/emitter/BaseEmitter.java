package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

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
	private long particlesCount;
	private long particleLife;
	private long emissionInterval;
	private long clusterSize;
	protected List<Particle> particles;

	public BaseEmitter(Class<? extends Component> componentType, int particlesCount,
			long particleLife, long emissionInterval)
	{
		super(componentType);
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
		this.particles = new LinkedList<>();
	}

	private List<Particle> getParticleCluster()
	{
		List<Particle> particleCluster = new LinkedList<>();
		for (int i = 0; i < clusterSize && (this.particles.size() < particlesCount); i++)
		{
			Particle particle = particlePool.getObject();
			particle.waitTime =
					emissionInterval * (this.particles.size() / clusterSize);
			particle.setLifeTimer(particleLife);
			particleCluster.add(particle);
		}
		return particleCluster;
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
			if (particle.lifeTimer <= 0)
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

	public void drawParticles(Canvas canvas)
	{
		Paint fuelTextPaint = new Paint();
		fuelTextPaint.setColor(Color.WHITE);
		fuelTextPaint.setTextAlign(Align.CENTER);
		fuelTextPaint.setTypeface(Typeface.SANS_SERIF);
		fuelTextPaint.setTextSize(50);
		//fuelTextPaint.setAlpha(255);

		for (Particle particle : particles)
		{
			fuelTextPaint.setAlpha((int) (255 * particle.alpha));
			if (particle.isActive)
			{
				canvas.drawCircle(particle.position.x, particle.position.y, 5, fuelTextPaint);
				//canvas.drawPoint(particle.position.x, particle.position.y, fuelTextPaint);
			}
		}
	}
}
