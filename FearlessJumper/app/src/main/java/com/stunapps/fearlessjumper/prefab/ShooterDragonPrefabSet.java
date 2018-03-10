package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.transform.Transform;
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
