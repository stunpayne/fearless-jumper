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
	@Override
	public boolean execute(GenerationRuleRequest ruleRequest, GenerationRuleResponse ruleResponse)
	{
		PrefabRef nextPrefab = shuffle();
		Log.d("OBSTACLE_RANDOMISATION", nextPrefab.name());
		setNextPrefab(ruleRequest, ruleResponse, nextPrefab);

		return true;
	}
}
