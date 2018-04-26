package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.rules.execution.RuleRequest;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import java.util.Map;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationRuleRequest extends RuleRequest
{
	private float fuel;
	private float time;
	private Transform lastGeneratedTransform;
	private Map<Prefab, GenerationState> currentPrefabStates;
	private Map<Prefab, GenerationConfig> prefabConfig;

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

	public Map<Prefab, GenerationConfig> getPrefabConfig()
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

	public void setCurrentPrefabStates(Map<Prefab, GenerationState> currentPrefabStates)
	{
		this.currentPrefabStates = currentPrefabStates;
	}

	public void setPrefabConfig(Map<Prefab, GenerationConfig> prefabConfig)
	{
		this.prefabConfig = prefabConfig;
	}
}
