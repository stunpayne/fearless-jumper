package com.stunapps.fearlessjumper.system.update;

import android.graphics.Bitmap;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.visual.Animator;
import com.stunapps.fearlessjumper.component.visual.Renderable;
import com.stunapps.fearlessjumper.entity.Entity;

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

		Set<Entity> animated = componentManager.getEntities(Animator.class);

		for (Entity entity : animated)
		{
			Animator animator = entity.getComponent(Animator.class);
			Bitmap nextBitmap = animator.update();
			entity.getComponent(Renderable.class).setSprite(nextBitmap);
		}
	}

	@Override
	public long getLastProcessTime()
	{
		return lastProcessTime;
	}
}
