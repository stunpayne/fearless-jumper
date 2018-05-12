package com.stunapps.fearlessjumper.system.update.parameter.change.impl;

import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.system.model.GameContextWrapper;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public class ScoreBasedRepeatingChange extends RepeatingParameterChange
{
	private static final String TAG = ScoreBasedRepeatingChange.class.getSimpleName();
	private static int conditionsMet = 0;
	private static int conditionsPerformed = 0;

	public ScoreBasedRepeatingChange()
	{
		super();
	}

	@Override
	public boolean isConditionMet(GameContextWrapper contextWrapper)
	{
		Score scoreComponent = contextWrapper.getScoreComponent();
		if (conditionsMet == 0 && scoreComponent.getScore() > 100)
		{
			conditionsMet++;
			return true;
		}
		return false;
	}

	@Override
	public void perform(GameContextWrapper contextWrapper)
	{
		Score scoreComponent = contextWrapper.getScoreComponent();
		if (conditionsPerformed == 0 && scoreComponent.getScore() > 100)
		{
			scoreComponent.addScore(20);
			conditionsPerformed++;
		}
	}
}
