package com.stunapps.fearlessjumper.instantiation;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.transform.Transform;

import java.util.List;

import lombok.Getter;

/**
 * Created by sunny.s on 10/03/18.
 */

@Getter
public class Instantiable
{
	List<Component> components;
	Transform relativeTransform;

	public Instantiable(List<Component> components, Transform relativeTransform)
	{
		this.components = components;
		this.relativeTransform = relativeTransform;
	}
}
