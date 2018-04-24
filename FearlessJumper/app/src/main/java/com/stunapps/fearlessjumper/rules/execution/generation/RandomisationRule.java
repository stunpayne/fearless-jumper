package com.stunapps.fearlessjumper.rules.execution.generation;

import android.util.Log;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;

/**
 * Created by sunny.s on 21/04/18.
 */

public class RandomisationRule extends GenerationRule
{
	private static float NEW_OBSTACLE_OFFSET = -600f;

	private Shuffler<PrefabRef> obstacleShuffler;

	public RandomisationRule()
	{
		initShuffler();
	}

	@Override
	public boolean execute(GenerationRuleRequest ruleRequest, GenerationRuleResponse ruleResponse)
	{
		Prefab nextPrefab = obstacleShuffler.shuffle().get();
		Log.d("OBSTACLE_RANDOMISATION", nextPrefab.toString());
		ruleResponse.setNextPrefab(nextPrefab);

		Transform lastTransform = ruleRequest.getLastGeneratedTransform();
		if (null != lastTransform)
		{
			Transform newTransform = Transform.shiftVertical(lastTransform, NEW_OBSTACLE_OFFSET);
			float newX = (float) Math.random() * (Device.SCREEN_WIDTH - nextPrefab.getWidth());
			newTransform.getPosition().setX(newX);

			ruleResponse.setTransform(newTransform);
		}

		return true;
	}

	private void initShuffler()
	{
		if (obstacleShuffler == null)
		{
			obstacleShuffler =
					new WeightedShuffler.Builder<PrefabRef>().returnItem(PrefabRef.PLATFORM)
							.withWeight(5f).returnItem(PrefabRef.FLYING_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.CLOCK).withWeight(3f)
							.returnItem(PrefabRef.SHOOTER_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.GROUNDED_DRAGON_SET).withWeight(1f)
							.returnItem(PrefabRef.FOLLOWING_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.ASSAULT_DRAGON).withWeight(2f)
							.returnItem(PrefabRef.UNFRIENDLY_PLATFORM).withWeight(5f).build();
		}
	}
}
