package com.stunapps.fearlessjumper.system.update;

import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.Score;
import com.stunapps.fearlessjumper.component.transform.Position;
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

	@Inject
	public ScoreUpdateSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
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
		Position playerPosition = player.transform.position;
		if (topPlayerPosition != null && topPlayerPosition.isBelow(playerPosition))
		{
			float topPosDiff = playerPosition.y - topPlayerPosition.y;
			float scoreIncrease = Math.abs(SCORE_MULTIPLIER * topPosDiff);
			player.getComponent(Score.class).addScore(scoreIncrease);
			playerPosition.copyTo(topPlayerPosition);
		}

		if (topPlayerPosition == null) topPlayerPosition = new Position(playerPosition);
	}
}
