package com.stunapps.fearlessjumper.helper;

import android.graphics.Bitmap;

import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.instantiation.prefab.Prefab;

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
		Renderable bitmapRenderable = prefab.getComponent(Renderable.class);
		if (bitmapRenderable != null)
		{
			return bitmapRenderable.getRenderable().getWidth();
		}

		ShapeRenderable shapeRenderable = prefab.getComponent(ShapeRenderable.class);
		if (shapeRenderable != null)
		{
			return shapeRenderable.getWidth();
		}
		return 0;
	}

	@Override
	public float getHeight(Prefab prefab)
	{
		Renderable component = prefab.getComponent(Renderable.class);
		if (component != null)
		{
			return ((Bitmap) component.getRenderable()).getHeight();
		}
		return 0;
	}
}
