package com.stunapps.fearlessjumper.system.update;

import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.input.Input;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessorFactory;

import java.util.Set;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static com.stunapps.fearlessjumper.system.update.InputUpdateSystem.State.SCREEN_PRESSED;
import static com.stunapps.fearlessjumper.system.update.InputUpdateSystem.State.SCREEN_RELEASED;

/**
 * Created by sunny.s on 18/03/18.
 */

@Singleton
public class InputUpdateSystem implements UpdateSystem
{
	private static final String TAG = InputUpdateSystem.class.getSimpleName();
	private final ComponentManager componentManager;
	private final InputProcessorFactory inputProcessorFactory;

	private StateMachine<State, Integer> inputStateMachine;

	private static long lastProcessTime = 0;

	@Inject
	public InputUpdateSystem(ComponentManager componentManager,
			InputProcessorFactory inputProcessorFactory)
	{
		this.componentManager = componentManager;
		this.inputProcessorFactory = inputProcessorFactory;
		inputStateMachine = StateMachine.builder().startState(SCREEN_RELEASED).from
				(SCREEN_RELEASED)
				.onEvent(ACTION_DOWN).toState(SCREEN_PRESSED).from(SCREEN_PRESSED)
				.onEvent(ACTION_UP).toState(SCREEN_RELEASED).build();
	}

	@Override
	public void process(long deltaTime)
	{
		//	TODO: Replace with all entities
		Entity player = componentManager.getEntity(PlayerComponent.class);
		State screenTouchState = inputStateMachine.getCurrentState();
		InputProcessor inputProcessor = getInputProcessor(player);
		if (null != inputProcessor)
		{
			inputProcessor.update(deltaTime, player, screenTouchState);
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

	public void updateState(MotionEvent motionEvent)
	{
		inputStateMachine.transitStateOnEvent(motionEvent.getAction());
	}

	public enum State
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
