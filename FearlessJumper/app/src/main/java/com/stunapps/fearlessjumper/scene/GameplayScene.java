package com.stunapps.fearlessjumper.scene;

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

	@Inject
	public GameplayScene(GameView gameView, EventSystem eventSystem)
	{
		super(R.layout.game_play_container, eventSystem);
		eventSystem.registerEventListener(GameOverEvent.class, gameOverListener);
		this.gameView = gameView;
	}

	private BaseEventListener<GameOverEvent> gameOverListener =
			new BaseEventListener<GameOverEvent>()
			{
				@Override
				public void handleEvent(GameOverEvent event) throws EventException
				{
					gameView.pause();
					modifyScene(new SceneModificationCallback()
					{
						@Override
						public Object call() throws Exception
						{
							((FrameLayout) view).addView(gameOverMenu);
							return null;
						}
					});
				}
			};

	@Override
	void setUpScene()
	{
		LayoutInflater inflater = LayoutInflater.from(Environment.CONTEXT);
		hud = inflater.inflate(R.layout.hud, null);
		pauseMenu = inflater.inflate(R.layout.pause_menu, null);
		pauseButton = inflater.inflate(R.layout.pause_button, null);
		gameOverMenu = inflater.inflate(R.layout.game_over, null);

		viewSetup.setupHud(hud);
		viewSetup.setupPauseMenu(pauseMenu);
		viewSetup.setupPauseButton(pauseButton);
		viewSetup.setupGameOverView(gameOverMenu);

		((FrameLayout) view).addView(gameView);
		((FrameLayout) view).addView(hud);
		((FrameLayout) view).addView(pauseButton);
	}

	@Override
	public void playScene()
	{

	}

	@Override
	void pauseScene()
	{
		gameView.pause();
		if (!pauseMenu.isShown())
		{
			modifyScene(new SceneModificationCallback()
			{
				@Override
				public Object call() throws Exception
				{
					((FrameLayout) view).removeView(pauseButton);
					((FrameLayout) view).addView(pauseMenu);
					return null;
				}
			});
		}
	}

	@Override
	void resumeScene()
	{
		gameView.resume();
		if (pauseMenu.isShown())
		{
			modifyScene(new SceneModificationCallback()
			{
				@Override
				public Object call() throws Exception
				{
					((FrameLayout) view).removeView(pauseMenu);
					((FrameLayout) view).addView(pauseButton);
					return null;
				}
			});
		}
	}

	@Override
	public void terminateScene()
	{
		((FrameLayout) view).removeAllViews();
		((FrameLayout) view).invalidate();
	}

	private class ViewSetup
	{
		public void setupHud(View hud)
		{
			hud.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
		}

		public void setupPauseButton(View pauseButtonView)
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
					pauseScene();
				}
			});
		}

		public void setupPauseMenu(View pauseMenu)
		{
			final View resumeButton = pauseMenu.findViewById(R.id.resumeButton);
			resumeButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					resumeScene();
				}
			});
		}

		public void setupGameOverView(View gameOverView)
		{
			Button mainMenuButton = (Button) gameOverView.findViewById(R.id.mainMenu);
			mainMenuButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					eventSystem.raiseEvent(new MainMenuEvent());
				}
			});

			Button restartButton = (Button) gameOverView.findViewById(R.id.restart);
			restartButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					eventSystem.raiseEvent(new StartGameEvent());
				}
			});
		}
	}
}
