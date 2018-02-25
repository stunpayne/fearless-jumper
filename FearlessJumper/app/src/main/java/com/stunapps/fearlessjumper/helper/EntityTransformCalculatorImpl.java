package com.stunapps.fearlessjumper.helper;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.prefab.Prefab;

/**
 * Created by sunny.s on 13/01/18.
 */

public class EntityTransformCalculatorImpl implements EntityTransformCalculator
{
	@Override
	public float getWidth(Entity entity)
	{
		return 0;
	}

	@Override
	public float getHeight(Entity entity)
	{
		return 0;
	}

	@Override
	public float getWidth(Prefab prefab)
	{
		Component component = prefab.getComponent(Renderable.class);
		if (component != null)
		{
			Renderable renderable = (Renderable) component;
			return ((Bitmap) renderable.getRenderable()).getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight(Prefab prefab)
	{
		return 0;
	}
}
