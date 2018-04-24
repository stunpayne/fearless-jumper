package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.rules.execution.Rule;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;

/**
 * Created by sunny.s on 19/04/18.
 */

public abstract class GenerationRule extends Rule<GenerationRuleRequest, GenerationRuleResponse>
{
	private static final float NEW_OBSTACLE_OFFSET = -600f;

	void setNextPrefab(GenerationRuleRequest request, GenerationRuleResponse response,
			Prefab prefab)
	{
		response.setNextPrefab(prefab);
		response.setTransform(getNextLocation(request, prefab));
	}

	private Transform getNextLocation(GenerationRuleRequest request, Prefab prefab)
	{
		GenerationConfig generationConfig = request.getPrefabConfig().get(prefab);
		switch (generationConfig.getGenerationLocation())
		{
			case X_ANYWHERE:
				Transform lastTransform = request.getLastGeneratedTransform();
				if (null != lastTransform)
				{
					Transform newTransform =
							Transform.withYShift(lastTransform, NEW_OBSTACLE_OFFSET);
					float newX = (float) Math.random() * (Device.SCREEN_WIDTH - prefab.getWidth());
					newTransform.getPosition().setX(newX);
					return newTransform;
				}
			case X_BOUNDARIES:
				return null;
			default:
				return null;
		}
	}
}
