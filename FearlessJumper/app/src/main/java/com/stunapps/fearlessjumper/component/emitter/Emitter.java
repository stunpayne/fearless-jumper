package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.Log;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Vector2D;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.Set;

import lombok.Getter;

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class Emitter extends Component
{
	private static final String TAG = Emitter.class.getSimpleName();

	@Getter
	protected EmitterConfig config;

	public Emitter()
	{
		super(Emitter.class);
	}

	abstract public long getId();

	public abstract void init();

	abstract public boolean isInitialised();

	abstract public void update(long delta);

	abstract public Set<Particle> getParticles();

	abstract void destroyParticle(Particle particleToDestroy);

	abstract public boolean isExhausted();

	abstract public void activate();

	abstract public void deactivate();

	//	TODO: Add getShape method as well to support different shapes such as circle/rect/square
	// etc

	abstract public Bitmap getTexture();

	public RenderMode getRenderMode()
	{
		return RenderMode.SHAPE;
	}

	//
	public Xfermode getBlendMode()
	{
		return new PorterDuffXfermode(config.getBlendingMode());
	}

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
		//	Size of every particle - applicable only in case of Shape RenderMode
		private int size;
		//	Texture of particle
		private Bitmap texture;

		//	Mode in which the particles will be rendered
		private RenderMode renderMode;
		//	Alpha blending mode
		private PorterDuff.Mode blendingMode;

		public EmitterConfig(boolean startAsActive, EmitterShape emitterShape, int maxParticles,
				int particleLife, int emissionRate, Vector2D positionVar, float maxSpeed,
				float direction, float directionVar, Vector2D offset, PorterDuff.Mode blendingMode,
				int startColor, int endColor, int size)
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
			this.blendingMode = blendingMode;
			this.startColor = startColor;
			this.endColor = endColor;
			this.renderMode = RenderMode.SHAPE;
			this.size = size;

			Log.d(TAG, "Creating config: " + this);
		}

		public EmitterConfig(boolean startAsActive, EmitterShape emitterShape, int maxParticles,
				int particleLife, int emissionRate, Vector2D positionVar, float maxSpeed,
				float direction, float directionVar, PorterDuff.Mode blendingMode, Vector2D offset,
				Bitmap texture)
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
			this.blendingMode = blendingMode;
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
							.directionVar(directionVar).offset(offset).blendingMode(blendingMode)
							.texture(texture);
					break;
				case SHAPE:
				default:
					builder.emitterShape(emitterShape).maxParticles(maxParticles)
							.particleLife(particleLife).emissionRate(emissionRate)
							.positionVar(positionVar).maxSpeed(maxSpeed).direction(direction)
							.directionVar(directionVar).offset(offset).blendingMode(blendingMode)
							.color(startColor).size(size);
					break;
			}

			if (startAsActive) builder.startAsActive();
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
			int size = 5;
			Bitmap texture = null;
			RenderMode renderMode;
			private PorterDuff.Mode blendingMode = Mode.DST_OVER;

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

			public Builder size(int size)
			{
				this.size = size;
				return this;
			}

			public Builder texture(Bitmap texture)
			{
				this.texture = texture;
				this.renderMode = RenderMode.TEXTURE;
				return this;
			}

			public Builder blendingMode(PorterDuff.Mode mode)
			{
				this.blendingMode = mode;
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
							directionVar, offset, blendingMode, startColor, endColor, size);
				}
				else if (renderMode == RenderMode.TEXTURE)
				{
					return new EmitterConfig(startAsActive, emitterShape, maxParticles,
							particleLife, emissionRate, positionVar, maxSpeed, direction,
							directionVar, blendingMode, offset, texture);
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

	public enum RenderMode
	{
		SHAPE, TEXTURE;
	}
}
