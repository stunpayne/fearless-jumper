package com.stunapps.fearlessjumper.rules;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.generation.LowTimeRule;
import com.stunapps.fearlessjumper.rules.generation.RandomisationRule;
import com.stunapps.fearlessjumper.rules.generation.model.GenerationLocation;
import com.stunapps.fearlessjumper.rules.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.generation.model.GenerationState;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunny.s on 19/04/18.
 */

public class RuleEngine
{
	private static RuleExecutor<GenerationRuleRequest, GenerationRuleResponse> ruleExecutor;
	private static GenerationRuleRequest generationRuleRequest;
	private static GenerationRuleResponse generationRuleResponse;

	static
	{
		ruleExecutor = new GenerationRuleExecutor(createRules());
		generationRuleRequest = initRequest();
		generationRuleResponse = initResponse();
	}

	public RuleEngine()
	{
	}

	public static GenerationRuleResponse execute(ComponentManager componentManager,
			EntityManager entityManager)
	{
		ruleExecutor.execute(buildRuleRequest(componentManager, entityManager),
				buildRuleResponse(componentManager, entityManager));

		return generationRuleResponse;
	}

	private static GenerationRuleRequest initRequest()
	{
		GenerationRuleRequest generationRuleRequest = new GenerationRuleRequest();
		generationRuleRequest.setPrefabConfig(prefabConfigs());
		generationRuleRequest.setCurrentPrefabStates(prefabStates());

		return generationRuleRequest;
	}

	private static GenerationRuleResponse initResponse()
	{
		return new GenerationRuleResponse();
	}

	private static Map<Prefab, GenerationConfig> prefabConfigs()
	{
		Map<Prefab, GenerationConfig> map = new HashMap<>();

		for (PrefabRef prefabRef : PrefabRef.values())
		{
			map.put(prefabRef.get(),
					GenerationConfig.builder().generationLocation(GenerationLocation.X_BOUNDARIES)
							.weight(5f).build());
		}

		return map;
	}

	private static Map<Prefab, GenerationState> prefabStates()
	{
		Map<Prefab, GenerationState> map = new HashMap<>();

		for (PrefabRef prefabRef : PrefabRef.values())
		{
			map.put(prefabRef.get(), new GenerationState());
		}

		return map;
	}

	private static List<Rule<GenerationRuleRequest, GenerationRuleResponse>> createRules()
	{
		List<Rule<GenerationRuleRequest, GenerationRuleResponse>> rules = Lists.newArrayList();
		rules.add(new LowTimeRule());
		rules.add(new RandomisationRule());

		return rules;
	}

	private static GenerationRuleRequest buildRuleRequest(ComponentManager componentManager,
			EntityManager entityManager)
	{
		Entity player = componentManager.getEntity(PlayerComponent.class);
		Fuel fuel = player.getComponent(Fuel.class);
		RemainingTime remainingTime = player.getComponent(RemainingTime.class);


		generationRuleRequest.setFuel(fuel.getFuel());
		generationRuleRequest.setTime(remainingTime.getRemainingSeconds());
		if (generationRuleResponse.getTransform() != null)
		{
			generationRuleRequest.setLastGeneratedTransform(generationRuleResponse.getTransform());
		}
		else
		{
			generationRuleRequest.setLastGeneratedTransform(player.getTransform());
		}
		return generationRuleRequest;
	}

	private static GenerationRuleResponse buildRuleResponse(ComponentManager componentManager,
			EntityManager entityManager)
	{
		return generationRuleResponse;
	}
}
