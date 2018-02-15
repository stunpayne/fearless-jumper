package com.stunapps.fearlessjumper.scene;

import android.view.MotionEvent;

import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.game.loop.GameView;

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
	public void playScene()
	{

	}

	@Override
	public void tearDownScene()
	{

	}

	@Override
	public void receiveTouch(MotionEvent motionEvent)
	{

	}

	@Override
	void setUpScene()
	{

	}
}