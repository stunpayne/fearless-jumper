package com.stunapps.fearlessjumper.scene;

import android.view.MotionEvent;

import com.stunapps.fearlessjumper.game.loop.GameView;

import javax.inject.Inject;


/**
 * Created by sunny.s on 12/02/18.
 */

public class GameplayScene extends AbstractScene
{
	@Inject
	public GameplayScene(GameView gameView)
	{
		super(gameView);
	}

	@Override
	public void play()
	{

	}

	@Override
	public void terminate()
	{

	}

	@Override
	public void receiveTouch(MotionEvent motionEvent)
	{

	}

	@Override
	protected void setupView()
	{

	}
}
