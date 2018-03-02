package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.transform.Transform.Rotation;
import com.stunapps.fearlessjumper.component.transform.Transform.Scale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny.s on 10/01/18.
 */

public abstract class Prefab
{
	public Transform transform;
	public List<Component> components;

	public Prefab()
	{
		this.transform = new Transform(Position.ORIGIN, Rotation.NO_ROTATION, Scale.UNIT_SCALE);
		this.components = new ArrayList<>();
	}

	public Component getComponent(Class<? extends Component> componentType)
	{
		for (Component component : components)
		{
			if (component.componentType == componentType)
			{
				return component;
			}
		}
		return null;
	}
}
