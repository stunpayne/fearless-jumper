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

	abstract public long getEmitterId();

	abstract public void update(long delta);

	abstract public Set<Particle> getParticles();

	public abstract void init();

	abstract void destroyParticle(Particle particleToDestroy);
}
