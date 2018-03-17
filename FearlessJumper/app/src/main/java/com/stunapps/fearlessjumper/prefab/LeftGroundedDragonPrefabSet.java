package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculatorImpl;
import com.stunapps.fearlessjumper.model.Position;

/**
 * Created by sunny.s on 09/03/18.
 */

public class LeftGroundedDragonPrefabSet extends PrefabSet
{
	public LeftGroundedDragonPrefabSet()
	{
		super();
		EntityTransformCalculator calculator = new EntityTransformCalculatorImpl();

		addPrefab(PrefabRef.GROUNDED_DRAGON.get(), new Transform(Position.ORIGIN));

		float height = calculator.getHeight(PrefabRef.GROUNDED_DRAGON.get());
		addPrefab(PrefabRef.PLATFORM.get(), new Transform(new Position(0, height)));
	}
}
