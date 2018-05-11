package com.stunapps.fearlessjumper.system.update.parameter;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.ComponentType;
import com.stunapps.fearlessjumper.entity.Entity;

/**
 * @author sunny.s
 * @since 11/05/18.
 */
public class GameContextWrapper
{
	ComponentManager componentManager;

	public GameContextWrapper(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	public ComponentManager getComponentManager()
	{
		return componentManager;
	}
}
