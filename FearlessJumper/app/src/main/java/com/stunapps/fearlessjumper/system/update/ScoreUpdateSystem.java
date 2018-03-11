package com.stunapps.fearlessjumper.system.update;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.manager.GameStatsManager;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.entity.Entity;

import javax.inject.Inject;

/**
 * Created by sunny.s on 19/02/18.
 */

public class ScoreUpdateSystem implements UpdateSystem
{
	private final static float SCORE_MULTIPLIER = 1 / 200f;

	private static long lastProcessTime;
	private Position topPlayerPosition = null;

	private final ComponentManager componentManager;
	private final GameStatsManager gameStatsManager;

	@Inject
	public ScoreUpdateSystem(ComponentManager componentManager, GameStatsManager gameStatsManager)
	{
		this.componentManager = componentManager;
		this.gameStatsManager = gameStatsManager;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Entity player = componentManager.getEntity(PlayerComponent.class);

		updateScore(player);
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void reset()
	{
		lastProcessTime = System.nanoTime();
		topPlayerPosition = null;
	}

	private void updateScore(Entity player)
	{
		if (topPlayerPosition != null && topPlayerPosition.isBelow(player.transform.position))
		{
			float topPosDiff = player.transform.position.y - topPlayerPosition.y;
			float scoreIncrease = Math.abs(SCORE_MULTIPLIER * topPosDiff);
			player.getComponent(Score.class).addScore(scoreIncrease);

			player.transform.position.copyTo(topPlayerPosition);
			float score = player.getComponent(Score.class).getScore();
			gameStatsManager.updateCurrentScore((long) score);
		}

		if (topPlayerPosition == null) topPlayerPosition = new Position(player.transform.position);
	}
}
