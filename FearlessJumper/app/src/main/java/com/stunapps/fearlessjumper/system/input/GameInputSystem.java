package com.stunapps.fearlessjumper.system.input;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;
import com.stunapps.fearlessjumper.system.update.InputUpdateManager;
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
	private final InputProcessorFactory inputProcessorFactory;

	@Inject
	public GameInputSystem(ComponentManager componentManager, InputUpdateSystem inputUpdateManager,
			InputProcessorFactory inputProcessorFactory)
	{
		this.componentManager = componentManager;
		this.inputUpdateManager = inputUpdateManager;
		this.inputProcessorFactory = inputProcessorFactory;
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
