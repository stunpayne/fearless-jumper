package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.execution.RuleRequest;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import java.util.Map;

/**
 * @author sunny.s
 * @see GenerationRuleExecutor
 * @see GenerationRuleResponse
 * @since 19/04/18.
 * <p>
 * Request model that will be provided to a GenerationRuleExecutor with the expectation
 * of a GenerationRuleResponse
 */

public class GenerationRuleRequest extends RuleRequest
{
	private float fuel;
	private float time;
	private Transform lastGeneratedTransform;
	private Map<PrefabRef, GenerationState> currentPrefabStates;
	private Map<PrefabRef, GenerationConfig> prefabConfig;

	public float getFuel()
	{
		return fuel;
	}

	public float getTime()
	{
		return time;
	}

	public Transform getLastGeneratedTransform()
	{
		return lastGeneratedTransform;
	}

	public Map<PrefabRef, GenerationState> getCurrentPrefabStates()
	{
		return currentPrefabStates;
	}

	public Map<PrefabRef, GenerationConfig> getPrefabConfig()
	{
		return prefabConfig;
	}

	public void setFuel(float fuel)
	{
		this.fuel = fuel;
	}

	public void setTime(float time)
	{
		this.time = time;
	}

	public void setLastGeneratedTransform(Transform lastGeneratedTransform)
	{
		this.lastGeneratedTransform = lastGeneratedTransform;
	}

	public void setCurrentPrefabStates(Map<PrefabRef, GenerationState> currentPrefabStates)
	{
		this.currentPrefabStates = currentPrefabStates;
	}

	public void setPrefabConfig(Map<PrefabRef, GenerationConfig> prefabConfig)
	{
		this.prefabConfig = prefabConfig;
	}
}
