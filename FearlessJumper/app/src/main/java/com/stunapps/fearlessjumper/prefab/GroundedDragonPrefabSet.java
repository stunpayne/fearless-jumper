package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculatorImpl;
import com.stunapps.fearlessjumper.model.Position;

/**
 * Created by sunny.s on 09/03/18.
 */

public class GroundedDragonPrefabSet extends PrefabSet
{
	public GroundedDragonPrefabSet()
	{
		super();
		EntityTransformCalculator calculator = new EntityTransformCalculatorImpl();

		entries.add(new PrefabSetEntry(PrefabRef.GROUNDED_DRAGON, new Transform(Position.ORIGIN)));

		float height = calculator.getHeight(PrefabRef.GROUNDED_DRAGON.get());
		entries.add(new PrefabSetEntry(PrefabRef.PLATFORM, new Transform(new Position(0, height)
		)));
	}
}
