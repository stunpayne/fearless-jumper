package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventException;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.game.GameEvent;
import com.stunapps.fearlessjumper.event.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.game.MainMenuEvent;
import com.stunapps.fearlessjumper.event.game.ParticleTestEvent;
import com.stunapps.fearlessjumper.event.game.StartGameEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunny.s on 01/04/18.
 */

public class LazySceneManager implements SceneManager
{
	private static final String TAG = LazySceneManager.class.getSimpleName();

	private final SoundSystem soundSystem;
	private final SceneFactory sceneFactory;

	private StateMachine<Class<? extends Scene>, Class<? extends BaseEvent>> sceneStateMachine;
	private Map<Class<? extends Scene>, Scene> sceneMap;
	private Scene currentScene;


	private BaseEventListener<StartGameEvent> startGameListener = new BaseEventListener<StartGameEvent>()
	{
		@Override
		public void handleEvent(StartGameEvent event) throws EventException
		{
			transitScene(event);
		}
	};

	private BaseEventListener<ParticleTestEvent> particleTestEventListener =
			new BaseEventListener<ParticleTestEvent>()
			{
				@Override
				public void handleEvent(ParticleTestEvent event) throws EventException
				{
					transitScene(event);
				}
			};

	@Inject
	public LazySceneManager(SoundSystem soundSystem, EventSystem eventSystem, Context context,
			SceneFactory sceneFactory)
	{
		this.soundSystem = soundSystem;
		this.sceneFactory = sceneFactory;

		sceneMap = new HashMap<>();
		sceneMap.put(MainMenuScene.class, sceneFactory.get(MainMenuScene.class));
		currentScene = sceneMap.get(MainMenuScene.class);
		eventSystem.registerEventListener(ParticleTestEvent.class, particleTestEventListener);
		eventSystem.registerEventListener(StartGameEvent.class, startGameListener);
	}


	@Override
	public void initialise()
	{
		soundSystem.initialise();

		sceneStateMachine =
				StateMachine.builder().startState(MainMenuScene.class).from(MainMenuScene.class)
						.onEvent(StartGameEvent.class).toState(GameplayScene.class)
						.from(MainMenuScene.class).onEvent(ParticleTestEvent.class)
						.toState(ParticleTestScene.class).from(GameplayScene.class)
						.onEvent(GameOverEvent.class).toState(GameplayScene.class)
						.from(GameplayScene.class).onEvent(StartGameEvent.class)
						.toState(GameplayScene.class).fromAnyStateOnEvent(MainMenuEvent.class)
						.toState(MainMenuScene.class).build();

		sceneMap.get(sceneStateMachine.getCurrentState()).resume();
		Scene scene = sceneMap.get(sceneStateMachine.getStartState());
		scene.setup();
	}

	@Override
	public void destroy()
	{
		sceneStateMachine = null;
		for (Scene scene : sceneMap.values())
		{
			scene.terminate();
		}
	}

	@Override
	public void start()
	{
		Log.i(TAG, "Scene manager start at scene: " + sceneStateMachine.getCurrentState());
		sceneMap.get(sceneStateMachine.getCurrentState()).play();
	}

	@Override
	public void stop()
	{
		Log.i(TAG, "Scene manager stop at scene: " + sceneStateMachine.getCurrentState());
		sceneMap.get(sceneStateMachine.getCurrentState()).stop();
	}

	@Override
	public void pause()
	{
		Log.i(TAG, "Scene manager pause at scene: " + sceneStateMachine.getCurrentState());
		sceneMap.get(sceneStateMachine.getCurrentState()).pause();
	}

	@Override
	public void resume()
	{
		Log.i(TAG, "Scene manager resume at scene: " + sceneStateMachine.getCurrentState());
		sceneMap.get(sceneStateMachine.getCurrentState()).resume();
	}

	@Override
	public void back()
	{
		if (!currentScene.getClass().equals(MainMenuScene.class))
		{
			transitScene(new MainMenuEvent());
		}
	}

	private void transitScene(GameEvent event)
	{
		currentScene.stop();
		currentScene.terminate();

		//	Get the type of the next scene
		Class<? extends Scene> nextSceneType =
				sceneStateMachine.transitStateOnEvent(event.eventType);
		Scene nextScene = sceneFactory.get(sceneStateMachine.getCurrentState());
		Log.d(TAG, "Next scene: " + nextScene.getClass().getSimpleName());

		nextScene.setup();
		nextScene.play();
		nextScene.resume();

		currentScene = nextScene;
	}
}
