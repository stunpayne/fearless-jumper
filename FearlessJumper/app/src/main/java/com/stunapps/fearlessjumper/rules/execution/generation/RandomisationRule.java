package com.stunapps.fearlessjumper.rules.execution.generation;

import android.util.Log;

import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;

/**
 * @author: sunny.s
 * @since 21/04/18.
 * <p>
 * Shuffles between all available items and decides on one prefab to be generated.
 * This should ideally be the last rule to execute
 */

public class RandomisationRule extends GenerationRule
{
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
		setNextPrefab(ruleRequest, ruleResponse, nextPrefab);

		return true;
	}

	private void initShuffler()
	{
		if (obstacleShuffler == null)
		{
			obstacleShuffler =
					new WeightedShuffler.Builder<PrefabRef>()
							.returnItem(PrefabRef.PLATFORM).withWeight(5f)
							.returnItem(PrefabRef.FLYING_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.CLOCK).withWeight(3f)
							.returnItem(PrefabRef.SHOOTER_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.GROUNDED_DRAGON_SET).withWeight(1f)
							.returnItem(PrefabRef.FOLLOWING_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.ASSAULT_DRAGON).withWeight(2f)
							.returnItem(PrefabRef.UNFRIENDLY_PLATFORM).withWeight(5f)
							.build();
		}
	}
}
