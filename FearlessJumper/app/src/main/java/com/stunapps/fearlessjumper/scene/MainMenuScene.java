package com.stunapps.fearlessjumper.scene;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.MainActivity;
import com.stunapps.fearlessjumper.R;
import com.stunapps.fearlessjumper.component.spawnable.Enemy.EnemyType;
import com.stunapps.fearlessjumper.event.system.EventSystem;
import com.stunapps.fearlessjumper.event.model.game.ParticleTestEvent;
import com.stunapps.fearlessjumper.event.model.game.StartGameEvent;
import com.stunapps.fearlessjumper.game.Environment;
import com.stunapps.fearlessjumper.game.Environment.Device;
import com.stunapps.fearlessjumper.manager.GameStatsManager;

import java.util.Map;
import java.util.Objects;

/**
 * Created by sunny.s on 11/02/18.
 */

@Singleton
public class MainMenuScene extends AbstractScene
{
	private static final String TAG = MainMenuScene.class.getSimpleName();

	@LayoutRes
	private final int mMainMenuLayout;
	@LayoutRes
	private final int mOptionsMenuLayout;
	@LayoutRes
	private final int mStatsMenuLayout;
	private View mCurrentActiveView;

	private final Context mContext;
	private final MenuSetup mMenuSetup = new MenuSetup();
	private final GameStatsManager mGameStatsManager;

	@Inject
	public MainMenuScene(EventSystem eventSystem, final Context mContext, MainActivity
			mainActivity,
			GameStatsManager gameStatsManager)
	{
		super(R.layout.menu_container, eventSystem, mainActivity);
		this.mContext = mContext;
		this.mGameStatsManager = gameStatsManager;
		this.mMainMenuLayout = R.layout.main_menu;
		this.mOptionsMenuLayout = R.layout.options_menu;
		this.mStatsMenuLayout = R.layout.stats_menu;
	}

	@Override
	void setUpScene()
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				switchToMenu(mMainMenuLayout);
				return null;
			}
		});
	}

	@Override
	public void playScene()
	{

	}

	@Override
	void pauseScene()
	{

	}

	@Override
	void stopScene()
	{

	}

	@Override
	void resumeScene()
	{

	}

	@Override
	public void terminateScene()
	{

	}

	private void showStatsMenu()
	{
		switchToMenu(mStatsMenuLayout);
	}

	private void showOptionsMenu()
	{
		switchToMenu(mOptionsMenuLayout);
	}

	private void showMainMenu()
	{
		switchToMenu(mMainMenuLayout);
	}

	private void switchToMenu(@LayoutRes final int newMenuId)
	{
		modifyScene(new SceneModificationCallback()
		{
			@Override
			public Object call() throws Exception
			{
				if (null != mCurrentActiveView)
				{
					((FrameLayout) view).removeView(mCurrentActiveView);
				}

				mCurrentActiveView = mMenuSetup.setupMenu(newMenuId);
				((FrameLayout) view).addView(mCurrentActiveView);
				return null;
			}
		});
	}


	private class MenuSetup
	{
		public View setupMenu(@LayoutRes int menuLayoutId)
		{
			View menu = LayoutInflater.from(mContext).inflate(menuLayoutId, null);

			if (menuLayoutId == mOptionsMenuLayout)
			{
				/*
					Options menu buttons
		 		*/
				Button optionsBackButton = menu.findViewById(R.id.optionsBackButton);
				optionsBackButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Options back button pressed");
						showMainMenu();
					}
				});

				Button statsButton = menu.findViewById(R.id.statsButton);
				statsButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Options back button pressed");
						showStatsMenu();
					}
				});

				final AudioManager audioManager = Device.audioManager;
				int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				SeekBar volControl = menu.findViewById(R.id.seekBar);
				volControl.setMax(maxVolume);
				volControl.setProgress(curVolume);
				volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
				{
					@Override
					public void onStopTrackingTouch(SeekBar arg0)
					{
					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0)
					{
					}

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2)
					{
						audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
					}
				});
			}
			else if (menuLayoutId == mMainMenuLayout)
			{
				/*
					Main menu buttons
		 		*/
				//	Add listener for startButton
				ImageButton startButton = menu.findViewById(R.id.startButton);
				startButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Start button pressed");
						eventSystem.raiseEvent(new StartGameEvent());
					}
				});

				//	Add listener for particleTestButton
				Button optionsMenuButton = menu.findViewById(R.id.optionsMenuButton);
				optionsMenuButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Options menu button pressed");
						showOptionsMenu();
					}
				});

				//	Add listener for particleTestButton
				Button particleTestButton = menu.findViewById(R.id.particleTestButton);
				particleTestButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Particle test button pressed");
						eventSystem.raiseEvent(new ParticleTestEvent());
					}
				});

				//	Add listener for quit button
				ImageButton quitButton = menu.findViewById(R.id.quitButton);
				quitButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Quit button pressed");
						android.os.Process.killProcess(android.os.Process.myPid());
						System.exit(1);
					}
				});
			}
			else if (menuLayoutId == mStatsMenuLayout)
			{
				//	Add listener for particleTestButton
				Button optionsMenuButton = menu.findViewById(R.id.statsBackButton);
				optionsMenuButton.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Log.d(TAG, "Stats menu back button pressed");
						showOptionsMenu();
					}
				});

				long globalHighScore = mGameStatsManager.getGlobalHighScore();
				long sessionHighScore = mGameStatsManager.getSessionHighScore();
				long gameplayCount = mGameStatsManager.getGamePlayCount();
				long averageScore = mGameStatsManager.getAverageScore();
				Map<String, Integer> hurtStats = mGameStatsManager.getHurtStats();

				setStat(menu, R.id.gamesPlayedValue, gameplayCount);
				setStat(menu, R.id.sessionHighScoreValue, sessionHighScore);
				setStat(menu, R.id.highScoreValue, globalHighScore);
				setStat(menu, R.id.averageScoreValue, averageScore);

				if (hurtStats.get(EnemyType.MINIGON.name()) != null)
				{
					setStat(menu, R.id.minigonValue, hurtStats.get(EnemyType.MINIGON.name()));
				}

				if (hurtStats.get(EnemyType.GRORUM.name()) != null)
				{
					setStat(menu, R.id.grorumValue, hurtStats.get(EnemyType.GRORUM.name()));
				}

				if (hurtStats.get(EnemyType.PYRADON.name()) != null)
				{
					setStat(menu, R.id.pyradonValue, hurtStats.get(EnemyType.PYRADON.name()));
				}

				if (hurtStats.get(EnemyType.ZELDROY.name()) != null)
				{
					setStat(menu, R.id.zeldroyValue, hurtStats.get(EnemyType.ZELDROY.name()));
				}

				if (hurtStats.get(EnemyType.DRAXUS.name()) != null)
				{
					setStat(menu, R.id.draxusValue, hurtStats.get(EnemyType.DRAXUS.name()));
				}
			}

			return menu;
		}

		private void setStat(View parentView, @IdRes final int viewId, long value)
		{
			TextView gamesPlayed = parentView.findViewById(viewId);
			gamesPlayed.setText(String.valueOf(value));
		}
	}

}
