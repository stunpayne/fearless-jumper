package com.stunapps.fearlessjumper.scene;

import android.view.MotionEvent;

import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.scene.AbstractScene;

import javax.inject.Inject;


/**
 * Created by sunny.s on 12/02/18.
 */

public class GameplayScene extends AbstractScene
{
	@Inject
	public GameplayScene(GameView gameView, EventSystem eventSystem, SoundSystem soundSystem)
	{
		super(gameView, eventSystem, soundSystem);
	}

	@Override
	void setUpScene()
	{

	}

	@Override
	public void playScene()
	{

	}

	@Override
	public void terminateScene()
	{

	}
}
