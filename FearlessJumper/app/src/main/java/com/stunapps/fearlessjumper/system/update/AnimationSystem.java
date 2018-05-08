package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.component.visual.BitmapAnimator;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.component.visual.Shape;
import com.stunapps.fearlessjumper.component.visual.ShapeAnimator;
import com.stunapps.fearlessjumper.component.visual.ShapeRenderable;
import com.stunapps.fearlessjumper.entity.Entity;

import java.util.List;
import java.util.Set;

/**
 * Created by sunny.s on 25/02/18.
 */

public class AnimationSystem implements UpdateSystem
{
	private final ComponentManager componentManager;

	private static long lastProcessTime = 0;

	@Inject
	public AnimationSystem(ComponentManager componentManager)
	{
		this.componentManager = componentManager;
	}

	@Override
	public void process(long deltaTime)
	{
		lastProcessTime = System.currentTimeMillis();

		Set<Entity> bitmapAnimatorEntities = componentManager.getEntities(BitmapAnimator.class);
		for (Entity bitmapAnimatorEntity : bitmapAnimatorEntities)
		{
			Animator bitmapAnimator = bitmapAnimatorEntity.getComponent(BitmapAnimator.class);
			Bitmap nextBitmap = (Bitmap) bitmapAnimator.update();
			bitmapAnimatorEntity.getComponent(Renderable.class).setSprite(nextBitmap);
		}

		Set<Entity> shapeAnimatorEntities = componentManager.getEntities(ShapeAnimator.class);
		for (Entity shapeAnimatorEntity : shapeAnimatorEntities)
		{
			Animator shapeAnimator = shapeAnimatorEntity.getComponent(ShapeAnimator.class);
			List<Shape> shapes = (List<Shape>) shapeAnimator.update();
			shapeAnimatorEntity.getComponent(ShapeRenderable.class).setShapes(shapes);
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}

	@Override
	public void reset()
	{
		lastProcessTime = 0;
	}
}
