package com.stunapps.fearlessjumper.system.update.parameter;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.ComponentType;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.UXParameters;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public class ParameterTuningSystem implements UpdateSystem
{
	private static final long lastProcessTime = 0;

	private final ComponentManager componentManager;

	private final List<ParameterTuner> parameterTuners;

	@Inject
	public ParameterTuningSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
		parameterTuners = initialiseParameterTuners();
	}

	@Override
	public void process(long deltaTime)
	{

	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void reset()
	{
		UXParameters.reset();
	}

	private List<ParameterTuner> initialiseParameterTuners()
	{
		return new ArrayList<ParameterTuner>();
	}
}
