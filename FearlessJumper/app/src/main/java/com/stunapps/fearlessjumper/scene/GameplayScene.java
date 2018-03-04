package com.stunapps.fearlessjumper.scene;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;

import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.event.BaseEventListener;
import com.stunapps.fearlessjumper.event.EventSystem;
import com.stunapps.fearlessjumper.event.game.GameOverEvent;
import com.stunapps.fearlessjumper.event.game.MainMenuEvent;
import com.stunapps.fearlessjumper.event.game.StartGameEvent;
import com.stunapps.fearlessjumper.exception.EventException;
import com.stunapps.fearlessjumper.game.loop.GameView;
import com.stunapps.fearlessjumper.helper.Environment;

import javax.inject.Inject;


/**
 * Created by sunny.s on 12/02/18.
 */

public class GameplayScene extends AbstractScene
{
	private static final String TAG = "GamePlayScene";

	private GameView gameView;
	private View hud;
	private View pauseMenu;
	private View pauseButton;
	private View gameOverMenu;

	private ViewSetup viewSetup = new ViewSetup();
	private Handler mainHandler;

	@Inject
	public GameplayScene(final GameView gameView, EventSystem eventSystem)
	{
		super(R.layout.game_play_container, eventSystem);
		eventSystem.registerEventListener(GameOverEvent.class, gameOverListener);
		this.gameView = gameView;

		mainHandler = new Handler(Looper.getMainLooper())
		{
			@Override
			public void handleMessage(Message msg)
			{
				Log.d(TAG, "New message received: " + msg);
				super.handleMessage(msg);

				View view = (View) msg.obj;

				switch (msg.what)
				{
					case Action.SHOW:
						view.setVisibility(View.VISIBLE);
						break;
					case Action.HIDE:
						view.setVisibility(View.GONE);
						break;
					case Action.KILL:
						gameView.stop();
						gameView.terminate();
				}
			}
		};
	}

	private final BaseEventListener<GameOverEvent> gameOverListener =
			new BaseEventListener<GameOverEvent>()
			{
				@Override
				public void handleEvent(GameOverEvent event) throws EventException
				{
					mainHandler.sendMessage(mainHandler.obtainMessage(Action.SHOW, gameOverMenu));
					mainHandler.sendMessage(mainHandler.obtainMessage(Action.KILL));
//					gameView.stop();
				}
			};

	@Override
	void setUpScene()
	{
		view = LayoutInflater.from(Environment.CONTEXT).inflate(R.layout.game_play_container,
				null);
		LayoutInflater inflater = LayoutInflater.from(Environment.CONTEXT);
		hud = inflater.inflate(R.layout.hud, null);
		pauseMenu = inflater.inflate(R.layout.pause_menu, null);
		pauseButton = inflater.inflate(R.layout.pause_button, null);
		gameOverMenu = inflater.inflate(R.layout.game_over, null);

		viewSetup.setupHud(hud);
		viewSetup.setupPauseMenu(pauseMenu);
		viewSetup.setupPauseButton(pauseButton);
		viewSetup.setupGameOverView(gameOverMenu);

		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				((FrameLayout) view).addView(gameView);
				((FrameLayout) view).addView(hud);
				((FrameLayout) view).addView(pauseButton);
				((FrameLayout) view).addView(pauseMenu);
				((FrameLayout) view).addView(gameOverMenu);

				gameOverMenu.setVisibility(View.GONE);
				pauseMenu.setVisibility(View.GONE);
				pauseButton.setVisibility(View.VISIBLE);
				hud.setVisibility(View.VISIBLE);

				return null;
			}
		});
	}

	@Override
	public void playScene()
	{
		gameView.start();
	}

	@Override
	void pauseScene()
	{
		gameView.stop();
		if (!pauseMenu.isShown())
		{
			pauseButton.setVisibility(View.GONE);
			pauseMenu.setVisibility(View.VISIBLE);
		}
	}

	@Override
	void stopScene()
	{
		gameView.stop();
	}

	//	TODO: Split into resumeScene and resumeGame?
	@Override
	void resumeScene()
	{
		if (gameView.isPaused())
		{
			gameView.resume();
		}
		if (pauseMenu.isShown())
		{
			pauseButton.setVisibility(View.VISIBLE);
			pauseMenu.setVisibility(View.GONE);
		}
	}

	@Override
	public void terminateScene()
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				((FrameLayout) view).removeAllViews();
				if (gameView.getParent() != null)
				{
					((ViewGroup) gameView.getParent()).removeView(gameView);
				}
				view.invalidate();
				return null;
			}
		});
	}

	private void pauseGame()
	{
		gameView.pause();
		if (!pauseMenu.isShown())
		{
			pauseButton.setVisibility(View.GONE);
			pauseMenu.setVisibility(View.VISIBLE);
		}
	}

	private void resumeGame()
	{
		gameView.resume();
		if (pauseMenu.isShown())
		{
			pauseButton.setVisibility(View.VISIBLE);
			pauseMenu.setVisibility(View.GONE);
		}
	}

	private class ViewSetup
	{
		void setupHud(View hud)
		{
			hud.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
		}

		void setupPauseButton(View pauseButtonView)
		{
			LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.gravity = Gravity.BOTTOM;
			pauseButtonView.setLayoutParams(layoutParams);

			ImageButton pauseButton = pauseButtonView.findViewById(R.id.pause_button);

			pauseButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					pauseGame();
				}
			});
		}

		void setupPauseMenu(View pauseMenu)
		{
			final View resumeButton = pauseMenu.findViewById(R.id.resumeButton);
			resumeButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					resumeGame();
				}
			});
		}

		void setupGameOverView(View gameOverView)
		{
			LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			gameOverView.setLayoutParams(layoutParams);
			Button mainMenuButton = (Button) gameOverView.findViewById(R.id.mainMenu);
			mainMenuButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Log.d("BUTTON", "Back to Main menu pressed");
					eventSystem.raiseEvent(new MainMenuEvent());
				}
			});

			Button restartButton = (Button) gameOverView.findViewById(R.id.restart);
			restartButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Log.d("BUTTON", "Restart game pressed");
					eventSystem.raiseEvent(new StartGameEvent());
				}
			});
		}
	}
}
