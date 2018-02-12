package com.stunapps.fearlessjumper.scene;

import android.view.MotionEvent;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny.s on 12/02/18.
 */

@Singleton
public class SceneManagerImpl implements SceneManager
{
	private List<Scene> scenes = new ArrayList<>();
	public static int ACTIVE_SCENE;

	public SceneManagerImpl()
	{
		scenes.add(new MainMenuScene(0));
//		scenes.add(new MainMenuScene());
		ACTIVE_SCENE = 0;
	}

	@Override
	public void start()
	{
		scenes.get(ACTIVE_SCENE).setActive();
		scenes.get(ACTIVE_SCENE).play();
	}

	@Override
	public void receiveTouch(MotionEvent motionEvent)
	{
		scenes.get(ACTIVE_SCENE).receiveTouch(motionEvent);
	}
}
