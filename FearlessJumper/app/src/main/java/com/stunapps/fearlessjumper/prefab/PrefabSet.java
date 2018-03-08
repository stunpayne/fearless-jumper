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
	protected Set<PrefabSetEntry> entries;

	public PrefabSet()
	{
		this.entries = Sets.newHashSet();
	}

	public Set<PrefabSetEntry> getEntries()
	{
		return entries;
	}


	public class PrefabSetEntry
	{
		Prefab prefab;
		Transform relativeTransform;

		public PrefabSetEntry(Prefab prefab, Transform relativeTransform)
		{
			this.prefab = prefab;
			this.relativeTransform = relativeTransform;
		}

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
