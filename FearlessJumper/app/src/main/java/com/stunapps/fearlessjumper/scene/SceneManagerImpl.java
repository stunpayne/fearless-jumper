package com.stunapps.fearlessjumper.scene;

import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.impl.GameOverEvent;
import com.stunapps.fearlessjumper.event.impl.StartGameEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.event.EventSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny.s on 12/02/18.
 */

public class SceneManagerImpl implements SceneManager
{
	private final Provider<MainMenuScene> mainMenuSceneProvider;
	private final Provider<GameplayScene> gameplaySceneProvider;

	private List<Scene> scenes = new ArrayList<>();
	public static int ACTIVE_SCENE;

	private BaseEventListener<StartGameEvent> startGameListener = new BaseEventListener<StartGameEvent>()
	{
		@Override
		public void handleEvent(StartGameEvent event) throws EventException
		{
			if (ACTIVE_SCENE == 0)
			{
				goToNextScene();
			}
		}
	};

	private BaseEventListener<GameOverEvent> gameOverListener = new BaseEventListener<GameOverEvent>()
	{
		@Override
		public void handleEvent(GameOverEvent event) throws EventException
		{

		}
	};

	@Inject
	public SceneManagerImpl(Provider<MainMenuScene> mainMenuSceneProvider,
			Provider<GameplayScene> gameplaySceneProvider, EventSystem eventSystem)
	{
		ACTIVE_SCENE = 0;
		Log.i("SCENE_MANAGER",
			  getClass().getSimpleName() + " Scene Manager hash code: " + hashCode());

		this.mainMenuSceneProvider = mainMenuSceneProvider;
		this.gameplaySceneProvider = gameplaySceneProvider;
		eventSystem.registerEventListener(StartGameEvent.class, startGameListener);
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
