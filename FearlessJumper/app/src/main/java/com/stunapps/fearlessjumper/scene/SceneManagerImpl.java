package com.stunapps.fearlessjumper.scene;

import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.stunapps.fearlessjumper.core.StateMachine;
import com.stunapps.fearlessjumper.event.BaseEvent;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.impl.GameOverEvent;
import com.stunapps.fearlessjumper.event.impl.StartGameEvent;
import com.stunapps.fearlessjumper.event.game.GameEvent;
import com.stunapps.fearlessjumper.event.game.MainMenuEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.event.EventSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunny.s on 12/02/18.
 */

public class SceneManagerImpl implements SceneManager
{
	private StateMachine<Class<? extends Scene>, Class<? extends BaseEvent>> sceneStateMachine;
	private Map<Class<? extends Scene>, Scene> sceneMap;

	private Provider<MainMenuScene> mainMenuSceneProvider;
	private Provider<GameplayScene> gameplaySceneProvider;

	private List<Scene> scenes = new ArrayList<>();
	public static int ACTIVE_SCENE;

	private BaseEventListener<StartGameEvent> startGameListener = new BaseEventListener<StartGameEvent>()
	{
		@Override
		public void handleEvent(StartGameEvent event) throws EventException
		{
			transitScene(event);
		}
	};

	private BaseEventListener<GameOverEvent> gameOverListener = new BaseEventListener<GameOverEvent>()
	{
		@Override
		public void handleEvent(GameOverEvent event) throws EventException
		{
			transitScene(event);
		}
	};

	private BaseEventListener<MainMenuEvent> mainMenuListener = new BaseEventListener<MainMenuEvent>()
	{
		@Override
		public void handleEvent(MainMenuEvent event) throws EventException
		{
			transitScene(event);
		}
	};

	private void transitScene(BaseEvent event)
	{
		sceneMap.get(sceneStateMachine.getCurrentState()).terminate();
		Class<? extends Scene> sceneState = sceneStateMachine.transitStateOnEvent(event.eventType);
		Scene scene = sceneMap.get(sceneState);
		scene.play();
	}

	/*
	@Inject
	public SceneManagerImpl(Provider<MainMenuScene> mainMenuSceneProvider,
			Provider<GameplayScene> gameplaySceneProvider, EventSystem eventSystem)
	{
		ACTIVE_SCENE = 0;
		Log.i("SCENE_MANAGER",
			  getClass().getSimpleName() + " Scene Manager hash code: " + hashCode());

		this.mainMenuSceneProvider = mainMenuSceneProvider;
		this.gameplaySceneProvider = gameplaySceneProvider;

		sceneMap = new HashMap<>();
		eventSystem.registerEventListener(StartGameEvent.class, startGameListener);
		eventSystem.registerEventListener(GameOverEvent.class, gameOverListener);
	} */

	@Inject
	public SceneManagerImpl(MainMenuScene mainMenuScene,
							GameplayScene gameplayScene, GameOverScene gameOverScene, EventSystem eventSystem)
	{
		ACTIVE_SCENE = 0;
		Log.i("SCENE_MANAGER",
				getClass().getSimpleName() + " Scene Manager hash code: " + hashCode());

		sceneMap = new HashMap<>();
		sceneMap.put(mainMenuScene.getClass(), mainMenuScene);
		sceneMap.put(gameplayScene.getClass(), gameplayScene);
		sceneMap.put(gameOverScene.getClass(), gameOverScene);

		eventSystem.registerEventListener(StartGameEvent.class, startGameListener);
		eventSystem.registerEventListener(GameOverEvent.class, gameOverListener);
	}

	@Override
	public void initialise()
	{
		sceneStateMachine = StateMachine.builder()
				.startState(MainMenuScene.class)
				.from(MainMenuScene.class).onEvent(StartGameEvent.class).toState(GameplayScene.class)
				.from(GameplayScene.class).onEvent(GameOverEvent.class).toState(GameplayScene.class)
				.from(GameplayScene.class).onEvent(MainMenuEvent.class).toState(MainMenuScene.class).build();

		Scene scene = sceneMap.get(sceneStateMachine.getStartState());
		scene.play();
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
		//	Populate scenes
		scenes.add(mainMenuSceneProvider.get());
		scenes.add(gameplaySceneProvider.get());

		playActiveScene();
	}

	@Override
	public void receiveTouch(MotionEvent motionEvent)
	{
		scenes.get(ACTIVE_SCENE).receiveTouch(motionEvent);
	}

	@Override
	public void goToNextScene()
	{
		scenes.get(ACTIVE_SCENE).terminate();
		++ACTIVE_SCENE;
		playActiveScene();
	}

	private void playActiveScene()
	{
		//	Start the active scene
		scenes.get(ACTIVE_SCENE).setActive();
		scenes.get(ACTIVE_SCENE).play();
	}

	private void jumpToMainMenu()
	{
		ACTIVE_SCENE = 0;
		playActiveScene();
	}
}
