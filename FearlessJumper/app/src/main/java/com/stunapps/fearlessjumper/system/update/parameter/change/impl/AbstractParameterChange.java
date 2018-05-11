package com.stunapps.fearlessjumper.system.update.parameter.change.impl;

import com.stunapps.fearlessjumper.system.update.parameter.change.ParameterChange;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public abstract class AbstractParameterChange implements ParameterChange
{
	private boolean repeats;

	public AbstractParameterChange(boolean repeats)
	{
		this.repeats = repeats;
	}

	@Override
	public boolean repeats()
	{
		return repeats;
	}
}
