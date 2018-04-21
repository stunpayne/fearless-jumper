package com.stunapps.fearlessjumper.rules.execution.generation;

import android.util.Log;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;

/**
 * Created by sunny.s on 21/04/18.
 */

public class RandomisationRule extends GenerationRule
{
	private static float NEW_OBSTACLE_OFFSET = -600f;

	private Shuffler<Prefab> obstacleShuffler;

	public RandomisationRule()
	{
		initShuffler();
	}

	@Override
	public boolean execute(GenerationRuleRequest ruleRequest, GenerationRuleResponse ruleResponse)
	{
		Prefab nextPrefab = obstacleShuffler.shuffle();
		Log.d("OBSTACLE_RANDOMISATION", nextPrefab.toString());
		ruleResponse.setNextPrefab(nextPrefab);

		Transform lastTransform = ruleRequest.getLastGeneratedTransform();
		if (null != lastTransform)
		{
			ruleResponse.setTransform(lastTransform.translateX(NEW_OBSTACLE_OFFSET));
		}

		return true;
	}

	private void initShuffler()
	{
		if (obstacleShuffler == null)
		{
			obstacleShuffler =
					new WeightedShuffler.Builder<Prefab>().returnItem(PrefabRef.PLATFORM.get())
							.withWeight(5f).returnItem(PrefabRef.FLYING_DRAGON.get()).withWeight
							(1f)
							.returnItem(PrefabRef.CLOCK.get()).withWeight(3f)
							.returnItem(PrefabRef.SHOOTER_DRAGON.get()).withWeight(1f)
							.returnItem(PrefabRef.GROUNDED_DRAGON_SET.get()).withWeight(1f)
							.returnItem(PrefabRef.FOLLOWING_DRAGON.get()).withWeight(1f)
							.returnItem(PrefabRef.ASSAULT_DRAGON.get()).withWeight(2f)
							.returnItem(PrefabRef.UNFRIENDLY_PLATFORM.get()).withWeight(5f)
							.build();
		}
	}
}
