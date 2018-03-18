package com.stunapps.fearlessjumper.system.update;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.component.ComponentManager;
import com.stunapps.fearlessjumper.component.emitter.Emitter;
import com.stunapps.fearlessjumper.component.physics.PhysicsComponent;
import com.stunapps.fearlessjumper.component.specific.Fuel;
import com.stunapps.fearlessjumper.component.specific.PlayerComponent;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.entity.Entity;
import com.stunapps.fearlessjumper.model.Position;
import com.stunapps.fearlessjumper.system.input.processor.InputProcessor;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static com.stunapps.fearlessjumper.system.update.InputProcessSystem.State.SCREEN_PRESSED;
import static com.stunapps.fearlessjumper.system.update.InputProcessSystem.State.SCREEN_RELEASED;

/**
 * Created by sunny.s on 18/03/18.
 */

@Singleton
public class InputProcessSystem implements UpdateSystem
{
	private static final String TAG = InputProcessSystem.class.getSimpleName();
	private final ComponentManager componentManager;
	private InputProcessor inputProcessor;

	private StateMachine<State, Integer> inputStateMachine;

	private static long lastProcessTime = 0;

	@Inject
	public InputProcessSystem(ComponentManager componentManager, InputProcessor inputProcessor)
	{
		this.componentManager = componentManager;
		this.inputProcessor = inputProcessor;
		inputStateMachine = StateMachine.builder().startState(SCREEN_RELEASED).from(SCREEN_RELEASED)
				.onEvent(ACTION_DOWN).toState(SCREEN_PRESSED).from(SCREEN_PRESSED)
				.onEvent(ACTION_UP).toState(SCREEN_RELEASED).build();
	}

	@Override
	public void process(long deltaTime)
	{
		Entity player = componentManager.getEntity(PlayerComponent.class);
		State screenTouchState = inputStateMachine.getCurrentState();
		inputProcessor.update(deltaTime, player, screenTouchState);
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
}
