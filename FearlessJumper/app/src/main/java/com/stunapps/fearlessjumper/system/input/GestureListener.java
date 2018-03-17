package com.stunapps.fearlessjumper.system.input;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;

import java.util.Set;

/**
 * Created by sunny.s on 18/03/18.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener
{
	private final ComponentManager componentManager;
	private final InputProcessorFactory inputProcessorFactory;

	@Inject
	public GestureListener(ComponentManager componentManager,
			InputProcessorFactory inputProcessorFactory)
	{
		this.componentManager = componentManager;
		this.inputProcessorFactory = inputProcessorFactory;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
		super.onShowPress(e);
		Set<Entity> entitySet = componentManager.getEntities(Input.class);
		if (!entitySet.isEmpty())
		{
			Entity entity = entitySet.iterator().next();

			if (entity.hasComponent(PlayerComponent.class))
			{
				inputProcessorFactory.get(PlayerComponent.class).process(entity, e);
			}
		}
	}
}
