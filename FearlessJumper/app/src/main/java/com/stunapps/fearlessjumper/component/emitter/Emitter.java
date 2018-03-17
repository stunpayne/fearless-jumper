package com.stunapps.fearlessjumper.component.emitter;

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

	abstract public boolean isInitialised();

	abstract public long getId();

	abstract public void update(long delta);

	abstract public Set<Particle> getParticles();

	public abstract void init();

	public abstract boolean isExhausted();

	abstract void destroyParticle(Particle particleToDestroy);
}
