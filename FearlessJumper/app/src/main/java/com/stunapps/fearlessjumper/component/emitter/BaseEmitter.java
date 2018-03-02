package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Particle;

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class BaseEmitter extends Emitter
{
	private static Particle[] particlePool;
	private int activeParticleCount;

	public BaseEmitter(Class<? extends Component> componentType, long emissionDuration,
			int particleCount)
	{
		super(componentType);
		particlePool = new Particle[particleCount];
		for (int i = 0; i < particleCount; i++)
		{
			particlePool[i] = new Particle();
		}
	}

	@Override
	public void init()
	{

	}

	@Override
	public void update(long delta)
	{

	}

	@Override
	public void getBitmap()
	{

	}

	@Override
	public void destroy()
	{

	}

	protected Particle getParticle(){
		return particlePool[activeParticleCount++];
	}

	protected void releaseParticle(){

	}

	@Override
	public Component clone() throws CloneNotSupportedException
	{
		return null;
	}
}
