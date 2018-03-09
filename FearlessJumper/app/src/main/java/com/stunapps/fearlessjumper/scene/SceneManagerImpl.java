package com.stunapps.fearlessjumper.scene;

import android.util.Log;

import com.google.inject.Inject;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.game.GameEvent;
import com.stunapps.fearlessjumper.event.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.game.MainMenuEvent;
import com.stunapps.fearlessjumper.event.game.StartGameEvent;
import com.stunapps.fearlessjumper.event.EventException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunny.s on 12/02/18.
 */

public class SceneManagerImpl implements SceneManager
{
	private static final String TAG = SceneManagerImpl.class.getSimpleName();
	private final SoundSystem soundSystem;

	private StateMachine<Class<? extends Scene>, Class<? extends BaseEvent>> sceneStateMachine;
	private Map<Class<? extends Scene>, Scene> sceneMap;

	private BaseEventListener<MainMenuEvent> mainMenuListener = new BaseEventListener<MainMenuEvent>()
	{
		@Override
		public void handleEvent(MainMenuEvent event) throws EventException
		{
			transitScene(event);
		}
	};

	private BaseEventListener<StartGameEvent> startGameListener = new BaseEventListener<StartGameEvent>()
	{
		@Override
		public void handleEvent(StartGameEvent event) throws EventException
		{
			transitScene(event);
		}
	};

	@Inject
	public SceneManagerImpl(MainMenuScene mainMenuScene, GameplayScene gameplayScene, EventSystem eventSystem, SoundSystem soundSystem)
	{
		this.soundSystem = soundSystem;
		Log.i("SCENE_MANAGER",
				getClass().getSimpleName() + " Scene Manager hash code: " + hashCode());

		sceneMap = new HashMap<>();
		sceneMap.put(mainMenuScene.getClass(), mainMenuScene);
		sceneMap.put(gameplayScene.getClass(), gameplayScene);

		eventSystem.registerEventListener(StartGameEvent.class, startGameListener);
		eventSystem.registerEventListener(MainMenuEvent.class, mainMenuListener);
	}

	@Override
	public void initialise()
	{
		sceneStateMachine =
				StateMachine.builder().startState(MainMenuScene.class)
						.from(MainMenuScene.class).onEvent(StartGameEvent.class).toState(GameplayScene.class)
						.from(GameplayScene.class).onEvent(GameOverEvent.class).toState(GameplayScene.class)
						.from(GameplayScene.class).onEvent(StartGameEvent.class).toState(GameplayScene.class)
						.from(GameplayScene.class).onEvent(MainMenuEvent.class).toState(MainMenuScene.class)
						.build();

		soundSystem.initialise();
//		soundSystem.loopMusic(Sound.BACKGROUND_MUSIC);

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
	public void stop()
	{
		Log.i(TAG, "Scene manager stop at scene: " + sceneStateMachine.getCurrentState());
		sceneMap.get(sceneStateMachine.getCurrentState()).stop();
	}

	private void transitScene(GameEvent event)
	{
		//Terminate current scene.
		sceneMap.get(sceneStateMachine.getCurrentState()).terminate();

		//Get new scene.
		Class<? extends Scene> nextScene = sceneStateMachine.transitStateOnEvent(event.eventType);
		Scene scene = sceneMap.get(nextScene);

		//Initialize new scene.
		scene.setup();
		scene.play();
		scene.resume();
	}
}
