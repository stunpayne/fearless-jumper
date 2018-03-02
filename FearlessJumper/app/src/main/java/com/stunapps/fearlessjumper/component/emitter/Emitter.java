package com.stunapps.fearlessjumper.component.emitter;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by anand.verma on 02/03/18.
 */

abstract public class Emitter extends Component
{
	public Emitter(Class<? extends Component> componentType)
	{
		super(componentType);
	}

	abstract void init();
	abstract void update(long delta);
	abstract void getBitmap();
	abstract void destroy();
}
