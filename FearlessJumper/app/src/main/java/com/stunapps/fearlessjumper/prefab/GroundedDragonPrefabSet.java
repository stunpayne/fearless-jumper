package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.model.Position;

import java.util.Set;

/**
 * Created by sunny.s on 09/03/18.
 */

public class GroundedDragonPrefabSet extends PrefabSet
{
	public GroundedDragonPrefabSet(Set<PrefabSetEntry> entries)
	{
		super();
		entries.add(new PrefabSetEntry(Prefabs.DRAGON.get(), new Transform(Position.ORIGIN)));
		entries.add(new PrefabSetEntry(Prefabs.PLATFORM.get(), new Transform(new Position(0, 150)
		)));
	}
}
