package com.stunapps.fearlessjumper.instantiation.prefab.sets.impl;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;
import com.stunapps.fearlessjumper.instantiation.prefab.sets.PrefabSet;
import com.stunapps.fearlessjumper.model.Position;

/**
 * Created by sunny.s on 10/03/18.
 */

public class ShooterDragonPrefabSet extends PrefabSet
{
	public ShooterDragonPrefabSet()
	{
		super();
		addPrefab(PrefabRef.GROUNDED_DRAGON.get(), new Transform(Position.ORIGIN));
	}
}
