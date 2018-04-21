package com.stunapps.fearlessjumper.prefab;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.component.transform.Transform.Rotation;
import com.stunapps.fearlessjumper.component.transform.Transform.Scale;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by sunny.s on 10/01/18.
 */

public abstract class Prefab implements Instantiator
{
	abstract public <C extends Component> C getComponent(Class<C> componentType);

	@Override
	public String toString()
	{
		return "Prefab{class = " + this.getClass().getSimpleName() + "}";
	}
}
