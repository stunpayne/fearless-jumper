package com.stunapps.fearlessjumper.system.update;

import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.input.SensorDataAdapter;
import com.stunapps.fearlessjumper.component.input.SensorDataAdapter.SensorData;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;
import com.stunapps.fearlessjumper.system.model.InputWrapper;

import java.util.Set;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static com.stunapps.fearlessjumper.system.update.InputUpdateSystem.ScreenState
		.SCREEN_PRESSED;
import static com.stunapps.fearlessjumper.system.update.InputUpdateSystem.ScreenState
		.SCREEN_RELEASED;

/**
 * Created by sunny.s on 18/03/18.
 */

@Singleton
public class InputUpdateSystem implements UpdateSystem, InputUpdateManager
{
	private static final String TAG = InputUpdateSystem.class.getSimpleName();
	private final ComponentManager componentManager;
	private final InputProcessorFactory inputProcessorFactory;
	private final SensorDataAdapter sensorDataAdapter;

	private StateMachine<ScreenState, Integer> inputStateMachine;

	private SensorData sensorData;
	private static long lastProcessTime = 0;

	@Inject
	public InputUpdateSystem(ComponentManager componentManager,
			InputProcessorFactory inputProcessorFactory, SensorDataAdapter sensorDataAdapter)
	{
		this.componentManager = componentManager;
		this.inputProcessorFactory = inputProcessorFactory;
		this.sensorDataAdapter = sensorDataAdapter;
		inputStateMachine = StateMachine.builder()
				.startState(SCREEN_RELEASED)
				.from(SCREEN_RELEASED).onEvent(ACTION_DOWN).toState(SCREEN_PRESSED)
				.from(SCREEN_PRESSED).onEvent(ACTION_UP).toState(SCREEN_RELEASED)
				.build();
	}

	@Override
	public void process(long deltaTime)
	{
		ScreenState screenTouchState = inputStateMachine.getCurrentState();
		updateRotationState(sensorDataAdapter.update());
		Set<Entity> allEntities = getAllEntities();
		for (Entity entity : allEntities)
		{
			InputProcessor inputProcessor = getInputProcessor(entity);
			if (null != inputProcessor)
			{
				inputProcessor.update(deltaTime, entity,
						new InputWrapper(screenTouchState, this.sensorData));
			}
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

	@Override
	public void updateState(MotionEvent motionEvent)
	{
		inputStateMachine.transitStateOnEvent(motionEvent.getAction());
	}

	@Override
	public void updateRotationState(SensorData sensorData)
	{
		this.sensorData = sensorData;
	}

	public enum ScreenState
	{
		SCREEN_PRESSED, SCREEN_RELEASED;
	}

	private Set<Entity> getAllEntities()
	{
		return componentManager.getEntities(Input.class);
	}

	private InputProcessor getInputProcessor(Entity entity)
	{
		return inputProcessorFactory.getProcessor(entity);
	}
}
