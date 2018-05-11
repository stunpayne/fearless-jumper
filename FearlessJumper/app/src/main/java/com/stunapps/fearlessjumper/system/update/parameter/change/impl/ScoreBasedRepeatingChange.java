package com.stunapps.fearlessjumper.system.update.parameter.change.impl;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.game.UXParameters;
import com.stunapps.fearlessjumper.system.update.parameter.GameContextWrapper;

/**
 * @author: sunny.s
 * @since 11/05/18.
 */

public class ScoreBasedRepeatingChange extends RepeatingParameterChange
{
	private static int conditionsMet = 0;

	public ScoreBasedRepeatingChange()
	{
		super();
	}

	@Override
	public boolean isConditionMet(GameContextWrapper contextWrapper)
	{
		ComponentManager componentManager = contextWrapper.getComponentManager();
		Entity player = componentManager.getEntity(PlayerComponent.class);
		Score scoreComponent = player.getComponent(Score.class);
		if (scoreComponent.getScore() > 1000 && conditionsMet <= 0)
		{
			conditionsMet++;
			return true;
		}
		return false;
	}

	@Override
	public void perform(GameContextWrapper contextWrapper)
	{
		ComponentManager componentManager = contextWrapper.getComponentManager();
		Entity player = componentManager.getEntity(PlayerComponent.class);
		Score scoreComponent = player.getComponent(Score.class);
		if (scoreComponent.getScore() > 20)
		{
			scoreComponent.addScore(10);
		}
	}
}
