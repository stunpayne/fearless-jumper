package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;

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

	@Override
	public float getWidth()
	{
		float width = 0;
		for (Instantiable instantiable : instantiables)
		{
			for (Component component : instantiable.getComponents())
			{
				//	Breaking at the end of this because one instantiable can have only one
				// renderable or shape renderable
				if (component.componentType == Renderable.class)
				{
					Renderable renderable = (Renderable) component;
					width += renderable.delta.x + renderable.width;
					break;
				}
				else if (component.componentType == ShapeRenderable.class)
				{
					width += ((ShapeRenderable) component).getWidth();
					break;
				}
			}
		}
		return 0;
	}
}
