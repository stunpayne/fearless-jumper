package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.execution.RuleResponse;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationRuleResponse extends RuleResponse
{
	private PrefabRef nextPrefab;
	private Transform transform;
	private Map<PrefabRef, GenerationState> updatedPrefabStates;

	public GenerationRuleResponse()
	{
		updatedPrefabStates = Maps.newHashMap();
	}

	public GenerationRuleResponse(Map<Prefab, GenerationConfig> prefabStates, PrefabRef nextPrefab,
			Transform transform)
	{
		this.nextPrefab = nextPrefab;
		this.transform = transform;
	}

	public PrefabRef getNextPrefab()
	{
		return nextPrefab;
	}

	public Transform getTransform()
	{
		return transform;
	}

	public Map<PrefabRef, GenerationState> getUpdatedPrefabStates()
	{
		return updatedPrefabStates;
	}

	public void setNextPrefab(PrefabRef nextPrefab)
	{
		this.nextPrefab = nextPrefab;
	}

	public void setTransform(Transform transform)
	{
		this.transform = transform;
	}

	public void blockPrefab(PrefabRef prefabRef)
	{
		if (!updatedPrefabStates.containsKey(prefabRef))
		{
			updatedPrefabStates.put(prefabRef, new GenerationState());
		}
		updatedPrefabStates.get(prefabRef).block();
	}

	public void unblockPrefab(PrefabRef prefabRef)
	{
		if (!updatedPrefabStates.containsKey(prefabRef))
		{
			updatedPrefabStates.put(prefabRef, new GenerationState());
		}
		updatedPrefabStates.get(prefabRef).unblock();
	}
}
