package com.stunapps.fearlessjumper.rules.generation;

import com.stunapps.fearlessjumper.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.GenerationRuleRequest;
import com.stunapps.fearlessjumper.rules.GenerationRuleResponse;
import com.stunapps.fearlessjumper.rules.Rule;

import java.util.Random;

/**
 * Created by sunny.s on 19/04/18.
 */

public class LowTimeRule extends Rule<GenerationRuleRequest, GenerationRuleResponse>
{
	private static final int RANGE = 100;
	private static final int PROBABILITY = 70;
	private static final float THRESHOLD_TIME = 10f;
	private static final Random random = new Random();

	@Override
	public boolean execute(GenerationRuleRequest ruleRequest, GenerationRuleResponse ruleResponse)
	{
		if (ruleRequest.getTime() < THRESHOLD_TIME)
		{
			if (newRandom() < PROBABILITY)
			{
				ruleResponse.setNextPrefab(PrefabRef.CLOCK.get());
				return true;
			}
		}
		return false;
	}

	private static int newRandom()
	{
		return random.nextInt(RANGE);
	}
}
