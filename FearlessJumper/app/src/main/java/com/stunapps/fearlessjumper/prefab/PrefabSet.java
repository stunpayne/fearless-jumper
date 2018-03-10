package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.transform.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny.s on 09/03/18.
 */

public abstract class PrefabSet extends Prefab
{
	private List<Instantiable> instantiables;

	public PrefabSet()
	{
		this.instantiables = new ArrayList<>();
	}

	@Override
	public List<Instantiable> getInstantiables()
	{
		return instantiables;
	}

	protected void addPrefab(Prefab prefab, Transform relativeTransform)
	{
		List<Instantiable> instantiables = prefab.getInstantiables();
		for (Instantiable instantiable : instantiables)
		{
			this.instantiables
					.add(new Instantiable(instantiable.getComponents(), relativeTransform));
		}
	}

	@Override
	public <C extends Component> C getComponent(Class<C> componentType)
	{
		throw new UnsupportedOperationException();
	}
}
