package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.Component;
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

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class BaseEmitter extends Emitter
{
	private static final String TAG = BaseEmitter.class.getSimpleName();

	private Random random = new Random();
	protected int emitterId;
	protected Set<Particle> particles;
	protected boolean isInitialised = false;

	@Inject
	private ParticlePool particlePool;

	private long particlesCount;
	private long particleLife;
	private long emissionInterval;
	private long clusterSize;

	public BaseEmitter(Class<? extends Component> componentType, int particlesCount,
			long particleLife, long emissionInterval)
	{
		super(componentType);
		emitterId = random.nextInt(50);
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
		Log.d(TAG, "init: emitter: emitterId = " + emitterId);
	}

	@Override
	public boolean isInitialised()
	{
		return isInitialised;
	}

	@Override
	public long getEmitterId()
	{
		return emitterId;
	}

	abstract void setupParticleCluster(List<Particle> particles);

	@Override
	public void update(long delta)
	{
		Log.d(TAG, "update: emitter : updated called.");
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
	protected void destroyParticle(Particle particle)
	{
		particle.reset();
		particlePool.returnObject(particle);
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
