package com.stunapps.fearlessjumper.system.input;

import android.hardware.SensorEvent;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.Delta;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.input.OrientationInput;
import com.stunapps.fearlessjumper.component.transform.Transform;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem;

import java.util.Set;

/**
 * Created by anand.verma on 14/01/18.
 */

@Singleton
public class GameInputSystem implements InputSystem
{
	private final ComponentManager componentManager;
	private final InputUpdateSystem inputUpdateSystem;
	private final InputProcessorFactory inputProcessorFactory;

	@Inject
	public GameInputSystem(ComponentManager componentManager, InputUpdateSystem inputUpdateSystem,
			InputProcessorFactory inputProcessorFactory)
	{
		this.componentManager = componentManager;
		this.inputUpdateSystem = inputUpdateSystem;
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
				InputProcessor inputProcessor = inputProcessorFactory.getProcessor(entity);
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
		inputUpdateSystem.updateState(motionEvent);
	}

	private Set<Entity> getAllEntities()
	{
		return componentManager.getEntities(Input.class);
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
