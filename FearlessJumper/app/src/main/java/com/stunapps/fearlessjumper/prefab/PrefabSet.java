package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.transform.Transform;

import org.roboguice.shaded.goole.common.collect.Sets;

import java.util.Set;

import lombok.AllArgsConstructor;

/**
 * Created by sunny.s on 09/03/18.
 */

public abstract class PrefabSet
{
	private Set<PrefabSetEntry> entries;

	public PrefabSet()
	{
		this.entries = Sets.newHashSet();
	}

	public Set<PrefabSetEntry> getEntries()
	{
		return entries;
	}

	@AllArgsConstructor
	public class PrefabSetEntry
	{
		Prefab prefab;
		Transform relativeTransform;

		public Prefab getPrefab()
		{
			return prefab;
		}

		public Transform getRelativeTransform()
		{
			return relativeTransform;
		}
	}
}
