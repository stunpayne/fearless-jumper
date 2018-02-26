package com.stunapps.fearlessjumper.scene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.audio.SoundSystem;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.game.MainMenuEvent;
import com.stunapps.fearlessjumper.event.game.StartGameEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Environment;

import java.util.concurrent.Callable;

import javax.inject.Inject;


/**
 * Created by sunny.s on 12/02/18.
 */

public class GameplayScene extends AbstractScene
{
	private static final String TAG = "GamePlayScene";
	private GameView gameView;

	@Inject
	public GameplayScene(GameView gameView, EventSystem eventSystem)
	{
		super(R.layout.game_play_container, eventSystem);
		eventSystem.registerEventListener(GameOverEvent.class, gameOverListener);
		this.gameView = gameView;
	}

	private BaseEventListener<GameOverEvent> gameOverListener = new BaseEventListener<GameOverEvent>()
	{
		@Override
		public void handleEvent(GameOverEvent event) throws EventException
		{
			modifyScene(new Callable()
			{
				@Override
				public Object call() throws Exception
				{
					gameView.getThread().setRunning(false);

					LayoutInflater inflater = LayoutInflater.from(Environment.CONTEXT);
					RelativeLayout gameOver = (RelativeLayout) inflater.inflate(R.layout.game_over, null);
					((FrameLayout)view).addView(gameOver);

					Button mainMenuButton = (Button)gameOver.findViewById(R.id.mainMenu);
					mainMenuButton.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							eventSystem.raiseEvent(new MainMenuEvent());
						}
					});

					Button restartButton = (Button)gameOver.findViewById(R.id.restart);
					restartButton.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							eventSystem.raiseEvent(new StartGameEvent());
						}
					});

					return null;
				}
			});
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
		((FrameLayout)view).removeAllViews();
		((FrameLayout)view).invalidate();
	}
}
