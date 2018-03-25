package com.stunapps.fearlessjumper.component.emitter;

import android.graphics.Bitmap;
import android.util.Pair;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.Vector2D;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.Set;

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class Emitter extends Component
{
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

	public enum RenderMode
	{
		SHAPE, TEXTURE;
	}
}
