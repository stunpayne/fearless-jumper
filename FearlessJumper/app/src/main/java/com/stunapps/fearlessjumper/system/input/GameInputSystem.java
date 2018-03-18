package com.stunapps.fearlessjumper.system.input;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;
import com.stunapps.fearlessjumper.system.update.InputProcessSystem;

import java.util.Set;

/**
 * Created by anand.verma on 14/01/18.
 */

@Singleton
public class GameInputSystem implements InputSystem
{
	private final ComponentManager componentManager;
	private final InputProcessSystem inputProcessSystem;
	private final InputProcessorFactory inputProcessorFactory;

	@Inject
	public GameInputSystem(ComponentManager componentManager, InputProcessSystem
			inputProcessSystem,
			InputProcessorFactory inputProcessorFactory)
	{
		this.componentManager = componentManager;
		this.inputProcessSystem = inputProcessSystem;
		this.inputProcessorFactory = inputProcessorFactory;
	}

	@Override
	public void processSensorInput(SensorEvent sensorEvent)
	{
		Set<Entity> entitySet = getAllEntities();
		for (Entity entity : entitySet)
		{
			if (entity.getComponent(Input.class).respondsToSensor())
			{
				InputProcessor inputProcessor = getInputProcessor(entity);
				if (inputProcessor != null)
				{
					inputProcessor.handleSensorEvent(entity, sensorEvent);
				}
			}
		}
	}

	@Override
	public void processTouchInput(MotionEvent motionEvent)
	{
		Set<Entity> entitySet = getAllEntities();
		for (Entity entity : entitySet)
		{
			if (entity.getComponent(Input.class).respondsToTouch())
			{
//				InputProcessor inputProcessor = getInputProcessor(entity);
//				if (inputProcessor != null)
//				{
//					inputProcessor.handleTouchEvent(entity, motionEvent);
//				}
				inputProcessSystem.updateState(motionEvent);
			}
		}
	}

	private Set<Entity> getAllEntities()
	{
		return componentManager.getEntities(Input.class);
	}

	private InputProcessor getInputProcessor(Entity entity)
	{
		if (entity.hasComponent(PlayerComponent.class))
		{
			return inputProcessorFactory.get(PlayerComponent.class);
		}
		return null;
	}

	private void processOrientationInput(Entity entity)
	{
		OrientationInput input = (OrientationInput) entity.getComponent(Input.class);
		Delta delta = input.getDeltaMovement();
		Transform transform = entity.transform;
		transform.position.x += delta.x;
		transform.position.y += delta.y;
	}
}
