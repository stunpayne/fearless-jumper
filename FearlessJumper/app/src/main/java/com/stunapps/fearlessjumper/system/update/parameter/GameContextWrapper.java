package com.stunapps.fearlessjumper.system.update.parameter;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.ComponentType;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.entity.Entity;

/**
 * @author sunny.s
 * @since 11/05/18.
 */
public class GameContextWrapper
{
	private Entity player;
	private Score scoreComponent;

	public GameContextWrapper(ComponentManager componentManager)
	{
		if (componentManager != null)
		{
			player = componentManager.getEntity(PlayerComponent.class);
			scoreComponent = player.getComponent(Score.class);
		}
	}

	public Entity getPlayer()
	{
		return player;
	}

	public Score getScoreComponent()
	{
		return scoreComponent;
	}
}
