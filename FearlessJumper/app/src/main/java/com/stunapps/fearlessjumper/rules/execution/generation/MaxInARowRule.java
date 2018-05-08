package com.stunapps.fearlessjumper.rules.execution.generation;

import android.util.Log;

import com.stunapps.fearlessjumper.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import java.util.Map;

/**
 * @author sunny.s
 * @since 01/05/18
 * <p>
 * This rule does not allow a prefab to be generated continuously for more than the allowed
 * number of times.
 */

public class MaxInARowRule extends GenerationRule
{
	@Override
	public boolean execute(GenerationRuleRequest ruleRequest, GenerationRuleResponse ruleResponse)
	{
		Map<PrefabRef, GenerationConfig> prefabConfig = ruleRequest.getPrefabConfig();
		Map<PrefabRef, GenerationState> prefabStates = ruleRequest.getCurrentPrefabStates();

		for (PrefabRef prefab : prefabConfig.keySet())
		{
			GenerationState prefabState = prefabStates.get(prefab);
			if (prefabState != null)
			{
				if (prefabState.getCurrInARow() >= prefabConfig.get(prefab).getMaxInARow())
				{
					Log.d("MAX_IN_A_ROW", "Blocking " + prefab.name());
					blockPrefab(ruleRequest, ruleResponse, prefab);
				}

				if (prefabState.getCurrInARow() == 0 && prefabState.isBlocked())
				{
					Log.d("MAX_IN_A_ROW", "Unblocking " + prefab.name());
					unblockPrefab(ruleRequest, ruleResponse, prefab);
				}
			}
		}
		return false;
	}
}
