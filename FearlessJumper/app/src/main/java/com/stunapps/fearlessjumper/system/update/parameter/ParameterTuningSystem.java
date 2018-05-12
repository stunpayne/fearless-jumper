package com.stunapps.fearlessjumper.system.update.parameter;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.game.UXParameters;
import com.stunapps.fearlessjumper.system.update.UpdateSystem;
import com.stunapps.fearlessjumper.system.update.parameter.change.ParameterChange;
import com.stunapps.fearlessjumper.system.update.parameter.change.impl.ScoreBasedRepeatingChange;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public class ParameterTuningSystem implements UpdateSystem
{
	private static final String TAG = ParameterTuningSystem.class.getSimpleName();
	private static final long lastProcessTime = 0;

	private final ComponentManager componentManager;
	private List<? extends ParameterChange> possibleParameterChanges;
	private List<ParameterChange> expiredChanges;

	@Inject
	public ParameterTuningSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
		reset();
	}

	@Override
	public void process(long deltaTime)
	{
		if (possibleParameterChanges.isEmpty())
		{
			return;
		}

		GameContextWrapper gameContextWrapper = new GameContextWrapper(componentManager);
		Iterator<? extends ParameterChange> iterator = possibleParameterChanges.iterator();
		while (iterator.hasNext())
		{
			ParameterChange parameterChange = iterator.next();
			if (parameterChange.isConditionMet(gameContextWrapper))
			{
				parameterChange.perform(gameContextWrapper);
				if (parameterChange.repeats())
				{
					expiredChanges.add(parameterChange);
					iterator.remove();
				}
			}
		}
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
		initialiseParameterChanges();
		expiredChanges = Lists.newArrayList();
	}

	private void initialiseParameterChanges()
	{
		possibleParameterChanges = Lists.newArrayList(new ScoreBasedRepeatingChange());
	}
}
