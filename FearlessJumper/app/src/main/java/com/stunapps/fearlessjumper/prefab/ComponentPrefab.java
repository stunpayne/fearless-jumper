package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.transform.Transform.Rotation;
import com.stunapps.fearlessjumper.component.transform.Transform.Scale;
import com.stunapps.fearlessjumper.model.Position;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by sunny.s on 10/03/18.
 */

public abstract class ComponentPrefab extends Prefab
{
	public Transform transform;

	@Getter
	protected List<Component> components;
	protected List<Instantiable> instantiables;

	public ComponentPrefab()
	{
		this.transform = new Transform(Position.ORIGIN, Rotation.NO_ROTATION, Scale.UNIT_SCALE);
		this.components = new ArrayList<>();
		this.instantiables = new ArrayList<>();
	}

	public <C extends Component> C getComponent(Class<C> componentType)
	{
		for (Component component : components)
		{
			if (component.componentType == componentType)
			{
				return componentType.cast(component);
			}
		}
		return null;
	}

	protected void addComponent(Component component)
	{
		addComponent(component);
		instantiables.add(new Instantiable(components, Transform.ORIGIN));
	}

	@Override
	public final List<Instantiable> getInstantiables()
	{
		return instantiables;
	}
}
