package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.particle.Particle;

import java.util.Set;

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class Emitter extends Component
{
	public Emitter(Class<? extends Component> componentType)
	{
		super(componentType);
	}

	abstract public boolean isInitialised();

	abstract public long getId();

	abstract public void update(long delta);

	abstract public Set<Particle> getParticles();

	public abstract void init();

	public abstract boolean isExhausted();

	abstract void destroyParticle(Particle particleToDestroy);
}
