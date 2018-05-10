package com.stunapps.fearlessjumper.instantiation.prefab.sets.impl;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculator;
import com.stunapps.fearlessjumper.helper.EntityTransformCalculatorImpl;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;
import com.stunapps.fearlessjumper.instantiation.prefab.sets.PrefabSet;
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

		addPrefab(PrefabRef.GROUNDED_DRAGON.get(), new Transform(Position.ORIGIN));

		float height = calculator.getHeight(PrefabRef.GROUNDED_DRAGON.get());
		addPrefab(PrefabRef.PLATFORM.get(), new Transform(new Position(0, height)));
	}
}
