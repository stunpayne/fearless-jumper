package com.stunapps.fearlessjumper.system.input;

import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;
import com.stunapps.fearlessjumper.manager.InputUpdateManager;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem;

import java.util.Set;

/**
 * Created by anand.verma on 14/01/18.
 */

@Singleton
public class GameInputSystem implements InputSystem
{
	private final ComponentManager componentManager;
	private final InputUpdateManager inputUpdateManager;

	@Inject
	public GameInputSystem(ComponentManager componentManager, InputUpdateSystem inputUpdateManager)
	{
		this.componentManager = componentManager;
		this.inputUpdateManager = inputUpdateManager;
	}


	@Override
	public void processTouchInput(MotionEvent motionEvent)
	{
		inputUpdateManager.updateState(motionEvent);
	}

	private Set<Entity> getAllEntities()
	{
		return componentManager.getEntities(Input.class);
	}
}
