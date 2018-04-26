package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 10/01/18.
 */

public abstract class Prefab implements Instantiator
{
	abstract public <C extends Component> C getComponent(Class<C> componentType);
	abstract public float getWidth();

	@Override
	public String toString()
	{
		return "Prefab{class = " + this.getClass().getSimpleName() + "}";
	}
}
