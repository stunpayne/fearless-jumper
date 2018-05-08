package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.core.Shuffler;
import com.stunapps.fearlessjumper.core.WeightedShuffler;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.prefab.Prefab;
import com.stunapps.fearlessjumper.prefab.PrefabRef;
import com.stunapps.fearlessjumper.rules.execution.Rule;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationConfig;
import com.stunapps.fearlessjumper.rules.execution.generation.model.GenerationState;

import java.util.Map;

import static com.stunapps.fearlessjumper.helper.Randomiser.nextFloat;

/**
 * Created by sunny.s on 19/04/18.
 */

public abstract class GenerationRule extends Rule<GenerationRuleRequest, GenerationRuleResponse>
{
	private static final float NEW_OBSTACLE_OFFSET = -600f;

	private static Shuffler<PrefabRef> obstacleShuffler;

	static
	{
		initShuffler();
	}

	protected static PrefabRef shuffle()
	{
		return obstacleShuffler.shuffle();
	}

	void setNextPrefab(GenerationRuleRequest request, GenerationRuleResponse response,
			PrefabRef prefabRef)
	{
		response.setNextPrefab(prefabRef);
		response.setTransform(getPrefabLocation(request, prefabRef));
	}

	void blockPrefab(GenerationRuleRequest request, GenerationRuleResponse response,
			PrefabRef prefabRef)
	{
		response.blockPrefab(prefabRef);
		block(prefabRef);
	}

	private static void block(PrefabRef prefabRef)
	{
		obstacleShuffler.ignore(prefabRef);
	}

	void unblockPrefab(GenerationRuleRequest request, GenerationRuleResponse response,
			PrefabRef prefabRef)
	{
		response.unblockPrefab(prefabRef);
		unblock(prefabRef);
	}

	private static void unblock(PrefabRef prefabRef)
	{
		obstacleShuffler.restore(prefabRef);
	}

	private Transform getPrefabLocation(GenerationRuleRequest request, PrefabRef prefabRef)
	{
		return LocationGenerator.generate(request, prefabRef, request.getLastGeneratedTransform());
	}

	private static void initShuffler()
	{
		if (obstacleShuffler == null)
		{
			obstacleShuffler =
					new WeightedShuffler.Builder<PrefabRef>()
							.returnItem(PrefabRef.CLOCK).withWeight(3f)
							.returnItem(PrefabRef.PLATFORM).withWeight(5f)
							.returnItem(PrefabRef.FLYING_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.SHOOTER_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.ASSAULT_DRAGON).withWeight(2f)
							.returnItem(PrefabRef.FOLLOWING_DRAGON).withWeight(1f)
							.returnItem(PrefabRef.GROUNDED_DRAGON_SET).withWeight(1f)
							.returnItem(PrefabRef.UNFRIENDLY_PLATFORM).withWeight(5f).build();
		}
	}

	private static class LocationGenerator
	{
		static Transform generate(GenerationRuleRequest request, PrefabRef prefabRef,
				Transform lastTransform)
		{
			Prefab prefab = prefabRef.get();
			Transform newTransform = Transform.withYShift(lastTransform, NEW_OBSTACLE_OFFSET);
			GenerationConfig generationConfig = request.getPrefabConfig().get(prefabRef);
			switch (generationConfig.getGenerationLocation())
			{
				case X_ANYWHERE:
					if (null != lastTransform)
					{
						float newX =
								(float) Math.random() * (Device.SCREEN_WIDTH - prefab.getWidth());
						newTransform.getPosition().setX(newX);
						return newTransform;
					}
				case X_LEFT:
				{
					float xLeft = nextFloat(generationConfig.getMaxMarginX());
					newTransform.getPosition().setX(xLeft);
					return newTransform;
				}
				case X_RIGHT:
				{
					float xRight = Device.SCREEN_WIDTH - prefab.getWidth() -
							nextFloat(generationConfig.getMaxMarginX());
					newTransform.getPosition().setX(xRight);
					return newTransform;
				}
				default:
					return null;
			}
		}
	}
}
