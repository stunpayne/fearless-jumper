package com.stunapps.fearlessjumper.scene;

import android.util.Log;
import android.view.MotionEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

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


	@Inject
	public SceneManagerImpl(Provider<MainMenuScene> mainMenuSceneProvider,
			Provider<GameplayScene> gameplaySceneProvider)
	{
		ACTIVE_SCENE = 0;
		Log.i("SCENE_MANAGER",
			  getClass().getSimpleName() + " Scene Manager hash code: " + hashCode());
		this.mainMenuSceneProvider = mainMenuSceneProvider;
		this.gameplaySceneProvider = gameplaySceneProvider;
	}

	@Override
	public void start()
	{
		//	Populate scenes
		scenes.add(mainMenuSceneProvider.get());
		scenes.add(gameplaySceneProvider.get());

		//	Start the active scene
		scenes.get(ACTIVE_SCENE).setActive();
		scenes.get(ACTIVE_SCENE).play();
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
		start();
	}
}
