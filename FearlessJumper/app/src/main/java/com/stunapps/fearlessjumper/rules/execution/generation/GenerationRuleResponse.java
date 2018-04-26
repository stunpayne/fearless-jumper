package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.rules.execution.RuleResponse;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationRuleResponse extends RuleResponse
{
	private Map<Prefab, GenerationState> updatedPrefabStates;
	private Prefab nextPrefab;
	private Transform transform;

	public GenerationRuleResponse()
	{
		updatedPrefabStates = new HashMap<>();
	}

	public GenerationRuleResponse(Map<Prefab, GenerationConfig> prefabStates,
			Prefab nextPrefab, Transform transform)
	{
		this.nextPrefab = nextPrefab;
		this.transform = transform;
	}

	public Map<Prefab, GenerationState> getUpdatedPrefabStates()
	{
		return updatedPrefabStates;
	}

	public Prefab getNextPrefab()
	{
		return nextPrefab;
	}

	public Transform getTransform()
	{
		return transform;
	}

	public void updatePrefabState(Prefab prefab, GenerationState newState)
	{
		if (updatedPrefabStates == null)
		{
			updatedPrefabStates = new HashMap<>();
		}
		updatedPrefabStates.put(prefab, newState);
	}

	public void setUpdatedPrefabStates(Map<Prefab, GenerationState> prefabStates)
	{
		this.updatedPrefabStates = prefabStates;
	}

	public void setNextPrefab(Prefab nextPrefab)
	{
		this.nextPrefab = nextPrefab;
	}

	public void setTransform(Transform transform)
	{
		this.transform = transform;
	}
}
