package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Vector2D;
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
		//	Whether the emitter is active since birth
		protected boolean startAsActive = false;

		//	Shape in which particles have to be emitted
		protected EmitterShape emitterShape;

		//	Max number of particles that can be alive at once
		protected int maxParticles;
		//	Duration for which each particle is alive
		protected int particleLife;
		//	Particles per second
		protected int emissionRate;

		//	Max speed of every particle
		protected float maxSpeed;
		//	Max variation in particle generation position, on either side, from the entity position
		protected Vector2D positionVar;

		//	Direction, in degrees, in which the particles should move
		protected float direction;
		//	Max variation in angle, on either side, from the direction property
		protected float directionVar;

		//	Offset from entity position
		private Vector2D offset;

		//	Starting Color of particles
		private int startColor;
		//	Ending Color of particles
		private int endColor;
		//	Texture of particle
		private Bitmap texture;

		//	Mode in which the particles will be rendered
		private RenderMode renderMode;

		public EmitterConfig(boolean startAsActive, EmitterShape emitterShape, int maxParticles,
				int particleLife, int emissionRate, Vector2D positionVar, float maxSpeed,
				float direction, float directionVar, Vector2D offset, int startColor, int endColor)
		{
			this.startAsActive = startAsActive;
			this.emitterShape = emitterShape;
			this.maxParticles = maxParticles;
			this.particleLife = particleLife;
			this.emissionRate = emissionRate;
			this.positionVar = positionVar;
			this.maxSpeed = maxSpeed;
			this.direction = direction;
			this.directionVar = directionVar;
			this.offset = offset;
			this.startColor = startColor;
			this.endColor = endColor;
			this.renderMode = RenderMode.SHAPE;

			Log.d(TAG, "Creating config: " + this);
		}

		public EmitterConfig(boolean startAsActive, EmitterShape emitterShape, int maxParticles,
				int particleLife, int emissionRate, Vector2D positionVar, float maxSpeed,
				float direction, float directionVar, Vector2D offset, Bitmap texture)
		{
			this.startAsActive = startAsActive;
			this.emitterShape = emitterShape;
			this.maxParticles = maxParticles;
			this.particleLife = particleLife;
			this.emissionRate = emissionRate;
			this.positionVar = positionVar;
			this.maxSpeed = maxSpeed;
			this.direction = direction;
			this.directionVar = directionVar;
			this.offset = offset;
			this.texture = texture;
			this.renderMode = RenderMode.TEXTURE;

			Log.d(TAG, "Creating config: " + this);
		}

		@Override
		protected EmitterConfig clone() throws CloneNotSupportedException
		{
			Log.d(TAG, "Cloning");
			EmitterConfig.Builder builder = builder();
			switch (renderMode)
			{
				case TEXTURE:
					builder.emitterShape(emitterShape).maxParticles(maxParticles)
							.particleLife(particleLife).emissionRate(emissionRate)
							.positionVar(positionVar).maxSpeed(maxSpeed).direction(direction)
							.directionVar(directionVar).offset(offset).texture(texture);
					break;
				case SHAPE:
				default:
					builder.emitterShape(emitterShape).maxParticles(maxParticles)
							.particleLife(particleLife).emissionRate(emissionRate)
							.positionVar(positionVar).maxSpeed(maxSpeed).direction(direction)
							.directionVar(directionVar).offset(offset).color(startColor);
					break;
			}

			if (startAsActive)
				builder.startAsActive();
			return builder.build();
		}

		public static Builder builder()
		{
			return new Builder();
		}

		public static class Builder
		{
			protected boolean startAsActive = false;
			EmitterShape emitterShape;
			int maxParticles;
			int particleLife;
			int emissionRate;
			Vector2D positionVar;
			float maxSpeed;
			float direction;
			float directionVar;
			Vector2D offset = new Vector2D();
			int startColor = Color.WHITE;
			int endColor = Color.TRANSPARENT;
			Bitmap texture = null;
			RenderMode renderMode;

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

			public Builder positionVar(Vector2D positionVar)
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

			public Builder offset(Vector2D offset)
			{
				this.offset = offset;
				return this;
			}

			public Builder color(int color)
			{
				this.startColor = color;
				this.renderMode = RenderMode.SHAPE;
				return this;
			}

			public Builder color(int a, int r, int g, int b)
			{
				this.startColor = Color.argb(a, r, g, b);
				this.renderMode = RenderMode.SHAPE;
				return this;
			}

			public Builder colorLimits(int startColor, int endColor)
			{
				this.startColor = startColor;
				this.endColor = endColor;
				this.renderMode = RenderMode.SHAPE;
				return this;
			}

			public Builder texture(Bitmap texture)
			{
				this.texture = texture;
				this.renderMode = RenderMode.TEXTURE;
				return this;
			}

			public Builder startAsActive()
			{
				this.startAsActive = true;
				return this;
			}

			public EmitterConfig build()
			{
				if (renderMode == RenderMode.SHAPE)
				{
					return new EmitterConfig(startAsActive, emitterShape, maxParticles,
							particleLife, emissionRate, positionVar, maxSpeed, direction,
							directionVar, offset, startColor, endColor);
				}
				else if (renderMode == RenderMode.TEXTURE)
				{
					return new EmitterConfig(startAsActive, emitterShape, maxParticles,
							particleLife, emissionRate, positionVar, maxSpeed, direction,
							directionVar, offset, texture);
				}
				return null;
			}
		}

		@Override
		public String toString()
		{
			return "EmitterConfig{" + "emitterShape=" + emitterShape + ", maxParticles=" +
					maxParticles + ", particleLife=" + particleLife + ", emissionRate=" +
					emissionRate + ", maxSpeed=" + maxSpeed + ", positionVar=" + positionVar +
					", direction=" + direction + ", directionVar=" + directionVar + ", offset=" +
					offset + ", hashCode=" + hashCode() + '}';
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

	private EmitterConfig config;

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
			//			Log.d(TAG, "Adding particle");
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
		//		Log.d(TAG, "Destroying particle: " + particleToDestroy);
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
