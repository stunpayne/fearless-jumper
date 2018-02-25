package com.stunapps.fearlessjumper.scene;

import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.di.DI;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.game.GameOverEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Environment;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import static com.stunapps.fearlessjumper.scene.Scene.ViewLoader.requestViewLoad;


/**
 * Created by sunny.s on 12/02/18.
 */

public class GameplayScene extends AbstractScene
{
	private GameView gameView;

	@Inject
	public GameplayScene(GameView gameView, EventSystem eventSystem, SoundSystem soundSystem)
	{
		super(R.layout.game_play_container, eventSystem, soundSystem);
		eventSystem.registerEventListener(GameOverEvent.class, gameOverListener);
		this.gameView = gameView;
	}

	private BaseEventListener<GameOverEvent> gameOverListener = new BaseEventListener<GameOverEvent>()
	{
		@Override
		public void handleEvent(GameOverEvent event) throws EventException
		{

			gameView.getThread().setRunning(false);
			LayoutInflater inflater = LayoutInflater.from(Environment.CONTEXT);
			final RelativeLayout gameover = (RelativeLayout) inflater.inflate(R.layout.game_over, null);
			modifyScene(new Callable()
			{
				@Override
				public Object call() throws Exception
				{
					((FrameLayout)view).addView(gameover);
					return null;
				}
			});
			requestViewLoad(view);
		}
	};

	@Override
	void setUpScene()
	{
		LayoutInflater inflater = LayoutInflater.from(Environment.CONTEXT);
		LinearLayout hud = (LinearLayout) inflater.inflate(R.layout.hud, null);
		((FrameLayout)view).addView(gameView);
		((FrameLayout)view).addView(hud);
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
