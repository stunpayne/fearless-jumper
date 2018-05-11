package com.stunapps.fearlessjumper.system.update.parameter.change;

import com.stunapps.fearlessjumper.system.update.parameter.GameContextWrapper;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public interface ParameterChange
{
	boolean isConditionMet(GameContextWrapper gameContextWrapper);

	void perform(GameContextWrapper contextWrapper);

	boolean repeats();
}
