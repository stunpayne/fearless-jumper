package com.stunapps.fearlessjumper.rules;

import android.util.Log;

import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.specific.RemainingTime;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.entity.EntityManager;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.execution.RuleExecutor;
import com.stunapps.fearlessjumper.rules.execution.generation.GenerationRule;
import com.stunapps.fearlessjumper.rules.execution.generation.GenerationRuleExecutor;
import com.stunapps.fearlessjumper.rules.execution.generation.GenerationRuleRequest;
import com.stunapps.fearlessjumper.rules.execution.generation.GenerationRuleResponse;
import com.stunapps.fearlessjumper.rules.execution.generation.LowTimeRule;
import com.stunapps.fearlessjumper.rules.execution.generation.MaxInARowRule;
import com.stunapps.fearlessjumper.rules.execution.generation.RandomisationRule;
import com.stunapps.fearlessjumper.rules.execution.generation.enums.GenerationLocation;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stunapps.fearlessjumper.game.Environment.scaleX;

/**
 * Created by sunny.s on 19/04/18.
 */

public class RuleEngine
{
	private static final String TAG = RuleEngine.class.getSimpleName();
	private static RuleExecutor<GenerationRuleRequest, GenerationRuleResponse> ruleExecutor;
	private static GenerationRuleRequest generationRuleRequest;
	private static GenerationRuleResponse generationRuleResponse;

	private static boolean initialised = false;

	static
	{
		ruleExecutor = new GenerationRuleExecutor(createRules());
	}

	public RuleEngine()
	{
	}

	public static GenerationRuleResponse execute(ComponentManager componentManager,
			EntityManager entityManager)
	{
		initialise();
		ruleExecutor.execute(buildRuleRequest(componentManager, entityManager),
							 buildRuleResponse(componentManager, entityManager));
		updatePrefabStates();

		return generationRuleResponse;
	}

	public static void reset()
	{
		reinitialise();
	}

	public static void signal()
	{

	}

	private static void reinitialise()
	{
		initialised = false;
		initialise();
	}

	private static void initialise()
	{
		if (initialised) return;
		generationRuleRequest = initRequest();
		generationRuleResponse = initResponse();

		initialised = true;
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

	private static Map<PrefabRef, GenerationConfig> prefabConfigs()
	{
		Map<PrefabRef, GenerationConfig> map = new HashMap<>();

		for (PrefabRef prefabRef : PrefabRef.values())
		{
			if (prefabRef == PrefabRef.SHOOTER_DRAGON)
			{
				map.put(prefabRef,
						GenerationConfig.builder().generationLocation(GenerationLocation.X_LEFT)
								.maxMarginX(scaleX(50)).build());
			}
			else if (prefabRef == PrefabRef.PLATFORM)
			{
				map.put(prefabRef,
						GenerationConfig.builder().generationLocation(GenerationLocation
																			  .X_BOUNDARY)
								.maxMarginX(scaleX(50)).build());
			}
			else
			{
				map.put(prefabRef,
						GenerationConfig.builder().generationLocation(GenerationLocation
																			  .X_ANYWHERE)
								.maxMarginX(scaleX(50)).build());
			}
		}

		return map;
	}

	private static Map<PrefabRef, GenerationState> prefabStates()
	{
		Map<PrefabRef, GenerationState> map = new HashMap<>();

		for (PrefabRef prefabRef : PrefabRef.values())
		{
			map.put(prefabRef, new GenerationState());
		}

		return map;
	}

	private static List<GenerationRule> createRules()
	{
		List<GenerationRule> rules = Lists.newArrayList();
		rules.add(new MaxInARowRule());
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
		generationRuleResponse = initResponse();
		return generationRuleResponse;
	}

	private static void updatePrefabStates()
	{
		Map<PrefabRef, GenerationConfig> prefabConfig = generationRuleRequest.getPrefabConfig();
		Map<PrefabRef, GenerationState> prefabStates =
				generationRuleRequest.getCurrentPrefabStates();
		Map<PrefabRef, GenerationState> updatedPrefabStates =
				generationRuleResponse.getUpdatedPrefabStates();
		PrefabRef nextPrefab = generationRuleResponse.getNextPrefab();

		for (PrefabRef prefabRef : prefabConfig.keySet())
		{
			prefabStates.get(prefabRef).updateFrom(updatedPrefabStates.get(prefabRef));
			updateCount(prefabRef, nextPrefab, prefabStates.get(prefabRef));
		}
	}

	private static void updateCount(PrefabRef prefabRef, PrefabRef nextPrefab,
			GenerationState prefabState)
	{
		if (prefabRef.equals(nextPrefab))
		{
			Log.d(TAG, "Next Prefab: " + nextPrefab.name());
			prefabState.incrementCurrInARow();
		}
		else
		{
			prefabState.resetCurrInARow();
		}
	}
}
